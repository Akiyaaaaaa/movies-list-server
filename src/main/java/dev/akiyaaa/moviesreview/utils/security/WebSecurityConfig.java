package dev.akiyaaa.moviesreview.utils.security;

import dev.akiyaaa.moviesreview.service.UserDetailServiceImpl;
import dev.akiyaaa.moviesreview.utils.security.jwt.AuthEntryPointJwt;
import dev.akiyaaa.moviesreview.utils.security.jwt.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableMethodSecurity
//@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    UserDetailServiceImpl userDetailService;
    
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){
        return new AuthTokenFilter();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvide = new DaoAuthenticationProvider();
        authProvide.setUserDetailsService(userDetailService);
        authProvide.setPasswordEncoder(passwordEncoder());
        
        return authProvide;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
        return authConfig.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Arrays.asList("https://watch-and-reviews.netlify.app")); // Add your frontend origin here
                            config.addAllowedHeader("*");
                            config.addAllowedMethod("*");
                            return config;
                        }))
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.antMatchers(HttpMethod.POST,"/signup").permitAll()
                        .antMatchers(HttpMethod.POST,"/login").permitAll()
                        .antMatchers(HttpMethod.GET,"/movies").permitAll()
                        .antMatchers(HttpMethod.GET,"/movies/{imdbId}").permitAll()
                        .antMatchers(HttpMethod.POST, "/movies/add").authenticated() // Require authentication for POST
                        .antMatchers(HttpMethod.PUT, "/movies/{imdbId}").authenticated() // Require authentication for PUT
                        .antMatchers(HttpMethod.DELETE, "/movies/{imdbId}").authenticated()
                        .antMatchers(HttpMethod.GET, "/reviews").permitAll()
                        .antMatchers(HttpMethod.POST, "/reviews").authenticated()
                        .antMatchers(HttpMethod.PUT, "/reviews/{imdbId}").authenticated()
                        .antMatchers(HttpMethod.DELETE, "/reviews/{imdbId}").authenticated()
                        .anyRequest().authenticated());
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
