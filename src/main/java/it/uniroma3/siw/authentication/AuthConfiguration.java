package it.uniroma3.siw.authentication;

import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //leggi questa classe all'avvio
@EnableWebSecurity
public class AuthConfiguration {

	@Autowired
	private DataSource dataSource; // Serve per accedere al Database

	@Autowired
	private OAuth2LoginSuccessHandler oauth2LoginSuccessHandler; // Quando qualcuno accede con Google


	 //Configurazione del Database per il Login Classico (User/Pass).
	 //Spring deve sapere dove cercare username e password.
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			// Query per prendere username e ruolo (ADMIN o DEFAULT)
			.authoritiesByUsernameQuery("SELECT username, role FROM credentials WHERE username=?")
			// Query per prendere username, password e stato attivo
			.usersByUsernameQuery("SELECT username, password, enabled FROM credentials WHERE username=?");
	}


	 //Definisce l'algoritmo di cifratura delle password.
	 //BCrypt è lo standard attuale.
	@Bean
	public PasswordEncoder passwordEncoder() { //Quando l'utente scrive la password al login, Spring la trasforma in hash
		                                        //e la confronta con quella nel DB. Se gli hash coincidono, entra.
		return new BCryptPasswordEncoder(); 
	}


	
	 //Qui definisci chi può vedere cosa
	@Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                 .csrf(csrf -> csrf.disable())
                //ordine fondamentale!
                //Decidi chi può vedere cosa 
                .authorizeHttpRequests(auth -> auth
                	//RISORSE SEMPRE ACCESSIBILI
                	.requestMatchers("/css/**", "/images/**", "/favicon.ico").permitAll()
                	//PAGINE PUBBLICHE(Home, Login, Regiistrazione)
                	.requestMatchers("/", "/index", "/searchRicette", "/ricette", "/ricetta/**").permitAll()
    				.requestMatchers("/registrazione", "/login").permitAll()
                    //PAGINE ADMIN
                    .requestMatchers("/admin/**").hasAnyAuthority(ADMIN_ROLE)
                    //PAGINE UTENTE LOGGATO
                    .requestMatchers("/user/**").authenticated()
                    //per tutte le altre pagine non menzionate sopra l'utente deve essere per forza loggato per poterci andare
                    .anyRequest().authenticated()
                )
                
                //LOGIN CLASSICO
                .formLogin(form -> form
                    .loginPage("/login") //la pagina con il form
                    .defaultSuccessUrl("/index", true) //se entra, vai qui 
                    .failureUrl("/login?error=true") // se sbaglia, ricarica la pagina con un errore
                )
                
                //LOGIN GOOGLE
                .oauth2Login(oauth2 -> oauth2
                    .loginPage("/login")
                      //Quando google dice ok è Mario, dobbiamo intercettare qwuel momento per salvare Mario anche nel nostro DB locale
                    .successHandler(oauth2LoginSuccessHandler) 
                )
                
                //serve per assicurarsi di non poter tornare indietro dopo aver fatto il logout
                .logout(logout -> logout
                    .logoutUrl("/logout") //indirizzo per uscire 
                    .logoutSuccessUrl("/") //dopo torna alla home
                    .invalidateHttpSession(true) //cancella la memoria del server 
                    .deleteCookies("JSESSIONID") //Quando fai il login, il server (Java) dà al tuo browser un Cookie con un codice lungo. 
                    //Ogni volta che cambi pagina, il browser mostra questo codice al server per dire "Sono sempre io, Mario".
                    //Conferma a Spring Security che l'azione di logout deve scattare esattamente quando viene chiamata l'URL /logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    //assicura che quell'oggetto venga cancellato istantaneamente dalla memoria del server
                    .clearAuthentication(true).permitAll() //chiunque può accedere alla funzione di logout 
                );
        return httpSecurity.build(); //tutte le regole diventano un oggetto reale e funzionante 
    }
}