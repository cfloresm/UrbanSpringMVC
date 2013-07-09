package mx.com.anzen.urban.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import mx.com.anzen.urban.beans.LoginForm;
import mx.com.anzen.urban.beans.MessageForm;
import mx.com.anzen.urban.beans.UrbanAppRequest;
import mx.com.anzen.urban.beans.UrbanAppResponse;
import mx.com.anzen.urban.beans.User;
import mx.com.anzen.urban.services.UrbanAirshipService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SingleController {
   private static final Logger logger = Logger.getLogger(SingleController.class);
 
    @Autowired
    HashMap<String, Map<String, Object>> memoryStore;

    @Autowired
    UrbanAirshipService urbanService;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getLogin(ModelMap model) {
	    model.addAttribute("loginForm", new LoginForm());
	    return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String postLogin(@ModelAttribute(value="loginForm") LoginForm loginForm, ModelMap model) {

		if(!memoryStore.containsKey(loginForm.getUsername())){
			model.addAttribute("errorMsg","Account no exists.");
			return "login";
		}
		
		User user = (User)memoryStore.get(loginForm.getUsername()).get("user");

		if(!user.getPassword().equals(loginForm.getPassword())){
			model.addAttribute("errorMsg","Username and/or password are incorrect.");
			logger.info("Username and/or password are incorrect.");
			return "login";
		}

		if(user.getApid() != null){
			model.addAttribute("messageForm", new MessageForm());
			return "notify";
		}
		
		logger.info("Login success.");
	    return "account";
	}



	@RequestMapping(value="/userForm", method = RequestMethod.GET)
	public String getUserForm(ModelMap model) {
	    model.addAttribute("userForm", new User());
	    return "register";
	}

	@RequestMapping(value="/user", method = RequestMethod.POST)
	public String postUser(@ModelAttribute(value = "userForm") User userForm, ModelMap model){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", userForm);

		memoryStore.put(userForm.getEmail(), map);
		logger.info("User registered successfully");
		model.addAttribute("infoMsg", "User registered successfully.");
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	@RequestMapping(value="/urbanapp", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody UrbanAppResponse postUrbanApp(@RequestBody UrbanAppRequest urbanApp) {

		UrbanAppResponse response = new UrbanAppResponse();

		if(!memoryStore.containsKey(urbanApp.getEmail())){
			response.getStatusInfo().setStatusCode(400);
			response.getStatusInfo().setStatusMessage("User "+urbanApp.getEmail()+" unregistered.");
			logger.error("/urbanapp  : 4000 - User Unregistered ");
			return response;
		}

		User user = (User) memoryStore.get(urbanApp.getEmail()).get("user");
		user.setTypeDevice(urbanApp.getType());
		
		if(user.getApid() != null){
			response.getStatusInfo().setStatusCode(100);
			response.getStatusInfo().setStatusMessage("Override APID.");
			logger.info("/urbanapp : 100 - Override APID. ");

		}else{
			response.getStatusInfo().setStatusCode(200);
			response.getStatusInfo().setStatusMessage("Successful.");
			logger.info("/urbanapp : 200 - Successful . ");

		}

		user.setApid(urbanApp.getApid());

	    return response;
	}


	@RequestMapping(value="/urbanappForm", method = RequestMethod.GET)
	public String getUrbanApp(ModelMap model) {
		model.addAttribute("users", memoryStore.keySet());
		model.addAttribute("messageForm", new MessageForm());
		return "notify";
	}


	@RequestMapping(value="/pushnotification", method = RequestMethod.POST)
	public String postPushNotification(@ModelAttribute(value="messageForm") MessageForm messageForm, ModelMap model) {

		String email = messageForm.getEmail();

		if(!memoryStore.containsKey(email)){
			logger.error("pushnotification : User no exists" + email);
			model.addAttribute("error", "User no exists.");
			return "notify";
		}

		User user = (User) memoryStore.get(email).get("user");

		try {
			logger.error("pushnotification :Send notification ...." + email);
			urbanService.sendNotification(user, messageForm.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "notify";
	}

}