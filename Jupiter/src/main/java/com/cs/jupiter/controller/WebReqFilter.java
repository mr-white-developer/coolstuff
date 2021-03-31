package com.cs.jupiter.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.jupiter.model.data.RequestCredential;
import com.cs.jupiter.utility.Authentication;
import com.cs.jupiter.utility.CommonUtility;
import com.zaxxer.hikari.util.UtilityElf;

@Component
public class WebReqFilter implements Filter {
	@Autowired
	Authentication authen;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;

		try {
			String token = req.getHeader(authen.TOKEN_NAME);
			if (!token.equals("") && token != null) {
				RequestCredential cred = authen.readToken(token, resp);

				if (resp.getStatus() == 200) {
					resp.setHeader(authen.TOKEN_NAME, token);
					req.setAttribute("credential", cred);
					chain.doFilter(request, response);
				}
				if (resp.getStatus() == 401) {

				}

			} else {
				RequestCredential cred = new RequestCredential("", "", true, CommonUtility.getCurrentDateTime());
				String tokenGust = authen.createToken(cred.getUserId(), cred.getRole(), cred.isGust(),
						cred.getAccess_datetime());
				resp.setHeader(authen.TOKEN_NAME, tokenGust);
				req.setAttribute("credential", cred);
				chain.doFilter(request, response);
			}
		} catch (NullPointerException e) {

		}

	}

}
