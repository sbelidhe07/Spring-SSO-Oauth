package com.schary.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.schary.util.CookieUtil;
import com.schary.handler.CustomSuccessHandler;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

@Controller
public class HomeController {

  private static final String accessTokenCookieName = "ACCESS-TOKEN";

	@Autowired
  private CustomSuccessHandler successHandler;
  
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value="redirect_uri" , required=false) String redirect_uri,Model model, String error, String logout,HttpServletRequest httpServletRequest) {
		System.out.println("in login");
		System.out.println(httpServletRequest.getSession());
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

    System.out.println(redirect_uri);

    successHandler.setRedirectURI(redirect_uri);

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@RequestMapping(value = "/hello" , method = RequestMethod.GET)
	public String hello(Model model) {
		return "hello";
	}

  @RequestMapping("/logout")
    public String logout(HttpServletResponse httpServletResponse) {
        CookieUtil.clear(httpServletResponse, accessTokenCookieName);
        return "redirect:/";
    }

}