package jasper.scorrtUtil.JasperScorrt;

import java.util.HashMap;

import jasper.scorrtUtil.JasperScorrt.caChe.Cache;
import net.sf.jasperreports.engine.JasperPrint;

public class JasperHtmlStatic extends JasperHanldDefault {

	public JasperHtmlStatic() {
		super();
	}

	private HashMap<String, Object> paramMap;
	String key="";
	public void Start() {
		paramMap = paramLoding.paramLoding(request);
		key= paramMap.get("keys").toString();
		parments = paramMap;
		boolean flag = Cache.beforeCaChe(paramMap.get("keys").toString());
		coreLoding();
		if (!flag) {
			dataSource();
		} else {
			jasperPrint = (JasperPrint) Cache.map.get(key);
			Cache.flag++;
		}
		exportLoding.ExportLoding(jasperPrint, request, response, index, session);
	}
}
