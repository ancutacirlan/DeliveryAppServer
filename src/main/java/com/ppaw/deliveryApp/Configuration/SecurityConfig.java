package com.ppaw.deliveryApp.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)  // Dezactivează protecția CSRF complet
                .authorizeHttpRequests((authorize) ->
                                authorize
                                        .requestMatchers("/api/auth/**").permitAll() // Permite accesul public pentru rutele de autentificare
                                        .anyRequest().permitAll() // Protejează restul rutelor
                        // .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();

    }
//        http
//                .authorizeRequests()
//
//                .requestMatchers("/register/**", "/api/auth/login").permitAll() // Permite acces la înregistrare și login fără autentificare
//                .requestMatchers("/api/user/**").hasRole("USER") // Acces doar pentru utilizatori cu rol USER
//                .anyRequest().authenticated() ;// Orice altă cerere necesită autentificare
////                .and()
//                .httpBasic();
//
//                .requestMatchers("/register/**").permitAll()
//                .requestMatchers("/index").permitAll()
//                //.anyRequest().permitAll()//For matching http request requests.
//                .requestMatchers("/api/user/**").hasRole("USER") //Giving acess for admins role users only.
//                //.requestMatchers("/users").hasRole("USER")
//               // .and()
//               // .formLogin(

    /// /                        form -> form
    /// /                                .loginPage("/login")
    /// /                                .loginProcessingUrl("/login")
    /// /                                .defaultSuccessUrl("/users")
    /// /                                .permitAll()
    /// /                ).logout(
    /// /                        logout -> logout
    /// /                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    /// /                                .permitAll()
    /// /
    /// /                )
//        ;
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
