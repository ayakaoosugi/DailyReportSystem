package com.techacademy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Authentication;
import com.techacademy.service.AuthenticationService;

@Controller
@RequestMapping("authentication")
public class AuthenticationController {
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service) {
        this.service = service;
    }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("userlist", service.getAuthenticationList());
        // user/list.htmlに画面遷移
        return "authentication/list";
    }
// ----- 追加:ここから -----
/** User登録画面を表示 */
@GetMapping("/register")
public String getRegister(@ModelAttribute Authentication authentication) {
    // User登録画面に遷移
    return "authentication/register";
}

/** User登録処理 */
@PostMapping("/register")
public String postRegister(Authentication authentication) {
    // User登録
    service.saveAuthentication(authentication);
    // 一覧画面にリダイレクト
    return "redirect:/authentication/list";
}
// ----- 追加:ここまで -----
}
