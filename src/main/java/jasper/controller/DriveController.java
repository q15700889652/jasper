package jasper.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jasper.driveselection.DriveSelection;
import jasper.driveselection.JDKDynamicProxy;
import jasper.driveselection.Serialize;
import jasper.driveselection.entity.JdbcEntity;
import jasper.driveselection.impl.DriveSelectionImpl;

@RestController
public class DriveController {

	Logger logger = Logger.getLogger(DriveController.class);
	 DriveSelection driveSelection = new JDKDynamicProxy(new DriveSelectionImpl()).getProxy();
	 DriveSelection driveSelection1= new DriveSelectionImpl();
	@GetMapping("add")
	public boolean add(JdbcEntity je, String key) throws IOException {

		return driveSelection.add(key, je);
	}

	@GetMapping("remove")
	public boolean remove(String key) throws IOException {
		return driveSelection.remove(key);
	}

	@GetMapping("update")
	public boolean update(JdbcEntity je, String oldkey, String newkey) throws IOException {
		return driveSelection.update(oldkey, newkey, je);
	}

	@GetMapping("query")
	public HashMap<String, Object> query(String key) throws IOException, ClassNotFoundException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<JdbcEntity> list = new ArrayList<JdbcEntity>();
		if (driveSelection.query(key) != null) {
			for (Entry<String, Object> e : driveSelection.query(key).entrySet()) {
				System.out.println("driver:" + ((JdbcEntity) e.getValue()).getDriver());
				((JdbcEntity) e.getValue()).setRemarks(e.getKey());
				list.add((JdbcEntity) e.getValue());
			}
		}
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", 1000);
		map.put("data", list);
		return map;
	}

	@GetMapping("test")
	public boolean test(JdbcEntity je) throws IOException, ClassNotFoundException {
		return driveSelection.testConnection(je);
	}
}
