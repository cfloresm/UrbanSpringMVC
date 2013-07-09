package test;

import java.io.IOException;

import mx.com.anzen.urban.beans.StatusInfo;
import mx.com.anzen.urban.beans.UrbanAppRequest;
import mx.com.anzen.urban.beans.UrbanAppResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Serializator {
    
    public static void main(String []args) throws JsonGenerationException, JsonMappingException, IOException{
	ObjectMapper mapper = new ObjectMapper();
	UrbanAppRequest request =  new  UrbanAppRequest();
	UrbanAppResponse response = new UrbanAppResponse();
	StatusInfo statusInfo =  new StatusInfo();
	statusInfo.setStatusCode(0);
	statusInfo.setStatusMessage("success");
	response.setStatusInfo(statusInfo );
	request.setApid("1231234124123");
	request.setEmail("cfloresm@anzen.com.mx");
	request.setType(1);
	mapper.writeValue(System.out,request );
    }

}
