package jasper.scorrtUtil.JasperScorrt;

import java.util.HashMap;

import jasper.scorrtUtil.JasperScorrt.caChe.Cache;
import jasper.scorrtUtil.JasperScorrt.param.impl.ParamLodingHtmlDynamic;
import net.sf.jasperreports.engine.JasperPrint;

public class JasperHtmlDynamic extends JasperHanldDefault {
	public JasperHtmlDynamic() {
		super();
	}

	private HashMap<String, Object> paramMap;
	private HashMap<String, Object> pageMap;
	boolean flag = false;
	String key = "";

	public void Start() {
		setParamLoding(new ParamLodingHtmlDynamic());
		page=request.getParameter("page");
		paramMap = paramLoding.paramLoding(request);
		key = paramMap.get("keys").toString();
		parments = paramMap;
		flag = Cache.beforeCaChe(key);
		coreLoding();
		if (!flag) {
			dataSource();
		} else {
			jasperPrint = (JasperPrint) Cache.map.get(key);
			Cache.flag++;
		}
		pageMap=pageLoding.pageLoding(Cache.flag, paramMap.get("values").toString(), jasperPrint, page);
		
		exportLoding.ExportLoding((JasperPrint)pageMap.get("jasperPrint"), request, response, Integer.valueOf(pageMap.get("index").toString()) , session);
	}
}
