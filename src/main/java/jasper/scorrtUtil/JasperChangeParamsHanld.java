package jasper.scorrtUtil;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;

public class JasperChangeParamsHanld extends JasperExportHanld implements ParamsHanldService,JasperPageHanld{
	
	public void start(){
		
		//参数处理
		Map<String, Object> map=  loadingChangeParams();
		String key=map.get("keys").toString();
		
		//缓存前判断
		boolean flag= Cache.beforeCaChe(key);
		//解析jasper
		if(!flag) {
			//解析
			analysis();
			//加载
			loding();
		}else {
			jasperPrint=(JasperPrint) Cache.map.get(key);
			Cache.flag++;
		}
		//页码处理
		ChangeParams(Cache.flag, values);
		//缓存后处理
		Cache.afterCaChe(key, jasperPrint);
		//导出
		Cache.flag=0;
		Export();
		
		
		
	}

	@Override
	public void ChangeParams(int y, String values) {
		int pageIndex = 0;
		int lastPageIndex = 0;
		String pageStr = request.getParameter("page");


		if (null != jasperPrint.getPages()) {
				
				lastPageIndex = jasperPrint.getPages().size() - 1;
			
		}
		if (null == pageStr) {
			pageStr = "0";
		}
		try {
			pageIndex = Integer.valueOf(pageStr);
			if (pageIndex > 0) {
				pageIndex = pageIndex - 1;
			}
		} catch (Exception e) {
			// 如果得到的非数字字符串
			if ("lastPage".equals(pageStr)) {
				pageIndex = lastPageIndex;
			}
		}

		if (pageIndex < 0) {
			pageIndex = 0;
		}
		if (pageIndex > lastPageIndex) {
			pageIndex = lastPageIndex;
		}
		
		try {
			if(null != jasperPrint.getPages()&&jasperPrint.getPages().size()>0&&y==0) {//缓存中的模板存在页码换算，将不再进入计算
			//
				List<JRPrintElement>	jasperPrint8=	jasperPrint.getPages().get(pageIndex).getElements();
				JRTemplatePrintText jpt;
				for (JRPrintElement e : jasperPrint8) {
					jpt=(JRTemplatePrintText) e;
					 if(jpt.getHyperlinkReference()!=null&&jpt.getHyperlinkReference().length()>0) {
						 jpt.setHyperlinkReference(jpt.getHyperlinkReference()+values);
					 }
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		index=pageIndex;
		
	}

	@Override
	public Map<String, Object> loadingChangeParams() {
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		Enumeration<String> paraNames = request.getParameterNames();
		int x = 0;
		String str = " 1=1 ";
		String values = "";
		String keys = "";
		for (Enumeration<String> e = paraNames; e.hasMoreElements();) {
			String thisName = e.nextElement().toString();
			String thisValue = request.getParameter(thisName);
			keys+=thisName+thisValue;
			if (!thisName.equals("page")&&!thisName.equals("name")) {//刷选掉page参数
				if (x > 0) {//刷选掉报表名称参数
					if (thisValue != null && thisValue.trim().length() > 0) {
						str += " and " + thisName + "=" + thisValue;
					    values += "&" + thisName + "=" + thisValue;
					}
				}
			}
			x++;
		}
		parameters.put("str", str);//动态sql
		parameters.put("values", values);//动态页面参数的
		parameters.put("keys", keys);//缓存的key
		System.out.println("str:"+ str);
		System.out.println("values:"+ values);
		System.out.println("keys:"+ keys);
		parments=(HashMap<String, Object>) parameters;
		super.values=values;
		return parameters;
	}

	@Override
	public Map<String, Object> loadingFixedParams() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
