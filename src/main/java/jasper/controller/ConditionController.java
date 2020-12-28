package jasper.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jasper.driveselection.DriveSelection;
import jasper.driveselection.JDKDynamicProxy;
import jasper.driveselection.entity.JdbcEntity;
import jasper.driveselection.impl.DriveSelectionImpl;
import jasper.entity.ConditionEntity;

@RestController
@RequestMapping("condition")
public class ConditionController {

	Logger logger = Logger.getLogger(ConditionController.class);
	DriveSelection driveSelection = new JDKDynamicProxy(new DriveSelectionImpl()).getProxy();//代理

	@PostMapping("add")
	public boolean add(JdbcEntity je, String key) throws IOException {
		return driveSelection.add(key, je,ConditionEntity.class);
	}

	@GetMapping("remove")
	public boolean remove(String key) throws IOException {
		String[] keys = key.split(",");
		for (int i = 0; i < keys.length; i++) {
			driveSelection.remove(keys[i],ConditionEntity.class);
		}
		return true;
	}

	@GetMapping("update")
	public boolean update(JdbcEntity je, String oldkey, String newkey) throws IOException {
		return driveSelection.update(oldkey, newkey, je,ConditionEntity.class);
	}

	@GetMapping("query")
	public HashMap<String, Object> query(String key) throws IOException, ClassNotFoundException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		List<JdbcEntity> list = new ArrayList<JdbcEntity>();
		HashMap<String, Object> querymap = driveSelection.query(key,ConditionEntity.class);
		if (key != null && key.length() > 0) {
			if (querymap.get(key) != null) {
				((JdbcEntity) querymap.get(key)).setRemarks(key);
				list.add((JdbcEntity) querymap.get(key));
			}
		} else {
			for (Entry<String, Object> e : querymap.entrySet()) {
				((JdbcEntity) e.getValue()).setRemarks(e.getKey());
				list.add((JdbcEntity) e.getValue());
			}

		}

		map.put("code", 0);
		map.put("msg", "");
		map.put("count", list.size());
		map.put("data", list);
		return map;
	}

}
