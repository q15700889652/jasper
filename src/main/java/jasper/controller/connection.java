package jasper.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import jasper.entity.JdbcEntity;

public class connection implements Sjdbc{

	private static boolean flag = true;

	// 增加数据源
	 public void add(String key, JdbcEntity je) throws IOException {
		 System.out.println("add");
		JdbcCache.jdbcHashMap.put(key, je);
	}

	// 移除数据源
	 public void remove(String key) throws IOException {
		 System.out.println("remove");
		 JdbcCache.jdbcHashMap.remove(key);
	}

	// 修改
	 public void update(String oldkey, String newkey, JdbcEntity je) throws IOException {
		 System.out.println("update");
		 JdbcCache.jdbcHashMap.remove(oldkey);
		 JdbcCache.jdbcHashMap.put(newkey, je);
	}

	// 查询
	 public void query(String key) throws ClassNotFoundException, IOException {
		 System.out.println("query");
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

	

	// 选择数据源
	public void connectionAndReport() {
		List<Object[]> conf = new ArrayList<Object[]>();
		Object[] ob = new Object[10];

		ob[1] = "RF-R-EXM-001";
		ob[2] = "";

		conf.add(ob);
	}

	
	
	
}
