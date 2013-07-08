package mx.com.anzen.urban.beans;

import java.util.ArrayList;

public class AndroidPushNotification {

	private ArrayList<String> apids;
	private AndroidPayload android;
	public ArrayList<String> getApids() {
		return apids;
	}
	public void setApids(ArrayList<String> apids) {
		this.apids = apids;
	}
	public AndroidPayload getAndroid() {
		return android;
	}
	public void setAndroid(AndroidPayload android) {
		this.android = android;
	}

}
