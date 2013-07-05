package com.test.rest.controller;

import java.util.HashMap;
import java.util.Map;

import mx.com.anzen.urban.beans.MessageForm;
import mx.com.anzen.urban.beans.RegisterForm;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SingleController {
    
    	private Map<String,String> users =  new HashMap<String, String>();
    
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(ModelMap model) {
	    model.addAttribute("registerForm", new RegisterForm());
	    return "register";
	}
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public String register(@ModelAttribute(value = "loginForm") RegisterForm registerForm, ModelMap model) {
	    users.put(registerForm.getAppId(),registerForm.getUserId());
	    model.addAttribute("users", users);
	    model.addAttribute("messageForm",new MessageForm());
	    return "notify";
	}
	
	@RequestMapping(value="/notify", method = RequestMethod.POST)
	public String notify(@ModelAttribute(value = "messageForm") MessageForm messageForm, ModelMap model) {
	    model.addAttribute("confirm", "Send message notification correctly ...");
	    return "notify";
	}
	
}