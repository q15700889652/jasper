package jasper.scorrtUtil;

import java.util.LinkedHashMap;
import java.util.Set;
/**
 * 缓存
 * @author chenshuoguo
 *
 */
public class Cache {
	public static LinkedHashMap<String, Object> map =  new LinkedHashMap<String, Object>();
	public static int flag=0;
	/**
	 * 是否存在缓存
	 * @param key
	 * @return
	 */
	public static boolean beforeCaChe(String key) {
		if (map.get(key) != null) {
			return true;
		}
		return false;
	};
	
	/**
	 * 是否需要缓存
	 * @param key
	 * @param o
	 */
	public static void afterCaChe(String key, Object o) {
		int i=0;
		Set<String> setend = map.keySet();
		if(map.size()==0) {
			map.put(key,o);
		}
		for (String keys : setend) {
			if (!key.equals(keys)) {
				i++;
			}
		}
		if(i==map.size()) {
			Cache.map.put(key, o);
		}
	};
}
