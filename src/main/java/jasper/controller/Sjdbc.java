package jasper.controller;



import java.io.IOException;

import jasper.entity.JdbcEntity;

public interface Sjdbc {
	// 增加数据源
	 void add(String key, JdbcEntity je) throws IOException ;

	// 移除数据源
	 void remove(String key) throws IOException;

	// 修改
	 void update(String oldkey, String newkey, JdbcEntity je) throws IOException;

	// 查询
	 void query(String key) throws ClassNotFoundException, IOException ;

	@SuppressWarnings("unused")
	// 测试连接
	boolean testConnection(JdbcEntity je);

	

	// 选择数据源
	void connectionAndReport();

}
