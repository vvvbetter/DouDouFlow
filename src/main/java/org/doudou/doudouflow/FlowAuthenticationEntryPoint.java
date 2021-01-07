package org.doudou.doudouflow;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class FlowAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final Logger LOG = LoggerFactory.getLogger(this.getClass());


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		LOG.info("{} enter into AuthenticationEntryPoint,{}", request.getRequestURI(), authException.getMessage());
		if (RequestUtils.isAjax(request)) {// ajax访问时
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		} else {
			AuthenticationException newExc = new InsufficientAuthenticationException("未登录或登录失效", authException);
			request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", newExc);
			response.sendRedirect(request.getContextPath() + "/login?error&source=1");
			return;
		}

	}

}