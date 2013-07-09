package mx.com.anzen.urban.beans;

import java.util.ArrayList;

public class IOsPushNotification {
    private ArrayList<String> device_tokens;
    private IOsPayload aps;
    
    public ArrayList<String> getDevice_tokens() {
	return device_tokens;
    }
    public void setDevice_tokens(ArrayList<String> device_tokens) {
	this.device_tokens = device_tokens;
    }
    public IOsPayload getIos() {
	return aps;
    }
    public void setIos(IOsPayload aps) {
	this.aps = aps;
    }
}
