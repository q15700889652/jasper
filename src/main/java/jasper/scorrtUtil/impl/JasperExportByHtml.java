package jasper.scorrtUtil.impl;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jasper.scorrtUtil.JasperExport;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;

public class JasperExportByHtml implements JasperExport {

	public void export(JasperPrint jasperPrint, HttpServletRequest request, HttpServletResponse response) {
		try {
			HtmlExporter exporterHTML = new HtmlExporter();
			SimpleExporterInput exporterInput = new SimpleExporterInput(jasperPrint);
			exporterHTML.setExporterInput(exporterInput);
			SimpleHtmlExporterOutput exporterOutput;
			exporterOutput = new SimpleHtmlExporterOutput(response.getOutputStream());
			exporterOutput.setImageHandler(new WebHtmlResourceHandler("image?image={0}"));
			
			exporterHTML.setExporterOutput(exporterOutput);
			SimpleHtmlReportConfiguration reportExportConfiguration = new SimpleHtmlReportConfiguration();
			reportExportConfiguration.setWhitePageBackground(false);
			reportExportConfiguration.setRemoveEmptySpaceBetweenRows(true);
			exporterHTML.setConfiguration(reportExportConfiguration);
			exporterHTML.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
