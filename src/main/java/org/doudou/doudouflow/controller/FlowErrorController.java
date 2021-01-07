package org.doudou.doudouflow.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 自定义错误响应<br>
 * <p>
 * 创建时间：2020年11月9日
 * </p>
 * 
 * @author decai
 * @since 1.0
 * @version 1.0
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class FlowErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		return null;
	}

	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		response.setStatus(status.value());
		return new ModelAndView("error/" + status.value());
	}

	@RequestMapping
	public ResponseEntity<String> error(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		if (status == HttpStatus.NO_CONTENT) {
			return new ResponseEntity<>(status);
		}
		String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
		return new ResponseEntity<>(message, status);
	}

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public ResponseEntity<String> mediaTypeNotAcceptable(HttpServletRequest request) {
		HttpStatus status = getStatus(request);
		return ResponseEntity.status(status).build();
	}

	protected HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		try {
			return HttpStatus.valueOf(statusCode);
		} catch (Exception ex) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
	}

}
