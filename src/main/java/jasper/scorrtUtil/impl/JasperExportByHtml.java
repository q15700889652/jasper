package jasper.scorrtUtil.impl;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jasper.scorrtUtil.Cache;
import jasper.scorrtUtil.JasperExport;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;

public class JasperExportByHtml implements JasperExport {

	public void export(JasperPrint jasperPrint, HttpServletRequest request, HttpServletResponse response,int index,HttpSession session) {
		try {
						// 将html输出到浏览器上
						HtmlExporter exporterHTML = new HtmlExporter();
						//建立一个简单的流
						SimpleExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
						exporterHTML.setExporterInput(exporterInput);
						SimpleHtmlExporterOutput exporterOutput;

						exporterOutput = new SimpleHtmlExporterOutput(response.getOutputStream());
						System.out.println(request.getParameterMap().toString()+session);
						System.out.println(request.getParameterMap().toString());
						//session丢失问题处理
						Cache.afterCaChe(request.getParameterMap().toString(), jasperPrint);
						
						
						request.getSession().setAttribute(
								ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
								jasperPrint);
						
						exporterOutput.setImageHandler(new WebHtmlResourceHandler("/image?image={0}"));
						
						exporterHTML.setExporterOutput(exporterOutput);
						
						
						
						SimpleHtmlReportConfiguration reportExportConfiguration = new SimpleHtmlReportConfiguration();//jasper内部样式配置
						reportExportConfiguration.setWhitePageBackground(false);
						reportExportConfiguration.setRemoveEmptySpaceBetweenRows(true);
						reportExportConfiguration.setPageIndex(index);
						
						exporterHTML.setConfiguration(reportExportConfiguration);
						System.out.println("----------------------------------------------");
						exporterHTML.exportReport();
						
						
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
