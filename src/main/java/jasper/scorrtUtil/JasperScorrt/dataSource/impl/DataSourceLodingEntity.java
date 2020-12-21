package jasper.scorrtUtil.JasperScorrt.dataSource.impl;

import java.util.HashMap;

import jasper.scorrtUtil.JasperScorrt.dataSource.DataSourceLoding;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class DataSourceLodingEntity implements DataSourceLoding{
	JasperPrint jasperPrint = null;		
	public JasperPrint dataSourceLoding(String name, HashMap<String, Object> parments, Object ob) {
		try {
			jasperPrint = JasperFillManager.fillReport(name, parments==null?new HashMap<String, Object>():parments,(JRBeanCollectionDataSource)ob);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return jasperPrint;
	}

}
