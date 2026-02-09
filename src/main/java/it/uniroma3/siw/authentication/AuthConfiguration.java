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



@Configuration //è una classe di configurazione che definisce come devono essere determinati i  Bean
@EnableWebSecurity //attiva l'integrazione di Spring Security per per le app web, permettendo di personalizzare le regole di accesso
public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    //dove andare a cercare gli utenti per il login classico 
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication() //Utilizza questa autenticazione per cercare gli utenti direttamente nel db tramite query sql
            .dataSource(dataSource) //utilizza il database configurato nel progetto (per cercare gli utenti)
            .authoritiesByUsernameQuery("SELECT username, ruolo FROM credentials WHERE username=?") //recupera il ruolo dell'utente
            .usersByUsernameQuery("SELECT username, password, enabled FROM credentials WHERE username=?"); //query per trovare l'utente
             //Spring verifica se l'username esiste e se la psw corrisponde, e se l'utente è attivo/bannato
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //cripate le psw, viene applicato un hash sicuro 
    }

    @Bean
    //Configure è la lista di controllo che ogni richiesta HTTP deve attraversare. Definisce le regole di autorizzazione e i flussi di login/logout
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable()) //sicurezza che in questo caso non ci serve 
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
                
                // 5. TUTTO IL RESTO RICHIEDE LOGIN (es.creare ricette, scivere recensioni)
                .anyRequest().authenticated()
            )
            
            // CONFIGURAZIONE LOGIN CLASSICO
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/success", true) //dove andare dopo success
                .failureUrl("/login?error=true") //dove in caso di errore 
            )
            
            // CONFIGURAZIONE LOGIN GOOGLE
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
            		.defaultSuccessUrl("/success", true)          
            )
            
            // CONFIGURAZIONE LOGOUT
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") //dopo il logout viene riportato all homepage
                .invalidateHttpSession(true)//assicura che i dati della sessione vengano cancellati
                .deleteCookies("JSESSIONID")// cancella i cookie
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .clearAuthentication(true).permitAll()
            );
            
        return httpSecurity.build();
    }
}