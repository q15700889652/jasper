package jasper.scorrtUtil;

import java.util.LinkedHashMap;
import java.util.Set;

public class Cache {
	public static LinkedHashMap<String, Object> map =  new LinkedHashMap<String, Object>();

	public static boolean beforeCaChe(String key) {
		if (map.get(key) != null) {
			return true;
		}
		return false;
	};

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
