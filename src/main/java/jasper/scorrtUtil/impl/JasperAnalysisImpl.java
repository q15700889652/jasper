package jasper.scorrtUtil.impl;

import java.io.File;
import java.io.InputStream;

import jasper.scorrtUtil.JasperAnalysis;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class JasperAnalysisImpl implements JasperAnalysis {
	
	public void Analysis(String name, String JasperAddress, String saveaddress) {
		String filename = saveaddress + name + ".jasper";
		File compiledFile = new File(filename);
		JasperReport jr;
		try {
			if (compiledFile.exists()) {
				jr = (JasperReport) JRLoader.loadObject(compiledFile);// 加在jasper文件
			} else {
				InputStream inputStream = getClass().getResourceAsStream(JasperAddress + name + ".jrxml");// 读取模板 从此类的包根目录
				JasperDesign jd = JRXmlLoader.load(inputStream);// 装载
				jr = (JasperCompileManager.compileReport(jd));// 编译jrxml文件 防止版本差异导致模板填充出现问题
				JRSaver.saveObject(jr, filename);// 备份jasper文件
			}
		} catch (JRException e) {
			e.printStackTrace();
		}

	}

}
