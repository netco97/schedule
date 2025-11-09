package com.example.demo.global.config;

import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    // ユーザー情報を取得するUserDetailsServiceのBean
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException(username + " は存在しません");
            }
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        };
    }

    // パスワードの暗号化に使用するPasswordEncoderのBean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManagerを明示的にBean登録
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // セキュリティ設定
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/join", "/css/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")           // カスタムログインページ
                .defaultSuccessUrl("/home", true) // ログイン成功後の遷移
                .failureUrl("/login?error")    // ログイン失敗時のリダイレクト
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")          // ログアウトURL
                .logoutSuccessUrl("/login?logout") // ログアウト後の遷移
            )
            .csrf(csrf -> csrf.disable());     // 開発時のみCSRF無効化

        return http.build();
    }
}
