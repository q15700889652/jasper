package jasper.driveselection;

import java.io.IOException;

import jasper.driveselection.Cache.JdbcCache;
import jasper.driveselection.impl.DriveSelectionImpl;
import jasper.driveselection.entity.JdbcEntity;

public class test {
	/*
	 * public static void main(String[] args) throws IOException,
	 * ClassNotFoundException { // 选择数据源 JdbcEntity je = new JdbcEntity();
	 * je.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	 * je.setUrl("jdbc:sqlserver://localhost:1433;databaseName=CSDHRMS");
	 * je.setUserName("sa"); je.setPassWard("123456");
	 * 
	 * 
	 * JdbcCache.removeCache(); // jdk动态代理测试 DriveSelection sjdbc = new
	 * JDKDynamicProxy(new DriveSelectionImpl()).getProxy();
	 * 
	 * // 测试连接 System.out.println(sjdbc.testConnection(je)); // 输入唯一id String
	 * jdbcName = "testJdbcName";
	 * 
	 * 
	 * // 增加 sjdbc.add(jdbcName, je); System.out.println(JdbcCache.jdbcHashMap+
	 * "-------------------------------------------------------------------add"); //
	 * 修改 String jdbcName1 = "test1JdbcName"; sjdbc.update(jdbcName, jdbcName1, je);
	 * System.out.println(JdbcCache.jdbcHashMap+
	 * "-------------------------------------------------------------------update");
	 * // 查询 sjdbc.query(jdbcName1); System.out.println(JdbcCache.jdbcHashMap+
	 * "-------------------------------------------------------------------query");
	 * // 删除 sjdbc.remove(jdbcName1); System.out.println(JdbcCache.jdbcHashMap+
	 * "-------------------------------------------------------------------query");
	 * // sjdbc.query(jdbcName1); }
	 */
	public static void main(String[] args) {
		System.out.println(JdbcEntity.class.getName());
	}

}
