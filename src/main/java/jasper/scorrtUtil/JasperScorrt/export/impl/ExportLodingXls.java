package jasper.scorrtUtil.JasperScorrt.export.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jasper.scorrtUtil.JasperScorrt.export.ExportLoding;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

public class ExportLodingXls implements ExportLoding {

	@Override
	public void ExportLoding(JasperPrint jasperPrint, HttpServletRequest request, HttpServletResponse response,
			int index, HttpSession session) {
		JRXlsExporter exporter = new JRXlsExporter();
		try {
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
   		        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(true);
			exporter.setConfiguration(configuration);
			exporter.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

}
