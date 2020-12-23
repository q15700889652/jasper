package jasper.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map.Entry;

import jasper.entity.JdbcEntity;

public class xuliehua {
	 static void writeDisk() throws IOException {
			// 序列化对象
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("D:\\objectFile.obj"));
			out.writeObject(JdbcCache.jdbcHashMap); // 写入customer对象
			out.close();
		}

		@SuppressWarnings("unchecked")
		static void readDisk() throws IOException, ClassNotFoundException {
			File file = new File("D:\\objectFile.obj");
			if (file.exists()) {
				// 反序列化对象
				ObjectInputStream in = new ObjectInputStream(new FileInputStream("D:\\objectFile.obj"));
				HashMap<String, JdbcEntity> obj3 = (HashMap<String, JdbcEntity>) in.readObject(); // 读取customer对象
				for (Entry<String, JdbcEntity> e : obj3.entrySet()) {
					System.out.println("id:" + e.getKey());
					System.out.println("driver:" + e.getValue().getDriver());
				}
				in.close();
				        } else {
				            System.out.println("file not exists, create it ...");
				            try {
				                 file.createNewFile();
				             } catch (IOException e) {
				                 // TODO Auto-generated catch block
			                e.printStackTrace();
			            }
				        }
				 
				    
			
		}
}
