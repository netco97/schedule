package com.example.demo.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // すべてのリクエストを許可（開発用）
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            // CSRF無効化（開発用）
            .csrf(csrf -> csrf.disable())
            // ルートアクセス時のリダイレクト
            .formLogin(form -> form
                .loginPage("/home")  // 初回起動時ホーム画面に誘導
            );
        return http.build();
    }
}
