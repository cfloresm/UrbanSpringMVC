package mx.com.anzen.urban.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mx.com.anzen.urban.beans.AndroidPayload;
import mx.com.anzen.urban.beans.AndroidPushNotification;
import mx.com.anzen.urban.beans.IOsPayload;
import mx.com.anzen.urban.beans.IOsPushNotification;
import mx.com.anzen.urban.beans.User;
import mx.com.anzen.urban.conectors.UrbanAirshipConnector;
import mx.com.anzen.urban.controller.SingleController;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UrbanAirshipService {

    	private static final Logger logger = Logger.getLogger(UrbanAirshipService.class);

	private static final String URBAN_PATH_PUSH = "/api/push/";

	public void sendNotification(User user, String msg) throws IOException{

	    	switch(user.getTypeDevice()){
	    	//ANDROID
	    	case 1:sendNotificationAndroid(user.getApid(),user.getEmail(),msg);
	    	break;
	    	
	    	//IOS
	    	case 2:sendNotificationIOS(user.getApid(),user.getEmail(),msg);
	    	break;
	    	
	    	//BLACKBERRY
	    	case 3:sendNotificationBlackberry(user.getApid(),user.getEmail(),msg);
	    	break;
	    	
	    	//WINDOWS
	    	case 4:sendNotificationWindows(user.getApid(),user.getEmail(),msg);
	    	break;
	    		    	
	    	}    
	}

	private void sendNotificationAndroid(String apid, String email, String msg) throws IOException {

		// Android payload estras;
		Map<String, String> extra = new HashMap<String,String>();
		extra.put("email", email);
		extra.put("message", msg);

		// Android payload;
		AndroidPayload androidpayload = new AndroidPayload();
		androidpayload.setAlert("Push Notification Demo.");
		androidpayload.setExtra(extra);

		// Android apids;
		ArrayList<String> apids = new ArrayList<String>();
		apids.add(apid);

		// Android push notification;
		AndroidPushNotification apn = new AndroidPushNotification();
		apn.setApids(apids);
		apn.setAndroid(androidpayload);

		// ObjectMapper to get JSON String.
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPN = objectMapper.writeValueAsString(apn);

		logger.info("Send Json Msg to Android : "+ jsonPN);
		// instance to connect to UrbanAirship
		UrbanAirshipConnector urbanConn = new UrbanAirshipConnector();
		String response = urbanConn.send(URBAN_PATH_PUSH, jsonPN, "POST");
		logger.info("Response Urban : "+ response);

	}
	
	private void sendNotificationIOS(String apid, String email, String msg) throws IOException {
	    
	    	//IOs Payload 
	    	IOsPayload payload = new IOsPayload();
	    	payload.setBadge(10);
	    	payload.setAlert(msg);
	    	payload.setSound("cat.caf");
    	    
	    	//IOs push notification
	    	IOsPushNotification iopn = new IOsPushNotification();
	    	ArrayList<String> device_tokens =  new ArrayList<String>();
	    	device_tokens.add(apid);
	    	iopn.setDevice_tokens(device_tokens);
	    	iopn.setIos(payload);
    
	    	// ObjectMapper to get JSON String.
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	String jsonPN = objectMapper.writeValueAsString(iopn);
    	    
		logger.info("Send Json Msg to IOS : "+ jsonPN);

	    	// instance to connect to UrbanAirship
	    	UrbanAirshipConnector urbanConn = new UrbanAirshipConnector();
	    	String response = urbanConn.send(URBAN_PATH_PUSH, jsonPN, "POST");
		logger.info("Response Urban : "+ response);

    	    
	}

	private void sendNotificationWindows(String apid, String email,
		String msg) {
	    	throw new NotImplementedException();
	}

	private void sendNotificationBlackberry(String apid, String email,
		String msg) {
	    	throw new NotImplementedException();
	}
}
