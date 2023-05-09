package com.techacademy.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.techacademy.service.UserDetail;



@Controller
public class IndexController {
	 @GetMapping("/")
	    public String getIndex(@AuthenticationPrincipal UserDetail userDetail, Model model) {
	       String name=userDetail.getEmployee().getName();
	       model.addAttribute("name",name);
		 // index.htmlに画面遷移
	        return "index";
	        }
	}
