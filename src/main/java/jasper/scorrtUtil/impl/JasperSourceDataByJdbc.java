package jasper.scorrtUtil.impl;

import java.sql.Connection;
import java.util.HashMap;

import jasper.scorrtUtil.JasperSourceData;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Html Show Impl
 * 
 * @author scorrt
 *
 */
public class JasperSourceDataByJdbc implements JasperSourceData {
	private Connection coon;
	public JasperSourceDataByJdbc(Connection coon) {
		this.coon=coon;
	}
	public JasperPrint sourceData(String name, HashMap<String, Object> parments, Object ob)  {
		JasperPrint jasperPrint = null;
		try {
			jasperPrint = JasperFillManager.fillReport(name, parments==null?new HashMap<String, Object>():parments , coon);
		} catch (JRException e) {
			e.printStackTrace();
		}
		return jasperPrint;
	}

}
