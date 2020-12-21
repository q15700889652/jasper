package jasper.scorrtUtil.JasperScorrt.dataSource.impl;

import java.sql.Connection;
import java.util.HashMap;

import jasper.scorrtUtil.JasperScorrt.dataSource.DataSourceLoding;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class DataSourceLodingJdbc implements DataSourceLoding{
	JasperPrint jasperPrint = null;		
	public JasperPrint dataSourceLoding(String name, HashMap<String, Object> parments, Object ob) {
		try {
			jasperPrint = JasperFillManager.fillReport(name, parments==null?new HashMap<String, Object>():parments,(Connection)ob);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return jasperPrint;
	}

}
