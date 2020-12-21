package jasper.scorrtUtil.JasperScorrt.export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperPrint;

public interface ExportLoding {
	void ExportLoding(JasperPrint jasperPrint, HttpServletRequest request, HttpServletResponse response,int index,HttpSession session);
}
