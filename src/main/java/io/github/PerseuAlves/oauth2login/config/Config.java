package io.github.PerseuAlves.oauth2login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class Config {

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {		
        http
            .authorizeHttpRequests((authz) -> authz
        		.antMatchers("/", "/error", "/webjars/**", "/h2-console/**").permitAll()
                .anyRequest().authenticated())
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
            .logout(logout -> logout
            		//.logoutSuccessHandler()
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.deleteCookies("JSESSIONID", "XSRF-TOKEN")
                    .logoutSuccessUrl("/").permitAll())
            .csrf(c -> c
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
            .oauth2Login();
        
        return http.build();
    }
}
