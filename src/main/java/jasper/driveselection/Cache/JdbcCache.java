package jasper.driveselection.Cache;

import java.util.HashMap;

public class JdbcCache {
	public static HashMap<String, Object> jdbcHashMap = new HashMap<String, Object>();
	public static String entityname;
	public static void removeCache() {
		jdbcHashMap=new HashMap<String, Object>();
	}
}
