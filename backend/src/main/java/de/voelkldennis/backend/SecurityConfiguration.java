package de.voelkldennis.backend;

import de.voelkldennis.backend.jwt.filter.JwtAccessDeniedHandler;
import de.voelkldennis.backend.jwt.filter.JwtAuthenticationEntryPoint;
import de.voelkldennis.backend.jwt.filter.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(JwtAuthorizationFilter jwtAuthorizationFilter,
                                 JwtAccessDeniedHandler jwtAccessDeniedHandler,
                                 JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                 @Qualifier("userDetailsService") UserDetailsService userDetailsService) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().authorizeRequests()
                //.antMatchers(HttpMethod.DELETE).hasRole(String.valueOf(SUPER_ADMIN_AUTHORITIES))
                //.antMatchers(HttpMethod.POST).hasAnyRole(String.valueOf(USER_AUTHORITIES))
                //.antMatchers(HttpMethod.PUT).hasAnyRole(String.valueOf(USER_AUTHORITIES))
                //.antMatchers(HttpMethod.GET).hasAnyRole("USER_READ", "USER_UPDATE", "USER_UPDATE", "USER_DELETE")
                //.antMatchers("/api/projects/memberOverview/**", "/api/projects/projectOverview/**", "/api/projects/addProject/**", "/api/projects/projectCard/**").hasAnyRole("USER_READ", "USER_UPDATE", "USER_UPDATE", "USER_DELETE")
                .antMatchers("/user/login/**", "/user/register/**", "/user/image/**", "/api/projects/freeGallery/**", "/api/projects/freeProjectCard/**").permitAll()
                .antMatchers(HttpMethod.GET).hasAnyRole("USER_READ", "USER_UPDATE", "USER_UPDATE", "USER_DELETE")
                //.antMatchers("/api/projects/memberOverview/**", "/api/projects/projectOverview/**", "/api/projects/addProject/**", "/api/projects/projectCard/**").authenticated()
                .antMatchers(HttpMethod.POST).hasAnyRole("USER_READ")
                .antMatchers(HttpMethod.PUT).hasAnyRole("USER_READ")
                .antMatchers(HttpMethod.DELETE).hasRole("USER_READ")
                //.antMatchers("/api/projects/memberOverview/**", "/api/projects/projectOverview/**", "/api/projects/addProject/**", "/api/projects/projectCard/**").hasRole("USER_READ")
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
