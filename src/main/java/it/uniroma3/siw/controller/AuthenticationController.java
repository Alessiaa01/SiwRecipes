package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
    
    @Autowired
	private UtenteService utenteService; // Assicurati di avere questo service

	// MOSTRA PAGINA DI LOGIN
	@GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "formLogin";
	}

	// MOSTRA PAGINA DI REGISTRAZIONE
	@GetMapping(value = "/registrazione") 
	public String showRegisterForm (Model model) {
		model.addAttribute("utente", new Utente());
		model.addAttribute("credentials", new Credentials());
		return "formRegistrazioneUtente";
	}

	// GESTISCE LA REGISTRAZIONE (POST)
	@PostMapping(value = { "/registrazione" })
	public String registerUtente(@Valid @ModelAttribute("utente") Utente utente,
			BindingResult userBindingResult, @Valid
			@ModelAttribute("credentials") Credentials credentials,
			BindingResult credentialsBindingResult,
			Model model) {

        // Controlla se username esiste già
        if(!credentials.getUsername().isEmpty() && credentialsService.getCredentials(credentials.getUsername()) != null){
            credentialsBindingResult.rejectValue("username", "duplicate", "Username già esistente");
        }

		// Se ci sono errori, ricarica la pagina
		if (userBindingResult.hasErrors() || credentialsBindingResult.hasErrors()) {
			return "formRegisterUser";
		}
		
        // Se tutto ok, salva
		utenteService.saveUtente(utente); // Salva prima l'utente
        credentials.setUtente(utente);    // Collega
        credentialsService.saveCredentials(credentials); // Salva le credenziali (password criptata)
        
		return "formLogin"; // Rimanda al login
	}
	
	// GESTIONE LOGOUT E INDEX
	@GetMapping(value = "/") 
	public String index(Model model) {
		return "redirect:/ricette"; // La nostra home vera è la lista ricette
	}
}