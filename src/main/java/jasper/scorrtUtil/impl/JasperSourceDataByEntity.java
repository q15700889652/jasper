package jasper.scorrtUtil.impl;




import java.util.HashMap;
import java.util.List;

import jasper.scorrtUtil.JasperSourceData;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
/**
 * PDF Show Impl
 * @author scorrt
 *
 */
public class JasperSourceDataByEntity implements JasperSourceData{
	private JRBeanCollectionDataSource dataSource;
	
	public JasperSourceDataByEntity(JRBeanCollectionDataSource dataSource) {
		this.dataSource=dataSource;
	}
	
	public JasperPrint sourceData(String name, HashMap<String, Object> parments, Object ob) {
		JasperPrint jasperPrint = null;		
		try {
			jasperPrint = JasperFillManager.fillReport(name, parments==null?new HashMap<String, Object>():parments,this.dataSource);
		System.out.println(jasperPrint.getPages().size());
		List<JRPrintPage> e=jasperPrint.getPages();
		
		} catch (JRException e) {
			e.printStackTrace();
		}
		return jasperPrint;
	}
}
