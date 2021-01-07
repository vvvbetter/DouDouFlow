package org.doudou.doudouflow;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.access.AccessDeniedHandler;

public class FlowAccessDeniedHandler implements AccessDeniedHandler {

	public FlowAccessDeniedHandler() {
	}

	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (!response.isCommitted()) {
			if (!"GET".equalsIgnoreCase(request.getMethod())) {
				// Put exception into request scope (perhaps of use to a view)
				request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);

				// Set the 403 status code.
				response.setStatus(HttpStatus.FORBIDDEN.value());

				response.getWriter().print(HttpStatus.FORBIDDEN.getReasonPhrase());

			} else {
				response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
			}
		}
	}

}