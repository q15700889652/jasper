package jasper.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jasper.entity.Model;

@Controller


public class HelloController {
	@RequestMapping("hello2")

	public ModelAndView hello(String name) {
		ModelAndView m=new ModelAndView();
		m.addObject("name",name);
		m.setViewName("helloworld");
		return m;
	}
	@RequestMapping("im")
	public ModelAndView websocket(String name) {
		ModelAndView m=new ModelAndView();
		m.addObject("name",name);
		m.setViewName("websocket");
		return m;
	}
	@RequestMapping("echarts")
	public ModelAndView echarts(String name) {
		ModelAndView m=new ModelAndView();
		m.addObject("name",name);
		m.setViewName("echarts");
		return m;
	}
	
	@RequestMapping("layim")
	public ModelAndView im(String name) {
		ModelAndView m=new ModelAndView();
		m.addObject("name",name);
		m.setViewName("im");
		return m;
	}
	/**
	 * ajax请求获取动态条件拼接
	 */
	@RequestMapping("ajax")
	@ResponseBody
	public HashMap<String, Object> ajax(String name){
		HashMap<String, Object> map=new HashMap<String, Object>(); 
		List<Model> list=new ArrayList<Model>();
		
		Model model=new Model();
		if(name.equals("R-HBM-007")) {
			model.setName("staffNo : <input type='text' name='staffNo' id='staffNo'/>");
		}else {
			model.setName("ID : <input type='text' name='id' id='id'/> <br/> AGE : <input type='text' name='age' id='age'/>");
		}
		list.add(model);	
		map.put("data", list);
		return map;
	}

}

