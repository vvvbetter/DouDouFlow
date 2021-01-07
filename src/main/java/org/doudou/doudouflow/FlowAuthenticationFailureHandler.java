package org.doudou.doudouflow;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class FlowAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler
		implements AuthenticationFailureHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * SimpleUrlAuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.
	 * http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		if (RequestUtils.isAjax(request)) {
			request.setAttribute("message", exception.getMessage());
			request.getRequestDispatcher("/login?ajax_error").forward(request, response);
		} else {
			super.onAuthenticationFailure(request, response, exception);
		}
	}

	/**
	 * 
	 */
	public FlowAuthenticationFailureHandler() {
		super();
	}

	/**
	 * @param defaultFailureUrl
	 */
	public FlowAuthenticationFailureHandler(String defaultFailureUrl) {
		super(defaultFailureUrl);
	}

}
