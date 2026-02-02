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


	// Registrazione utente
@PostMapping("/registrazione")
public String registerUser(@Valid @ModelAttribute("user") Utente utente,
                           BindingResult userBindingResult,
                           @Valid @ModelAttribute("credentials") Credentials credentials,
                           BindingResult credentialsBindingResult,
                           Model model) {

	if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
        // salva l'utente
        utenteService.saveUtente(utente);//salva dati anagrafici
        credentials.setUtente(utente);//collega credenziali all'utente
        credentialsService.saveCredentials(credentials); //salva le credenziali 
        model.addAttribute("utente", utente);
        return "registrazioneSuccessful.html"; // pagina conferma registrazione
    }
    return "formRegistrazioneUtente.html"; // ritorna al form se ci sono errori
}

@GetMapping(value = "/success")
public String defaultAfterLogin() {
    // tutti vengono mandati alle ricette
    return "welcome.html";
}
  
	
	// GESTIONE LOGOUT E INDEX
	@GetMapping(value = "/") 
	public String index(Model model) {
		return "redirect:/ricette"; // La nostra home vera è la lista ricette
	}
	
	@GetMapping("/admin")
	public String indexAdmin() {
	    // Questo indirizza alla cartella templates/admin/indexAdmin.html
	    return "admin/indiceAdmin";
	}
	
	
}