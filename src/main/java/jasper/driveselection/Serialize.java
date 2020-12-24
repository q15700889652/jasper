package jasper.driveselection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import jasper.driveselection.Cache.JdbcCache;
import jasper.driveselection.entity.JdbcEntity;

/**
 * 序列化读和写 读写都是缓存数据
 * 
 * @author
 *
 */
public class Serialize {
	static final String SERIALIZE_ADDRESS = "D:\\objectFile.obj";
	public static HashMap<String, Object> obj3;
	public static int i;

	static void writeDisk() throws IOException {
		// 序列化对象
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SERIALIZE_ADDRESS));
		out.writeObject(JdbcCache.jdbcHashMap); // 写入customer对象
		out.close();
		
	}

	@SuppressWarnings("unchecked")
	static void readDisk() throws IOException, ClassNotFoundException {
		File file = new File(SERIALIZE_ADDRESS);
		if (file.exists()) {
			// 反序列化对象
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(SERIALIZE_ADDRESS));
			obj3 = (HashMap<String, Object>) in.readObject(); // 读取customer对象
			  for (Entry<String, Object> e : obj3.entrySet()) {
				  //同步数据缓存
				  JdbcCache.jdbcHashMap.put(e.getKey(), e.getValue());
			  }
			 
			in.close();
		} else {
			System.out.println("file not exists, create it ...");
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
