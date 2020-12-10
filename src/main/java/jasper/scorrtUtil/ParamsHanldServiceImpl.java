package jasper.scorrtUtil;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author liningning
 * 加工sql
 * 加工动态分页
 * 加工缓存key
 */
public class ParamsHanldServiceImpl extends JasperExportHanld implements ParamsHanldService{
	
	private Map<String, Object> parameters;
	/**
	 * 动态条件查询
	 * 动态sql: 组装为  -> and key+"="+value 
	 * 约定:jasper动态sql部分必须写为   -> $P!{str}
	 * 动态分页条件: 当以这个条件查询的时候  报表会动态的从新租住装分页的条件参数  -> ?page=3&value1=1&value2=2......            
	 * @return 
	 */
	public Map<String, Object> loadingChangeParams() {
		parameters = new HashMap<String, Object>();
		Enumeration<String> paraNames = request.getParameterNames();
		int x = 0;
		String str = " 1=1 ";
		String values = "";
		String keys = "";
		for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			String thisValue = request.getParameter(thisName);
			keys+=thisName+thisValue;
			if (!thisName.equals("page")&&!thisName.equals("name")) {//刷选掉page参数
				if (x > 0) {//刷选掉报表名称参数
					if (thisValue != null && thisValue.trim().length() > 0) {
						str += " and " + thisName + "=" + thisValue;
					    values += "&" + thisName + "=" + thisValue;
					}
				}
			}
			x++;
		}
		parameters.put("str", str);//动态sql
		parameters.put("values", values);//动态页面参数的
		parameters.put("keys", keys);//缓存的key
		System.out.println("str:"+ str);
		System.out.println("values:"+ values);
		System.out.println("keys:"+ keys);
		parments=(HashMap<String, Object>) parameters;
		super.values=values;
		return parameters;
	};
	
	/**
	 * 固定的条件查询
	 */
	public Map<String, Object> loadingFixedParams() {
		parameters = new HashMap<String, Object>();
		String keys = "";
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
