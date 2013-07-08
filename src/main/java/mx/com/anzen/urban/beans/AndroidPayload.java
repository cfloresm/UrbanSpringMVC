package mx.com.anzen.urban.beans;

import java.util.Map;

public class AndroidPayload {
	private String alert;
	private Map<String, String> extra;
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}
	public Map<String, String> getExtra() {
		return extra;
	}
	public void setExtra(Map<String, String> extra) {
		this.extra = extra;
	}
}
