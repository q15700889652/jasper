package jasper.scorrtUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jasper.scorrtUtil.impl.JasperAnalysisImpl;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * export
 * 
 * @author Scorrt
 * @version 1.0
 */
public abstract class JasperExportHanld {
	// 文件名称
	protected String name;
	// 获取文件位置
	protected String jasperAddress="/jaspermodel/";
	// 保存编译好的文件地址
	protected String saveaddress="D:\\";
	// jasper对象
	protected JasperPrint jasperPrint;
	// 数据封装
	protected Object ob;
	//
	private JasperAnalysis jasperAnalysis;
	// 数据获取
	private JasperSourceData jasperDate;
	// 装载
	//private Jasperloding jasperloding;
	protected HashMap<String, Object> parments;
	// 导出
	private JasperExport jasperExport;
	// web断需要的参数
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	
	protected String values;
	
	protected int index;
	/**
	 * 解析
	 */
	public void analysis() {
		jasperAnalysis = new JasperAnalysisImpl();
		jasperAnalysis.Analysis(this.name, this.jasperAddress, this.saveaddress);
	}

	/**
	 * 数据
	 */
	void SourceDate() {
		
	}

	/**
	 * 装载
	 */
	public void loding() {
		this.jasperPrint = this.jasperDate.sourceData("D:\\"+name + ".jasper", parments, ob);
	}

	/**
	 * 导出
	 */
	public void Export() {
		jasperExport.export(this.jasperPrint, this.request, this.response,index,session);
	}

	/**
	 * 抽象启动类 每个子类的具体启动方式不一样
	 */
	public void start() {
		analysis();
		SourceDate();
		loding();
		Export();
	};

	// -----------------------------------------------------------------------------------------------------------

	public void setJasperDate(JasperSourceData jasperDate) {
		this.jasperDate = jasperDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOb(Object ob) {
		this.ob = ob;
	}

	public void setJasperAddress(String jasperAddress) {
		this.jasperAddress = jasperAddress;
	}

	public void setSaveaddress(String saveaddress) {
		this.saveaddress = saveaddress;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setParments(HashMap<String, Object> parments) {
		this.parments = parments;
	}

	public void setJasperExport(JasperExport jasperExport) {
		this.jasperExport = jasperExport;
	}

	public JasperPrint getJasperPrint() {
		return jasperPrint;
	}

	public void setJasperPrint(JasperPrint jasperPrint) {
		this.jasperPrint = jasperPrint;
	}

	public JasperAnalysis getJasperAnalysis() {
		return jasperAnalysis;
	}

	public void setJasperAnalysis(JasperAnalysis jasperAnalysis) {
		this.jasperAnalysis = jasperAnalysis;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public String getJasperAddress() {
		return jasperAddress;
	}

	public String getSaveaddress() {
		return saveaddress;
	}

	public Object getOb() {
		return ob;
	}

	public JasperSourceData getJasperDate() {
		return jasperDate;
	}

	public HashMap<String, Object> getParments() {
		return parments;
	}

	public JasperExport getJasperExport() {
		return jasperExport;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
