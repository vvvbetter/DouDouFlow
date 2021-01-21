package org.doudou.doudouflow.controller;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.doudou.doudouflow.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public Callable<String> loginPage(ModelMap map) throws IOException {
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "login";
			}
		};
	}

	@RequestMapping(method = RequestMethod.GET)
	public Callable<String> indexPage(ModelMap map) throws IOException {
		return new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "index";
			}
		};
	}

	@GetMapping("/test2")
	@ResponseBody
	public String test2() {
		userRepository.m();
		return "test2";
	}
}
