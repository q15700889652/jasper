package jasper.scorrtUtil.JasperScorrt.initConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import jasper.scorrtUtil.JasperScorrt.JasperHanld;
@Component
@PropertySource("classpath:jasper.properties")

public class JasperFactory {
	
	@Value("${jasper.jasperAddress}")
	private String jasperAddress;

	@Value("${jasper.saveaddress}")
	private String saveaddress;

	@Bean
	public JasperHanld configureHTML() {
		JasperHanld jh = new JasperHanld() {

			@Override
			public void Start() {
				// TODO Auto-generated method stub
				
			}
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
