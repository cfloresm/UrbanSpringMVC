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
			return "login";
		}

		if(user.getApid() != null){
			model.addAttribute("messageForm", new MessageForm());
			return "notify";
		}

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
			return response;
		}

		Map<String, Object> map = memoryStore.get(urbanApp.getEmail());
		User user = (User) map.get("user");

		if(user.getApid() != null){
			response.getStatusInfo().setStatusCode(100);
			response.getStatusInfo().setStatusMessage("Override APID.");
		}else{
			response.getStatusInfo().setStatusCode(200);
			response.getStatusInfo().setStatusMessage("Successful.");
		}

		user.setApid(urbanApp.getApid());
//		map.put("user", user);
//		memoryStore.put(urbanApp.getEmail(), map);


	    return response;
	}

	@RequestMapping(value="/urbanappForm", method = RequestMethod.GET)
	public String getUrbanApp(ModelMap model) {
		model.addAttribute("users", memoryStore);
		model.addAttribute("messageForm", new MessageForm());
		return "notify";
	}


	@RequestMapping(value="/pushnotification", method = RequestMethod.POST)
	public String postPushNotification(@ModelAttribute(value="messageForm") MessageForm messageForm, ModelMap model) {

		String email = messageForm.getEmail();
		String errorMsg = null;

		if(!memoryStore.containsKey(email)){
			errorMsg = "User no exists.";
		}

		User user = (User) memoryStore.get(email).get("user");


		try {
			urbanService.sendNotification(user.getApid(), user.getEmail(), messageForm.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}

		model.addAttribute("error", errorMsg);
		return "notify";
	}

}