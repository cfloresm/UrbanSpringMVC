package mx.com.anzen.urban.beans;

public class UrbanAppRequest {
    	
	private String apid;
	private String email;
	private int type;
	
	public int getType() {
	    return type;
	}
	public void setType(int type) {
	    this.type = type;
	}
	public String getApid() {
		return apid;
	}
	public void setApid(String apid) {
		this.apid = apid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
