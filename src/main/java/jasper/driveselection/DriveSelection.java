package jasper.driveselection;



import java.io.IOException;
import java.util.HashMap;

import jasper.driveselection.entity.JdbcEntity;

public interface DriveSelection {
	 // 增加数据源
	 boolean add(String key, Object je,Class<?> class1) throws IOException ;

	 // 移除数据源
	 boolean remove(String key,Class<?> class1) throws IOException;

	 // 修改
	 boolean update(String oldkey, String newkey, Object je,Class<?> class1) throws IOException;

	 // 查询
	 HashMap<String, Object> query(String key,Class<?> class1) throws ClassNotFoundException, IOException ;

	 // 测试连接
	 boolean testConnection(JdbcEntity je,Class<?> class1);

}
