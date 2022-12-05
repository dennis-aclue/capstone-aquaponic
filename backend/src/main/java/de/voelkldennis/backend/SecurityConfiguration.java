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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static de.voelkldennis.backend.jwt.constant.Authority.SUPER_ADMIN_AUTHORITIES;
import static de.voelkldennis.backend.jwt.constant.Authority.USER_AUTHORITIES;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    private JwtAuthorizationFilter jwtAuthorizationFilter;
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SecurityConfiguration(JwtAuthorizationFilter jwtAuthorizationFilter,
                                 JwtAccessDeniedHandler jwtAccessDeniedHandler,
                                 JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                 @Qualifier("userDetailsService") UserDetailsService userDetailsService,
                                 BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
//    }

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
                .antMatchers(HttpMethod.POST).hasAnyRole(String.valueOf(USER_AUTHORITIES))
                .antMatchers(HttpMethod.PUT).hasAnyRole(String.valueOf(USER_AUTHORITIES))
                .antMatchers(HttpMethod.DELETE).hasRole(String.valueOf(SUPER_ADMIN_AUTHORITIES))
                .and()
                .httpBasic()
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
}
/*

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.DELETE)
                .hasRole("ADMIN")
                .antMatchers("/admin/**")
                .hasAnyRole("ADMIN")
                .antMatchers("/user/**")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/login/**")
                .anonymous()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
*/
