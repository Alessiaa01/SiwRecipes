package it.uniroma3.siw.authentication;

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
			// Query per prendere username, password e stato attivo (1 as enabled serve perché non hai il campo enabled nel DB)
			.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
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
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// 1. REGOLE DI ACCESSO (AUTORIZZAZIONI)
			.authorizeHttpRequests(request -> request
				// Pagine pubbliche (Home, Login, Registrazione, CSS, Immagini)
				.requestMatchers("/", "/index", "/register", "/css/**", "/images/**", "/favicon.ico").permitAll()
				// Pagine solo per ADMIN (es. "/admin/**")
				.requestMatchers("/admin/**").hasAuthority("ADMIN")
				// Tutte le altre pagine richiedono il Login
				.anyRequest().authenticated()
			)
			
			// 2. CONFIGURAZIONE LOGIN CLASSICO (FORM)
			.formLogin(form -> form
				.loginPage("/login")        // La tua pagina HTML personalizzata
				.defaultSuccessUrl("/index", true) // Dove va se il login riesce
				.failureUrl("/login?error=true") // Dove va se sbaglia password
				.permitAll()
			)
			
			// 3. CONFIGURAZIONE LOGIN GOOGLE (OAUTH2)
			.oauth2Login(oauth -> oauth
				.loginPage("/login") // Usiamo la stessa pagina di login
				.successHandler(oauth2LoginSuccessHandler) // <--- QUI COLLEGHIAMO IL TUO HANDLER!
			)
			
			// 4. CONFIGURAZIONE LOGOUT
			.logout(logout -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/") // Dopo il logout torna alla home pubblica
				.invalidateHttpSession(true) //cancella la memoria della sessione
				.deleteCookies("JSESSIONID") //butta via il codice
				.permitAll()
			);

		return http.build();
	}
}