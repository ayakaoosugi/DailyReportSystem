package com.techacademy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;



@Controller
public class IndexController {
	private final ReportService service;

	@Autowired
	public IndexController(ReportService service) {
		this.service = service;
	}
	 @GetMapping("/")
	    public String getIndex(@AuthenticationPrincipal UserDetail userDetail, Model model) {
	       String name=userDetail.getEmployee().getName();
	        model.addAttribute("name",name);
	        model.addAttribute("reportlist", service.getReportEmployeeList(userDetail.getEmployee()));
		 // index.htmlに画面遷移
	        return "index";
	        }
	}
