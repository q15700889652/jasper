package jasper.scorrtUtil.JasperScorrt;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jasper.scorrtUtil.JasperScorrt.core.CoreLoding;
import jasper.scorrtUtil.JasperScorrt.dataSource.DataSourceLoding;
import jasper.scorrtUtil.JasperScorrt.export.ExportLoding;
import jasper.scorrtUtil.JasperScorrt.page.PageLoding;
import jasper.scorrtUtil.JasperScorrt.param.ParamLoding;
import net.sf.jasperreports.engine.JasperPrint;

public abstract class JasperHanld {
	// 参数
	protected ParamLoding paramLoding;

	public void setParamLoding(ParamLoding paramLoding) {
		this.paramLoding = paramLoding;
	}

	// 核心
	protected CoreLoding coreloding;

	// 数据源
	protected DataSourceLoding dataSource;

	// 页码
	protected PageLoding pageLoding;

	// 导出
	protected ExportLoding exportLoding;

	protected String page;

	protected String name;
	// 获取文件位置
	protected String jasperAddress = "/jaspermodel/";
	// 保存编译好的文件地址
	protected String saveaddress = "D:\\";
	// jasper对象
	protected JasperPrint jasperPrint;
	// 数据封装
	protected Object ob;
	// 数据获取
	// 装载
	protected HashMap<String, Object> parments;
	// web断需要的参数
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;

	protected String values;

	protected int index=0;

	public abstract void Start();

	public void coreLoding() {
		coreloding.coreLoding(name, jasperAddress, saveaddress);
	}

	public void dataSource() {
		jasperPrint = dataSource.dataSourceLoding(saveaddress + name + ".jasper", parments, ob);
	}

	public CoreLoding getCoreloding() {
		return coreloding;
	}

	public void setCoreloding(CoreLoding coreloding) {
		this.coreloding = coreloding;
	}

	public DataSourceLoding getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSourceLoding dataSource) {
		this.dataSource = dataSource;
	}

	public PageLoding getPageLoding() {
		return pageLoding;
	}

	public void setPageLoding(PageLoding pageLoding) {
		this.pageLoding = pageLoding;
	}

	public ExportLoding getExportLoding() {
		return exportLoding;
	}

	public void setExportLoding(ExportLoding exportLoding) {
		this.exportLoding = exportLoding;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJasperAddress() {
		return jasperAddress;
	}

	public void setJasperAddress(String jasperAddress) {
		this.jasperAddress = jasperAddress;
	}

	public String getSaveaddress() {
		return saveaddress;
	}

	public void setSaveaddress(String saveaddress) {
		this.saveaddress = saveaddress;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public Object getOb() {
		return ob;
	}

	public void setOb(Object ob) {
		this.ob = ob;
	}



	public HashMap<String, Object> getParments() {
		return parments;
	}

	public void setParments(HashMap<String, Object> parments) {
		this.parments = parments;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public ParamLoding getParamLoding() {
		return paramLoding;
	}

}
