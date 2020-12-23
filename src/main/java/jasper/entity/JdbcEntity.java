package jasper.entity;

import java.io.Serializable;

public class JdbcEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String driver ="1";
	
	private String host="2";
	
	private int post=1;
	
	private String dateBase="3";
	
	private String url="4";
	
	private String userName="5";
	
	private String passWard="6";

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPost() {
		return post;
	}

	public void setPost(int post) {
		this.post = post;
	}

	public String getDateBase() {
		return dateBase;
	}

	public void setDateBase(String dateBase) {
		this.dateBase = dateBase;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWard() {
		return passWard;
	}

	public void setPassWard(String passWard) {
		this.passWard = passWard;
	}
	
}	
