package jasper.scorrtUtil.JasperScorrt.param.impl;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import jasper.scorrtUtil.JasperScorrt.param.ParamLoding;

public class ParamLodingHtmlDynamic implements ParamLoding{
	private HashMap<String, Object> parameters;
	private int x = 0;
	private String str = " 1=1 "; // 动态sql
	private String values = ""; // 动态页面参数的
	private String keys = ""; // 缓存的key

	public HashMap<String, Object> paramLoding(HttpServletRequest request) {
		parameters = new HashMap<String, Object>();
		Enumeration<String> paraNames = request.getParameterNames();
		for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			String thisValue = request.getParameter(thisName);
			keys += thisName + thisValue;
			if (!thisName.equals("page") && !thisName.equals("name")) {// 刷选掉page参数
				if (x > 0) {// 刷选掉报表名称参数
					if (thisValue != null && thisValue.trim().length() > 0) {
						str += " and " + thisName + "=" + thisValue;
						values += "&" + thisName + "=" + thisValue;
					}
				}
			}
			x++;
		}
		parameters.put("str", str);
		parameters.put("values", values);
		parameters.put("keys", keys);
		return parameters;
	}
}
