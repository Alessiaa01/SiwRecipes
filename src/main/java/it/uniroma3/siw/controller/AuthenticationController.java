package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.RicettaService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;
    
    @Autowired
	private UtenteService utenteService; 
    
    @Autowired
    private RicettaService ricettaService;

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
public String defaultAfterLogin(Authentication authentication) {
    String username;
    
    // Identifichiamo l'utente (che sia login classico o Google)
    if (authentication instanceof OAuth2AuthenticationToken) {
        OAuth2User principal = ((OAuth2AuthenticationToken) authentication).getPrincipal();
        username = principal.getAttribute("email");
    } else {
        username = authentication.getName();
    }

    // Controlliamo se le credenziali nel DB sono attive
    Credentials credentials = credentialsService.getCredentials(username);
    if (credentials != null && !credentials.isEnabled()) {
        // Se l'utente è bloccato, lo mandiamo forzatamente al logout
    	return "redirect:/login?error=blocked";
    }

    return "welcome.html";
}
  
	
	// GESTIONE LOGOUT E INDEX
	@GetMapping(value = "/") 
	public String index(Model model) {
		return "redirect:/ricette"; // La nostra home vera è la lista ricette
	}
	
	@GetMapping("/admin/indiceAdmin")
	public String indexAdmin() {
	    // Questo indirizza alla cartella templates/admin/indexAdmin.html
	    return "admin/indiceAdmin";
	}
	
	@GetMapping("/admin/manageUtenti")
	public String manageUtenti(Model model) {
	    // 1. Recupera la lista
	    List<Credentials> allCredentials = this.credentialsService.findAll();
	    model.addAttribute("credentialsList", allCredentials);
	    return "admin/manageUtenti";
	}
		
		@GetMapping("/admin/manageRicette")
		public String manageRicette(Model model) {
		    // Carica le ricette dal database
		    model.addAttribute("ricette", this.ricettaService.findAll()); 
		    
		    return "admin/manageRicette.html"; 
		}
		
		//Azione per bannare un utente 
		@PostMapping("/admin/manageUtenti/{username}/ban")
	    public String banUtenti(@PathVariable("username") String username) {
	        this.credentialsService.lockCredentials(username);
	        return "redirect:/admin/manageUtenti";
	    }
		
		//Azione per riabilitare un utente 
		@PostMapping("/admin/manageUtenti/{username}/unban")
	    public String unbanUtenti(@PathVariable("username") String username) {
	        this.credentialsService.unlockCredentials(username);
	        return "redirect:/admin/manageUtenti";
	    }
	
	
}