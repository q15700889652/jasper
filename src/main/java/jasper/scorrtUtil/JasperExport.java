package jasper.scorrtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperPrint;

public interface JasperExport {
	void export(JasperPrint jasperPrint, HttpServletRequest request, HttpServletResponse response,int index);
}
