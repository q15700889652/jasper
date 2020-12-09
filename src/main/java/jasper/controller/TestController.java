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
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		//hashmap值页面封装
		String keys = "";
		Enumeration<String> paraNames=request.getParameterNames();
		int x=0;
		String str=" 1=1 ";
		String values="";
		for(Enumeration<String> e=paraNames;e.hasMoreElements();){
		      String thisName=e.nextElement().toString();
		      String thisValue=request.getParameter(thisName);
		      System.out.println("param的key:"+thisName+"--------------param的value:"+thisValue);
		      keys+=thisValue;
		      parameters.put(thisName, thisValue==null?"":thisValue);
		      if(!thisName.equals("page")) {
			      if(x>0) {
			    	  if(thisValue!=null && thisValue.length()>0)
			    	  str+=" and "+thisName+"="+thisValue;
			    	  values+="&"+thisName+"="+thisValue;
			      }
		      }
		      x++;
		   
		}
		System.out.println(str);
		parameters.put("str", str);
		
		
		request.getParameterMap();
		JasperPrint jasperPrint = null;
		Boolean cache = false;
		// 是否存在缓存
		Set<String> set = Cache.map.keySet();
		for (String key : set) {
			if (keys.equals(key)) {
				jasperPrint = (JasperPrint) Cache.map.get(keys);
				cache = true;
			}
		}
		int pageIndex = 0;
		int lastPageIndex = 0;
		String pageStr = request.getParameter("page");

		if (!cache) {

		

			// list数据源
			/*
			 * List<User> list = new ArrayList<User>(); for (int i = 1; i <= 50; i++) { User
			 * users = new User(i, "name" + i); list.add(users); }
			 */

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
				
				//
				JRTemplatePrintText jasperPrint1=(JRTemplatePrintText) jasperPrint.getPages().get(pageIndex).getElements().get(jasperPrint.getPages().get(pageIndex).getElements().size()-5);
				//JROrigin t4=jasperPrint1.getPropertiesMap()
				JRPropertiesMap t5=jasperPrint1.getPropertiesMap();
				String t1=jasperPrint1.getHyperlinkReference();
				System.out.println(t1+values);
				jasperPrint1.setHyperlinkReference(t1+values);
				
				logger.info(t1);
				
				
				//                                   hyperlinkReference		
				ModeEnum t2=jasperPrint1.getOwnModeValue();
				JRPropertiesHolder t3=jasperPrint1.getParentProperties();
				// 放入缓存
				Set<String> setend = Cache.map.keySet();
				if (Cache.map.keySet().size() == 0) {
					Cache.map.put(keys, jasperPrint);
				}
				for (String key : setend) {
					if (!keys.equals(key)) {
						Cache.map.put(keys, jasperPrint);
					}
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}

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
