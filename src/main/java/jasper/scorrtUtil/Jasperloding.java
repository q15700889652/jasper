package jasper.scorrtUtil;

import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrint;

public interface Jasperloding {
	JasperPrint jasperloding(String filename,HashMap<String, Object> map,Object ob);
}
