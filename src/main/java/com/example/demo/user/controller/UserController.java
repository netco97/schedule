package com.example.demo.user.controller;

import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // 会員登録フォームを表示
    @GetMapping("/join")
    public String joinForm() {
        return "user/join";
    }

    // 会員登録処理
    @PostMapping("/join")
    public String join(User user) {
        userService.join(user, passwordEncoder); // パスワードを暗号化して保存
        return "redirect:/login"; // 登録後にログインページへ遷移
    }

    // ログインフォームを表示
    @GetMapping("/login")
    public String loginForm() {
        return "user/login"; // login.htmlで ?error処理
    }
}
