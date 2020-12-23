package jasper.controller;

import java.util.HashMap;

public class JdbcCache {
	static HashMap<String, Object> jdbcHashMap = new HashMap<String, Object>();
	
	static void removeCache() {
		jdbcHashMap=new HashMap<String, Object>();
	}
}
