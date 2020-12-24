package jasper.driveselection.impl;


import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import jasper.driveselection.DriveSelection;
import jasper.driveselection.Serialize;
import jasper.driveselection.Cache.JdbcCache;
import jasper.driveselection.entity.JdbcEntity;

public class DriveSelectionImpl implements DriveSelection{

	private static boolean flag = true;

	// 增加数据源
	 public boolean add(String key, JdbcEntity je) throws IOException {
		 System.out.println("add");
		 JdbcCache.jdbcHashMap.put(key, je);
		 return flag;
	}

	// 移除数据源
	 public boolean remove(String key) throws IOException {
		 System.out.println("remove");
		 JdbcCache.jdbcHashMap.remove(key);
		 return flag;
	}

	// 修改
	 public boolean update(String oldkey, String newkey, JdbcEntity je) throws IOException {
		 System.out.println("update");
		 JdbcCache.jdbcHashMap.remove(oldkey);
		 JdbcCache.jdbcHashMap.put(newkey, je);
		 return flag;
	}

	// 查询
	 public HashMap<String, Object> query(String key) throws ClassNotFoundException, IOException {
		 
		 return Serialize.obj3;
	}

	@SuppressWarnings("unused")
	// 测试连接
	public boolean testConnection(JdbcEntity je) {
		System.out.println("test");
		// 加载驱动类
		try {
			Class.forName(je.getDriver());
			Connection conn = DriverManager.getConnection(je.getUrl(), je.getUserName(), je.getPassWard());
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}	
}
