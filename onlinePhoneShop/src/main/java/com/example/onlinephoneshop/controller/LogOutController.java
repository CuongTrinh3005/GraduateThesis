package com.example.onlinephoneshop.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.onlinephoneshop.security.jwt.BlackListJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class LogOutController {
	@Autowired
	private BlackListJwt blackList;
	
	@PostMapping("logout")
	public Boolean logout(HttpServletRequest request, HttpServletResponse response){
		try{
			String headerAuth = request.getHeader("Authorization"), jwt="";

	        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
	            jwt = headerAuth.substring(7, headerAuth.length());
	        }
	        blackList.addToBlackList(jwt);
		}
		catch(Exception ex){
			System.out.println("Logout error!");
			return false;
		}
        return true;
	}
}