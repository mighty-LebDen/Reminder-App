package ru.lebedev.reminder.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.lebedev.reminder.service.impl.OAuth2UserServiceImpl;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final OAuth2UserServiceImpl oAuth2UserServiceImpl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                                               authorizeRequests
                                                       .requestMatchers("/", "/login", "/oauth/**")
                                                       .permitAll()
                                                       .requestMatchers(
                                                               "/v3/api-docs/**",
                                                               "/swagger-ui/**",
                                                               "/swagger-ui.html"
                                                       )
                                                       .permitAll()
                                                       .anyRequest()
                                                       .authenticated()
                )
                .oauth2Login(config -> config
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserServiceImpl))
                        .defaultSuccessUrl("/"))

                .logout(logout ->
                                logout
                                        .logoutUrl("/logout")
                                        .logoutSuccessUrl("/login")
                                        .invalidateHttpSession(true)
                                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

}