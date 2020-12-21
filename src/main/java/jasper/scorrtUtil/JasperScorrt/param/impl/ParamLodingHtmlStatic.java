package jasper.scorrtUtil.JasperScorrt.param.impl;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import jasper.scorrtUtil.JasperScorrt.param.ParamLoding;

public class ParamLodingHtmlStatic implements ParamLoding {
	private HashMap<String, Object> parameters;
	private String keys = ""; // 缓存的key

	public HashMap<String, Object> paramLoding(HttpServletRequest request) {
		parameters = new HashMap<String, Object>();
		Enumeration<String> paraNames = request.getParameterNames();
		for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			String thisValue = request.getParameter(thisName);
			parameters.put(thisName, thisValue);//固定条件
			keys+=thisValue;
		}
		parameters.put("keys", keys);//缓存的key
		return parameters;
	};
	
}
