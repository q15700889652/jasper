package jasper.scorrtUtil.JasperScorrt.page;

import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrint;

public interface PageLoding {
	HashMap<String, Object> pageLoding(int flag,String value,JasperPrint jasperPrint,String pageStr);
}
