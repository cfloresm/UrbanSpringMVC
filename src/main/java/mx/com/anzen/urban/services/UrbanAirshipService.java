package mx.com.anzen.urban.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mx.com.anzen.urban.beans.AndroidPayload;
import mx.com.anzen.urban.beans.AndroidPushNotification;
import mx.com.anzen.urban.conectors.UrbanAirshipConnector;

import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UrbanAirshipService {


	private static final String URBAN_PATH_PUSH = "/api/push/";

	public void sendNotification(String apid, String email, String msg) throws IOException{

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

		// instance to connect to UrbanAirship
		UrbanAirshipConnector urbanConn = new UrbanAirshipConnector();
		String response = urbanConn.send(URBAN_PATH_PUSH, jsonPN, "POST");

	}

}
