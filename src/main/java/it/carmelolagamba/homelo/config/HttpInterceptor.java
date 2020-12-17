package it.carmelolagamba.homelo.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import it.carmelolagamba.homelo.persistence.TokenDocumentService;

public class HttpInterceptor implements HandlerInterceptor {

	private Logger logger = LoggerFactory.getLogger(HttpInterceptor.class);

	@Autowired
	private TokenDocumentService tokenDocumentService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		
		if(request.getRequestURI().contains("ping")) {
			return true;
		}
		
		logger.debug("Check JWT start");

		String authorization = request.getHeader("Authorization");

		String jwt = "";

		if (authorization != null) {
			jwt = authorization.substring(authorization.indexOf(" ") + 1, authorization.length());
		}

		boolean check = tokenDocumentService.isTokenValid(jwt);

		logger.debug("Check JWT end");

		if (!check) {
			response.setStatus(401);
			response.getWriter().write("401 Unauthorized");
			return false;
		}

		return true;
	}
}
