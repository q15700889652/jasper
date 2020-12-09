package jasper.controller;

import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jasper.entity.Student;
import jasper.entity.User;
import jasper.mapper.UserMapper;
import jasper.scorrtUtil.Cache;
import jasper.scorrtUtil.JasperExportHanld;
import jasper.scorrtUtil.impl.JasperExportByHtml;
import jasper.scorrtUtil.impl.JasperExportByPdf;
import jasper.scorrtUtil.impl.JasperSourceDataByEntity;
import jasper.scorrtUtil.impl.JasperSourceDataByJdbc;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JROrigin;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPropertiesHolder;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;

@RestController
public class TestController {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JasperExportHanld jasperExportHanld;
	@Autowired
	private SqlSession sqlSession;
	
	Logger logger = Logger.getLogger(TestController.class);
	/**
	 * pdf
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @throws JRException
	 */
	@GetMapping("pdfdata")
	public void downloadPdf(HttpServletRequest request, HttpServletResponse response, String name) throws JRException {
		/* 测试mybatis连接 */
		User u = userMapper.getUser(1);
		System.out.println(u.getName());
		Student student = new Student("Jack", "China");
		List<Student> list = new ArrayList<>();
		list.add(student);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
		jasperExportHanld.setName(name);
		jasperExportHanld.setRequest(request);
		jasperExportHanld.setResponse(response);
		jasperExportHanld.setJasperExport(new JasperExportByPdf());
		jasperExportHanld.setJasperDate(new JasperSourceDataByEntity(dataSource));
		jasperExportHanld.start();

	}

	/**
	 * html
	 * 
	 * @param request
	 * @param response
	 * @param name
	 * @throws JRException
	 * @throws SQLException
	 */
	@GetMapping("htmldata")
	public void ddtest(HttpServletRequest request, HttpServletResponse response, String name)
			throws JRException, SQLException {
		Connection ob = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
		jasperExportHanld.setName(name); // 文件名称
		jasperExportHanld.setRequest(request);
		jasperExportHanld.setResponse(response);
		jasperExportHanld.setJasperDate(new JasperSourceDataByJdbc(ob));// 读取数据方式
		jasperExportHanld.setJasperExport(new JasperExportByPdf());// 展示数据方式
		jasperExportHanld.start();
	}

	@GetMapping("htmlpagedata1")
	public void pagedate(HttpServletRequest request, HttpServletResponse response, String name)
			throws JRException, SQLException {
		// 分页算法
		int pagesize = 10;
		String pagenumber = request.getParameter("page");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pagenumber", Integer.valueOf(pagenumber) == 0 ? 1 : pagenumber);
		parameters.put("pagesize", pagesize);

		//
		List<User> u = userMapper.getUsers(parameters);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(u);
		jasperExportHanld.setName(name); // 文件名称
		jasperExportHanld.setRequest(request);
		jasperExportHanld.setResponse(response);
		jasperExportHanld.setJasperDate(new JasperSourceDataByEntity(dataSource));// 读取数据方式
		jasperExportHanld.setJasperExport(new JasperExportByHtml());// 展示数据方式
		jasperExportHanld.start();

	}

	@GetMapping("htmlpagedata")
	public void dd(HttpServletRequest request, HttpServletResponse response, String name) {
		
		
		//动态查询条件组装                                                 换算出sql查询条件的动态条件组装                                      参数request   返回值map   一个是sql的换算条件    一个是jasper动态页面需要的页码条件
		//loadingParamters();        
		Map<String, Object> parameters = new HashMap<String, Object>();
		Enumeration<String> paraNames = request.getParameterNames();
		int x = 0;
		String str = " 1=1 ";
		String values = "";
		String keys = "";
		for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			String thisValue = request.getParameter(thisName);
			keys+=thisName+thisValue;
			if (!thisName.equals("page")&&!thisName.equals("name")) {//刷选掉page参数
				if (x > 0) {//刷选掉报表名称参数
					if (thisValue != null && thisValue.length() > 0) {
						str += " and " + thisName + "=" + thisValue;
					    values += "&" + thisName + "=" + thisValue;
					}
				}
			}
			x++;

		}
		System.out.println("sql:"+str);
		System.out.println("values:"+values);
		System.out.println("cache:"+keys);
		parameters.put("str", str);
		parameters.put("values", values);
		parameters.put("cache", keys);
		//判断是否存在缓存                                                 页码+查询条件组成key 判断缓存是否存在                      参数request   返回值 布尔 
		//beforeCaChe();  
		JasperPrint jasperPrint = null;
		if(!Cache.beforeCaChe(parameters.get("cache").toString())) 
		//加载Jasper                编译好的jasper文件对象                                                      参数name     返回值 jasper对象                         
		//loaddingJasper(); 
		{
			
			String JasperAddress = "/jaspermodel/";
			String saveaddress = "D:\\";	
			String filename = saveaddress + name + ".jasper";
			File compiledFile = new File(filename);
			JasperReport jr;
			try {
				Connection ob = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
				if (compiledFile.exists()) {
					jr = (JasperReport) JRLoader.loadObject(compiledFile);// 加在jasper文件
				} else {
					InputStream inputStream = getClass().getResourceAsStream(JasperAddress + name + ".jrxml");// 读取模板
																												// 从此类的包根目录
					JasperDesign jd = JRXmlLoader.load(inputStream);// 装载
					jr = (JasperCompileManager.compileReport(jd));// 编译jrxml文件 防止版本差异导致模板填充出现问题
					JRSaver.saveObject(jr, filename);// 备份jasper文件
				}
				// jasperPrint = JasperFillManager.fillReport(name,parameters , ob);

				/*
				 * JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
				 */

				jasperPrint = JasperFillManager.fillReport(saveaddress + name + ".jasper", parameters, ob);
				
				
			} catch (Exception e) {
				//e.printStackTrace();
			}
			
			
			
			
		}else {
			jasperPrint=(JasperPrint) Cache.map.get(parameters.get("cache").toString());
		}
		
		
		
		
		
		
		
		
		
