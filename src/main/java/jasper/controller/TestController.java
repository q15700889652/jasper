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
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jasper.entity.Student;
import jasper.entity.User;
import jasper.mapper.UserMapper;
import jasper.scorrtUtil.JasperScorrt.JasperHanld;
import jasper.scorrtUtil.JasperScorrt.JasperHtmlDynamic;
import jasper.scorrtUtil.JasperScorrt.caChe.Cache;
import jasper.scorrtUtil.JasperScorrt.export.impl.ExportLodingHtml;
import jasper.scorrtUtil.JasperScorrt.export.impl.ExportLodingPdf;
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
	private JasperHanld jasperExportHanld;
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
		/*
		 * jasperExportHanld.setName(name); jasperExportHanld.setRequest(request);
		 * jasperExportHanld.setResponse(response);
		 * jasperExportHanld.setJasperExport(new JasperExportByPdf());
		 * jasperExportHanld.setJasperDate(new JasperSourceDataByEntity(dataSource));
		 * jasperExportHanld.start();
		 */

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
		/*
		 * jasperExportHanld.setName(name); // 文件名称
		 * jasperExportHanld.setRequest(request);
		 * jasperExportHanld.setResponse(response); jasperExportHanld.setJasperDate(new
		 * JasperSourceDataByJdbc(ob));// 读取数据方式 jasperExportHanld.setJasperExport(new
		 * JasperExportByPdf());// 展示数据方式 jasperExportHanld.start();
		 */
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
		/*
		 * jasperExportHanld.setName(name); // 文件名称
		 * jasperExportHanld.setRequest(request);
		 * jasperExportHanld.setResponse(response); jasperExportHanld.setJasperDate(new
		 * JasperSourceDataByEntity(dataSource));// 读取数据方式
		 * jasperExportHanld.setJasperExport(new JasperExportByHtml());// 展示数据方式
		 * jasperExportHanld.start();
		 */

	}

	@GetMapping("htmlpagedata")
	public void dd(HttpServletRequest request, HttpServletResponse response, HttpSession session,String name) {
		//JasperChangeParamsHanld jcph=(JasperChangeParamsHanld) jasperExportHanld;
		System.out.println("123");
		jasperExportHanld=new JasperHtmlDynamic();
		
		Connection ob = null;
		try {
			ob = sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection();
		
			jasperExportHanld.setName(name); // 文件名称
			jasperExportHanld.setRequest(request);
			jasperExportHanld.setResponse(response);
			jasperExportHanld.setSession(session);
			jasperExportHanld.setOb(ob);
			jasperExportHanld.setExportLoding(new ExportLodingHtml());
			jasperExportHanld.Start();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			Cache.flag=0;
			try {
				ob.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
