package org.doudou.doudouflow;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

	public static boolean isAjax(HttpServletRequest request) {
		String xreqwith = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equalsIgnoreCase(xreqwith);
	}

}
