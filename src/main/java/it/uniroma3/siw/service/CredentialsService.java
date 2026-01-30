package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.repository.CredentialsRepository;

//Gestisce la registrazione, la cifratura delle password e il recupero dell'utente loggato
@Service
public class CredentialsService {

	@Autowired
	protected CredentialsRepository credentialsRepository;
	
	@Autowired
    protected PasswordEncoder passwordEncoder;
	
	
	@Transactional// se trova un errore annulla tutto, per non avre dati a metà
	public Credentials saveCredentials(Credentials credentials) {
		
		//Assegnazione ruolo, di default chi si registra è un utente normale
		credentials.setRuolo(Credentials.DEFAULT_ROLE);
		
		//CIFRATURA PASSWORD(Security)
		// Prendo la password scritta dall'utente (es. "pippo123")
	    // La trasformo in un hash illeggibile (es. "$2a$10$D9...")
		String passwordInChiaro = credentials.getPassword();
		String passwordCifrata = this.passwordEncoder.encode(passwordInChiaro);
		
		// SALVATAGGIO NEL DB
		// Chiamo la Repository per scrivere fisicamente nel database.
	    // Grazie al CascadeType.ALL su Credentials, questo salverà anche l'oggetto Utente collegato
		return this.credentialsRepository.save(credentials);
	}
	
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
}
