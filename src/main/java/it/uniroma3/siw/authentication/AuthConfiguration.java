package it.uniroma3.siw.authentication;

import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private OAuth2LoginSuccessHandler oauth2LoginSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .authoritiesByUsernameQuery("SELECT username, ruolo FROM credentials WHERE username=?")
            .usersByUsernameQuery("SELECT username, password, enabled FROM credentials WHERE username=?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // 1. RISORSE STATICHE (CSS, Immagini) - Devono essere pubbliche
                .requestMatchers("/css/**", "/images/**", "/favicon.ico").permitAll()
                
                // 2. PAGINE PUBBLICHE (Home, Ricette, E IL NUOVO PROFILO AUTORE)
                // L'asterisco doppio ** è fondamentale per includere tutti gli ID (es. /profiloAutore/1)
                .requestMatchers("/", "/index", "/searchRicette", "/ricette", "/ricetta/**", "/profiloAutore/**").permitAll()
                
                // 3. PAGINE DI ACCESSO
                .requestMatchers("/registrazione", "/login").permitAll()
                .requestMatchers("/login/oauth2/**", "/oauth2/**","/login/oauth2/code/google").permitAll()
                
                .requestMatchers("/error").permitAll()
                
                // 4. PAGINE ADMIN
                .requestMatchers("/admin/**").hasAnyAuthority(ADMIN_ROLE)
                
                // 5. TUTTO IL RESTO RICHIEDE LOGIN (es. "/user/**", "/myRecipes")
                .anyRequest().authenticated()
            )
            
            // CONFIGURAZIONE LOGIN CLASSICO
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/success", true)
                .failureUrl("/login?error=true")
            )
            
            // CONFIGURAZIONE LOGIN GOOGLE
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
            		.defaultSuccessUrl("/success", true)
                .successHandler(oauth2LoginSuccessHandler)
                
            )
            
            // CONFIGURAZIONE LOGOUT
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .clearAuthentication(true).permitAll()
            );
            
        return httpSecurity.build();
    }
}