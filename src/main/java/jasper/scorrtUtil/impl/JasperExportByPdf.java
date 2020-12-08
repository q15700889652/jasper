package jasper.scorrtUtil.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jasper.scorrtUtil.JasperExport;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

public class JasperExportByPdf implements JasperExport {

	public void export(JasperPrint jasperPrint, HttpServletRequest request, HttpServletResponse response) {
		JRPdfExporter exporter = new JRPdfExporter();
		try {
			response.setContentType("application/pdf");
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			exporter.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
