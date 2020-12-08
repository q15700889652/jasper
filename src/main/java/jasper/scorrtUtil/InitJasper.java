package jasper.scorrtUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import jasper.scorrtUtil.JasperExportHanld;

@Component
@PropertySource("classpath:jasper.properties")
public class InitJasper {

	@Value("${jasper.jasperAddress}")
	private String jasperAddress;

	@Value("${jasper.saveaddress}")
	private String saveaddress;

	@Bean
	public JasperExportHanld configureHTML() {
		JasperExportHanld jh = new JasperExportHanld() {
		};
		jh.setJasperAddress(jasperAddress);
		jh.setSaveaddress(saveaddress);
		return jh;

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

}
