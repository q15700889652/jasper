package jasper.scorrtUtil;

import java.util.HashMap;

import net.sf.jasperreports.engine.JasperPrint;

/**
 * 
 * @author scorrt
 * 装载数据
 * ob 不同数据源连接
 * filename 装在名称
 *
 */
public interface JasperSourceData {
	
	JasperPrint sourceData(String name, HashMap<String, Object> parments, Object ob);

}
