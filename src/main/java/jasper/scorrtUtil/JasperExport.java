package jasper.scorrtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperPrint;

public interface JasperExport {
	void export(JasperPrint jasperPrint, HttpServletRequest request, HttpServletResponse response,int index,HttpSession session);
}
