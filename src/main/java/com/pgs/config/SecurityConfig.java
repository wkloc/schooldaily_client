package com.pgs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * Created by mmalek on 2/16/2017.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }

    RequestMatcher csrfRequestMatcher = new RequestMatcher() {

        // Always allow the HTTP GET method
        private Pattern allowedMethods = Pattern.compile("^GET$");

        // Disable CSFR protection on the following urls:
        private AntPathRequestMatcher[] requestMatchers = {
                new AntPathRequestMatcher("/login"),
                new AntPathRequestMatcher("/logout"),
                new AntPathRequestMatcher("/verify/**")
        };

        @Override
        public boolean matches(HttpServletRequest request) {
            // Skip allowed methods
            if (allowedMethods.matcher(request.getMethod()).matches()) {
                return false;
            }

            // If the request match one url the CSFR protection will be disabled
            for (AntPathRequestMatcher rm : requestMatchers) {
                if (rm.matches(request)) { return false; }
            }

            return true;
        } // method matches
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().requireCsrfProtectionMatcher(csrfRequestMatcher).and()
                .authorizeRequests()
                .antMatchers("/**").permitAll();
//                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
