package jasper.driveselection.impl;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import jasper.driveselection.DriveSelection;
import jasper.driveselection.Serialize;
import jasper.driveselection.Cache.JdbcCache;
import jasper.driveselection.entity.JdbcEntity;


/**
 * 增删改查
 * @author scorrt
 *
 */
public class DriveSelectionImpl implements DriveSelection {

	private boolean flag = true;

	// 增加数据源
	public boolean add(String key, Object entity,Class<?> class1) throws IOException {
		System.out.println("add");
		try {
			JdbcCache.jdbcHashMap.put(key+JdbcCache.entityname, entity);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	// 移除数据源
	public boolean remove(String key,Class<?> class1) throws IOException {
		System.out.println("remove");
		JdbcCache.jdbcHashMap.remove(key+JdbcCache.entityname);
		flag = true;
		return flag;
	}

	// 修改
	public boolean update(String oldkey, String newkey, Object je,Class<?> class1) throws IOException {
		System.out.println("update");
		JdbcCache.jdbcHashMap.remove(oldkey+JdbcCache.entityname);
		JdbcCache.jdbcHashMap.put(newkey+JdbcCache.entityname, je);
		return flag;
	}

	// 查询
	public HashMap<String, Object> query(String key,Class<?> class1) throws ClassNotFoundException, IOException {
		return Serialize.obj3;
	}

	@SuppressWarnings("unused")
	// 测试连接
	public boolean testConnection(JdbcEntity je,Class<?> class1) {
		System.out.println("test");
		// 加载驱动类
		try {
			Class.forName(je.getDriver());
			Connection conn = DriverManager.getConnection(je.getUrl(), je.getUserName(), je.getPassWard());
			flag = true;
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}
}
