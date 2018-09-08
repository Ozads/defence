package com.ozads.futsalnepal.services;

import java.security.NoSuchAlgorithmException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ozads.futsalnepal.dto.LoginResponseDto;
import com.ozads.futsalnepal.exceptions.ExpireException;
import com.ozads.futsalnepal.exceptions.LoginFailException;
import com.ozads.futsalnepal.exceptions.LogoutFailException;
import com.ozads.futsalnepal.exceptions.NotFoundException;
import com.ozads.futsalnepal.exceptions.VerificationException;
import com.ozads.futsalnepal.model.Login;

import com.ozads.futsalnepal.repository.LoginRepository;
import com.ozads.futsalnepal.repository.VerificationRepository;

import com.ozads.futsalnepal.util.DateUtil;

import com.ozads.futsalnepal.util.LoginStatus;
import com.ozads.futsalnepal.util.LoginType;
import com.ozads.futsalnepal.util.Md5Hashing;
import com.ozads.futsalnepal.util.Status;
import com.ozads.futsalnepal.util.TokenGenerator;



@Service
public class LoginService {
	private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);
	@Autowired
	LoginRepository loginRepository;

	@Autowired
	VerificationRepository verificationRepository;

	
	
	@Value("${futsalnepal.token.expire.enable}")
	private String tokenExpireEnable;

	@Value("${futsalnepal.token.expire.after}")
	private int tokenExpireAfter;

	@Value("${futsalnepal.login.password.length}")
	private int passwordLength;

	 @Autowired
	  HttpServletRequest request;
	 
	 private  String getClientIp() {

	        String remoteAddr = "";

	        if (request != null) {
	            remoteAddr = request.getHeader("X-FORWARDED-FOR");
	            if (remoteAddr == null || "".equals(remoteAddr)) {
	                remoteAddr = request.getRemoteAddr();
	            }
	        }
	        return remoteAddr;
	}
	 
	 @Transactional
		public LoginResponseDto logInUser(String username, String password, Status active) {
			LOG.debug("Request for Login");
			Login login = loginRepository.findByUsernameAndStatusNot(username, Status.DELETED);
			if (login == null) {

				throw new LoginFailException("Sorry,Username not found !!");
			}

			Login l = loginRepository.findByUsernameAndStatus(username, Status.BLOCKED);
			if (l != null) {
				throw new VerificationException("Sorry Your Account is not verified Check Your Email");
			}

			try {
				
				System.out.println(login.getPassword());
				
				System.out.println("Swagger:"+password);
				System.out.println(Md5Hashing.getPw(password));
				if (Md5Hashing.getPw(password).equals(login.getPassword())) {
					login.setLastLogin(new Date());
					login.setLoginStatus(LoginStatus.LOGGEDIN);
					
					String ip=getClientIp();
					System.out.println("my ip="+ip);
					Client client = ClientBuilder.newClient();
					  Response response = client.target("https://api.ipdata.co/")
					    .request(MediaType.TEXT_PLAIN_TYPE)
					    .header("Accept", "application/json")
					    .get();
					  
					  System.out.println("body:" + response.readEntity(String.class));
					
					
					
					login.setToken(TokenGenerator.getToken());
					if (tokenExpireAfter > 0) {
						login.setTokenExpirationDateTime(
								DateUtil.currentDateTimePlusMinutes(tokenExpireAfter));
					}
					LoginResponseDto responce = getLoginResponse(login);
					LOG.debug("Login Accepted");
					return responce;
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			throw new LoginFailException("Username and Password missmatch");
	}
	 
	 private LoginResponseDto getLoginResponse(Login login) {
			LoginResponseDto loginResponseDto=new LoginResponseDto();
			if(login.getLoginType().equals(LoginType.CUSTOMER)) {
				loginResponseDto=new LoginResponseDto.Builder().id(login.getCustomer().getId())
						.token(login.getToken()).build();
			}
			return loginResponseDto;
	}
	 
	 public Login logout(Long userId) {
			LOG.debug("request for logout");
			if (userId != null) {
				Login user = loginRepository.findLoginById(userId);
				if (user == null) {
					throw new LogoutFailException("User id mismatch");
				}
				user.setLoginStatus(LoginStatus.LOGOUT);
				user.setToken("");
				user.setTokenExpirationDateTime(new Date());
				loginRepository.save(user);
				LOG.debug("logout");
				return user;
			}

			return null;
	}
	 
	 @Transactional
		public void saveLogin(Login login) {

			loginRepository.save(login);
	}
	 
	 public void chekToken(String token) {
			Login login=loginRepository.findByToken(token);
			if(login==null) {
				throw new NotFoundException("Token is invallied");
			}
			if (!DateUtil.isCurrentTimeBeforeThanGivenTime(login.getTokenExpirationDateTime()))
				throw new ExpireException("token Expired");

		
	 }
	 
	 public boolean isValidToken(Long loginId, String token) {
			
			if(loginId==null || token==null) {
				return false;
			}
			Login login = loginRepository.findByIdAndToken(loginId, token);
			if (null == login) {
				return false;
			}
			LOG.debug("Login found.");
			if (null != tokenExpireEnable) {
				if (tokenExpireEnable.equalsIgnoreCase("ENABLE")) {
					if (!DateUtil.isCurrentTimeBeforeThanGivenTime(
							login.getTokenExpirationDateTime()))
						return false;
				}
			}
			return true;
	}
}
