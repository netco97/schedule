package com.example.demo.global.config;

import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    // パスワードの暗号化
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // カスタム認証プロバイダー
    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String password = authentication.getCredentials().toString();

                User user = userService.findByUsername(username);

                // ユーザーが存在しない場合
                if (user == null) {
                    throw new UsernameNotFoundException("ユーザーが存在しません");
                }

                // パスワードが一致しない場合
                if (!passwordEncoder().matches(password, user.getPassword())) {
                    throw new BadCredentialsException("パスワードが間違っています");
                }

                // 認証成功
                return new UsernamePasswordAuthenticationToken(username, password, 
                        org.springframework.security.core.authority.AuthorityUtils.createAuthorityList("ROLE_" + user.getRole()));
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
            }
        };
    }

    // 認証マネージャー
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        ProviderManager manager = new ProviderManager(authenticationProvider());
        return manager;
    }

    // セキュリティ設定
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/join", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .failureHandler(customAuthFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // ログイン失敗時のハンドラ
    @Bean
    public AuthenticationFailureHandler customAuthFailureHandler() {
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request,
                                                HttpServletResponse response,
                                                AuthenticationException exception)
                    throws IOException, ServletException {

                String errorParam = "unknown";

                if (exception instanceof UsernameNotFoundException) {
                    errorParam = "no_user";
                } else if (exception instanceof BadCredentialsException) {
                    errorParam = "bad_password";
                }

                response.sendRedirect("/login?error=" + errorParam);
            }
        };
    }
}
