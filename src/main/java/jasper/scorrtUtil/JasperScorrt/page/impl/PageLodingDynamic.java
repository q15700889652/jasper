package jasper.scorrtUtil.JasperScorrt.page.impl;

import java.util.HashMap;
import java.util.List;

import jasper.scorrtUtil.JasperScorrt.page.PageLoding;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;

public class PageLodingDynamic implements PageLoding{
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
		
		try {
			if(null != jasperPrint.getPages()&&jasperPrint.getPages().size()>0&&flag==0) {//缓存中的模板存在页码换算，将不再进入计算
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
			e.printStackTrace();
		}
		
		
		map.put("index", pageIndex);
		map.put("jasperPrint",jasperPrint);
		return map;
	}

}
