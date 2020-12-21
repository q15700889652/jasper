package jasper.scorrtUtil.JasperScorrt.dataSource;

import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrint;

public interface DataSourceLoding {
	JasperPrint dataSourceLoding(String name, HashMap<String, Object> parments, Object ob);
}
