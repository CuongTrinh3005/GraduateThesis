package com.example.onlinephoneshop.security.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class BlackListJwt {
	@Value("${rookies.app.jwtSecret}")
    private String jwtSecret;
	public static List<String> jwtLoggedOutList = new ArrayList<String>();
	
	public BlackListJwt() {
		// TODO Auto-generated constructor stub
	}

	public void addToBlackList(String jwt){
		jwtLoggedOutList.add(jwt);
	}
	
	public static Boolean existedInJwtBlackList(String jwt){
		if(jwtLoggedOutList.contains(jwt))
			return true;
		return false;
	}
	
	public Boolean isExpire(String jwt){
		Date expiration = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getExpiration();
		Date now = new Date();
		if(expiration.before(now))
			return true;
		return false;
	}
	
	@Scheduled(cron="0 0 2 * *") // every 2 days at midnight, every month.
	public void deleteExpireToken(){
		for (String jwt : jwtLoggedOutList) {
			if(isExpire(jwt))
				jwtLoggedOutList.remove(jwt);
		}
	}
}
