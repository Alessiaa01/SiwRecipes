package it.uniroma3.siw.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredentialsRepository;
import it.uniroma3.siw.repository.UtenteRepository;

//Gestisce la registrazione, la cifratura delle password e il recupero dell'utente loggato
@Service
public class CredentialsService {

	@Autowired
	protected CredentialsRepository credentialsRepository;
	
	@Autowired
	@Lazy
    protected PasswordEncoder passwordEncoder;
	
	@Autowired
	protected UtenteRepository utenteRepository;
	
	//---METODI DI LETTURA---
	
	//RECUPERO PER ID, se vuoi sapere chi è
		public Credentials getCredentials(Long id) {
			return this.credentialsRepository.findById(id).orElse(null);
		}
		
		//RECUPERO USERNAME
		//Questo è il metodo che userà Spring Security per fare il login.
		// Quando tu scrivi username e password nel form di login, 
		// Spring cerca qui se l'utente esiste.
		public Credentials getCredentials(String username) {
			return this.credentialsRepository.findByUsername(username).orElse(null);
		}
		
		@Transactional
	    public List<Credentials> getAllCredentials() {
	        return this.credentialsRepository.findAll();
	    }
		
	//---METODI REGISTRAZIONE CLASSICA(Form)---
	
	@Transactional// se trova un errore annulla tutto, per non avre dati a metà
	public Credentials saveCredentials(Credentials credentials) {
		
		//Assegnazione ruolo, di default chi si registra è un utente normale
		credentials.setRuolo(Credentials.DEFAULT_ROLE);
		
		//CIFRATURA PASSWORD(Security)
		// Prendo la password scritta dall'utente (es. "pippo123")
	    // La trasformo in un hash illeggibile (es. "$2a$10$D9...")
		String passwordInChiaro = credentials.getPassword();
		String passwordCifrata = this.passwordEncoder.encode(passwordInChiaro);
		
		credentials.setPassword(passwordCifrata);
		// SALVATAGGIO NEL DB
		// Chiamo la Repository per scrivere fisicamente nel database.
	    // Grazie al CascadeType.ALL su Credentials, questo salverà anche l'oggetto Utente collegato
		return this.credentialsRepository.save(credentials);
	}
	
	public List<Credentials> findAll() {
		return this.credentialsRepository.findAll();
    }
	
	
	

	
	//LOGIN GOOGLE
	@Transactional
	public void loginOrRegisterGoogleUser(String email, String nome, String cognome) {
		
		// 1. Controllo se l'utente esiste già
		if (this.credentialsRepository.findByUsername(email).isPresent()) {
			return; // Esiste, tutto ok.
		}

		// 2. Se non esiste, lo registro
		System.out.println("Nuovo utente Google: " + email);
		
		// A. Creo Utente
		Utente nuovoUtente = new Utente();
		nuovoUtente.setEmail(email);
		nuovoUtente.setNome(nome);
		nuovoUtente.setCognome(cognome);
		this.utenteRepository.save(nuovoUtente);
		
		// B. Creo Credenziali
		Credentials nuoveCredenziali = new Credentials();
		nuoveCredenziali.setUsername(email);
		nuoveCredenziali.setRuolo(Credentials.DEFAULT_ROLE);
		nuoveCredenziali.setUtente(nuovoUtente);
		
		// C. Password Casuale (Trucco per soddisfare il DB), l'utente non saprà mai e non userà mai
		nuoveCredenziali.setPassword(UUID.randomUUID().toString());
		
		this.credentialsRepository.save(nuoveCredenziali);
	}
	
	//---GESTIONE BAN(ADMIN)---
	@Transactional
    public void lockCredentials(String username) {
        Credentials credentials = this.credentialsRepository.findByUsername(username).orElse(null);
        if (credentials != null) {
            credentials.setEnabled(false); 
            this.credentialsRepository.save(credentials);
        }
    }

    @Transactional
    public void unlockCredentials(String username) {
        Credentials credentials = this.credentialsRepository.findByUsername(username).orElse(null);
        if (credentials != null) {
             credentials.setEnabled(true); 
            this.credentialsRepository.save(credentials);
        }
    }
}