		//加载Jasper页码                                              jasper动态条件页码是取决于查询条件的参数               参数jasper动态页面需要的页码条件+返回值 jasper对象                     返回值jasper对象
		//loaddingJasperPage();
		int pageIndex = 0;
		int lastPageIndex = 0;
		String pageStr = request.getParameter("page");


		if (null != jasperPrint.getPages()) {
			
				lastPageIndex = jasperPrint.getPages().size() - 1;
			
		}
		if (null == pageStr) {
			pageStr = "0";
		}
		try {
			pageIndex = Integer.valueOf(pageStr);
			if (pageIndex > 0) {
				pageIndex = pageIndex - 1;
			}
		} catch (Exception e) {
			// 如果得到的非数字字符串
			if ("lastPage".equals(pageStr)) {
				pageIndex = lastPageIndex;
			}
		}

		if (pageIndex < 0) {
			pageIndex = 0;
		}
		if (pageIndex > lastPageIndex) {
			pageIndex = lastPageIndex;
		}
		if(null != jasperPrint.getPages()&&jasperPrint.getPages().size()>0) {
		//
		JRTemplatePrintText jasperPrint1=(JRTemplatePrintText) jasperPrint.getPages().get(pageIndex).getElements().get(jasperPrint.getPages().get(pageIndex).getElements().size()-6);
		JRTemplatePrintText jasperPrint2=(JRTemplatePrintText) jasperPrint.getPages().get(pageIndex).getElements().get(jasperPrint.getPages().get(pageIndex).getElements().size()-5);
		JRTemplatePrintText jasperPrint3=(JRTemplatePrintText) jasperPrint.getPages().get(pageIndex).getElements().get(jasperPrint.getPages().get(pageIndex).getElements().size()-4);
		JRTemplatePrintText jasperPrint4=(JRTemplatePrintText) jasperPrint.getPages().get(pageIndex).getElements().get(jasperPrint.getPages().get(pageIndex).getElements().size()-3);
		JRTemplatePrintText jasperPrint5=(JRTemplatePrintText) jasperPrint.getPages().get(pageIndex).getElements().get(jasperPrint.getPages().get(pageIndex).getElements().size()-2);
		String t1=jasperPrint1.getHyperlinkReference();
		String t2=jasperPrint2.getHyperlinkReference();
		String t3=jasperPrint3.getHyperlinkReference();
		String t4=jasperPrint4.getHyperlinkReference();
		String t5=jasperPrint5.getHyperlinkReference();
		System.out.println(t1+values);
		jasperPrint1.setHyperlinkReference(t1+values);
		jasperPrint2.setHyperlinkReference(t2+values);
		jasperPrint3.setHyperlinkReference(t3+values);
		jasperPrint4.setHyperlinkReference(t4+values);
		jasperPrint5.setHyperlinkReference(t5+values);
		}
		//缓存处理                                                                   判断是否缓存，无则缓存                                                               参数request+返回值 jasper对象    
		//afterCaChe();             
		Cache.afterCaChe(parameters.get("cache").toString(), jasperPrint);
		//html 展示                                                          展示方式                                                                                               参数response+返回值 jasper对象        
		//showHtml();		
		try {
			// 将html输出到浏览器上
			HtmlExporter exporterHTML = new HtmlExporter();
			// 分页

			SimpleExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
			exporterHTML.setExporterInput(exporterInput);
			SimpleHtmlExporterOutput exporterOutput;

			exporterOutput = new SimpleHtmlExporterOutput(response.getOutputStream());

			exporterOutput.setImageHandler(new WebHtmlResourceHandler("image?image={0}"));

			exporterHTML.setExporterOutput(exporterOutput);
			SimpleHtmlReportConfiguration reportExportConfiguration = new SimpleHtmlReportConfiguration();
			reportExportConfiguration.setWhitePageBackground(false);
			reportExportConfiguration.setRemoveEmptySpaceBetweenRows(true);
			reportExportConfiguration.setPageIndex(pageIndex);
			exporterHTML.setConfiguration(reportExportConfiguration);
			exporterHTML.exportReport();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
