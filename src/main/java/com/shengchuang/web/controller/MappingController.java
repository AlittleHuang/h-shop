package com.shengchuang.web.controller;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import config.springmvc.SpringMvcConfig;

@Controller
public class MappingController extends BaseController {

	@GetMapping("/**/*.html")
	public String mapping(HttpServletRequest request) {
		String requestURI = request.getServletPath();
		ServletContext servletContext = request.getServletContext();
		String path = SpringMvcConfig.TEMPLATE_LOADER_PATH + requestURI;
		String realPath = servletContext.getRealPath(path);
		if (!new File(realPath).exists()) {
			requestURI = "/404";
		} else {
			requestURI = requestURI.substring(0, requestURI.length() - 5);
		}
		return requestURI;
	}

	@GetMapping({ "", "/" })
	public String index() {
		return "redirect:/index.html";
	}

}
