package com.example.Preproj223.config;

import com.example.Preproj223.security.Role;
import com.example.Preproj223.security.UserSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .successHandler((request, response, authentication) -> {
                    Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

                    if (roles.contains("ROLE_ADMIN")) {
                        response.sendRedirect("/users/admin");
                    } else {
                        response.sendRedirect("/users");
                    }
                })
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies();


    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                new UserSecurity("admin", passwordEncoder().encode("admin"), true, Set.of(Role.ADMIN)),
                new UserSecurity("user", passwordEncoder().encode("user"), true, Set.of(Role.USER))
        );
    }
}
