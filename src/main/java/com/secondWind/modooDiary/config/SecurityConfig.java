package com.secondWind.modooDiary.config;

import com.secondWind.modooDiary.common.component.PrincipalOAuth2DetailsService;
import com.secondWind.modooDiary.common.handler.JwtAccessDeniedHandler;
import com.secondWind.modooDiary.common.handler.JwtAuthenticationEntryPoint;
import com.secondWind.modooDiary.common.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final StringRedisTemplate redisTemplate;
    private final PrincipalOAuth2DetailsService principalOAuth2DetailsService;

    private static final String[] AUTH_WHITELIST = {
        "/api/auth/login", "/api/auth/signup", "/api/auth/reissue", "/swagger-ui/**", "/v3/api-docs/**",
            "/api/auth/oauth2/google"
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider, redisTemplate))
                .and()
                .authorizeHttpRequests(authorize ->
                        authorize
                        .shouldFilterAllDispatcherTypes(false)
                        .requestMatchers(AUTH_WHITELIST)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
