package jasper.scorrtUtil.JasperScorrt.page.impl;

import java.util.HashMap;

import jasper.scorrtUtil.JasperScorrt.page.PageLoding;
import net.sf.jasperreports.engine.JasperPrint;

public class PageLodingStatic implements PageLoding{

		private HashMap<String, Object> map;
		public HashMap<String, Object> pageLoding(int flag, String values, JasperPrint jasperPrint,String pageStr) {
			map=new HashMap<String, Object>();
			int pageIndex = 0;
			int lastPageIndex = 0;
			
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
			
			map.put("index", pageIndex);
			map.put("jasperPrint",jasperPrint);
			return map;
	}

}
