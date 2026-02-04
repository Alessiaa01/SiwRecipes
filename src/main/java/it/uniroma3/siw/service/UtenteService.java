package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

@Service
public class UtenteService {

	@Autowired 
	protected UtenteRepository utenteRepository;
	
	//Recupera un utente specifico dato il suo ID
	//per mostrare la pagina del profilo utente 
	@Transactional
    public Utente getUtente(Long id) {
        Optional<Utente> result = this.utenteRepository.findById(id);
        return result.orElse(null);
    }
	
	//salva un utente (NON SO SE SERVE )
	//Di solito viene chiamato automaticamente dal salvataggio delle Credentials
    // grazie al "Cascade", ma è utile averlo per aggiornare profilo (es. cambio nome)
	@Transactional
	public Utente saveUtente(Utente utente) {
        return this.utenteRepository.save(utente);
    }
	
	//Recupera TUTTI gli utenti del sistema
	//Utile per una pagina di amministrazione dove l'admin vede gli iscritti
	@Transactional
    public List<Utente> getAllUtenti() {
        return this.utenteRepository.findAll();
    }
	
	public Utente findById(Long id) {
        return this.utenteRepository.findById(id).orElse(null);
    }
}
