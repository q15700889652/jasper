package jasper.scorrtUtil.JasperScorrt.export.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jasper.scorrtUtil.JasperScorrt.export.ExportLoding;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;

public class ExportLodingCsv implements ExportLoding {

	@Override
	public void ExportLoding(JasperPrint jasperPrint, HttpServletRequest request, HttpServletResponse response,
			int index, HttpSession session) {
		JRCsvExporter exporter = new JRCsvExporter();
		try {
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getOutputStream()));
			exporter.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

}
