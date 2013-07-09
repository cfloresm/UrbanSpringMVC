package mx.com.anzen.urban.beans;

public class User {
	private String username;
	private String password;
	private String email;
	private String apid;
	private int typeDevice;
	
	public String getApid() {
		return apid;
	}
	public void setApid(String apid) {
		this.apid = apid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTypeDevice() {
	    return typeDevice;
	}
	public void setTypeDevice(int typeDevice) {
	    this.typeDevice = typeDevice;
	}
}
