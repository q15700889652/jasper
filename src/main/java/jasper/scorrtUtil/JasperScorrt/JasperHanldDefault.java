package jasper.scorrtUtil.JasperScorrt;

import jasper.scorrtUtil.JasperScorrt.core.impl.CoreLodingDefault;
import jasper.scorrtUtil.JasperScorrt.dataSource.impl.DataSourceLodingJdbc;
import jasper.scorrtUtil.JasperScorrt.export.impl.ExportLodingHtml;
import jasper.scorrtUtil.JasperScorrt.page.impl.PageLodingDynamic;
import jasper.scorrtUtil.JasperScorrt.param.impl.ParamLodingHtmlStatic;

public class JasperHanldDefault extends JasperHanld{
	
	public JasperHanldDefault() {
		if (paramLoding == null) {
			setParamLoding(new ParamLodingHtmlStatic());
		}
		if (coreloding == null) {
			setCoreloding(new CoreLodingDefault());
		}
		if (dataSource == null) {
			setDataSource(new DataSourceLodingJdbc());
		}
		if (pageLoding == null) {
			setPageLoding(new PageLodingDynamic());
		}
		if (exportLoding == null) {
			setExportLoding(new ExportLodingHtml());
		}
	}
	public void Start() {
		
	}

}
