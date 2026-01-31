package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.RicettaService;
import jakarta.validation.Valid;



@Controller
public class RicettaController {
	
	//inietta dipendenze 
	@Autowired 
	private RicettaService ricettaService;
	
	@Autowired
	private CredentialsService credentialsService;

	//Ricetta singola
	@GetMapping("/ricetta/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta",this.ricettaService.findById(id));
		return "ricetta.html";
	}
	
	//lista ricette
	@GetMapping("/ricette")
	public String getRicette(Model model) {
		model.addAttribute("ricette",this.ricettaService.findAll());
		return "ricette.html";
	}
	
	//inserimento nuova ricetta (form)
	@GetMapping("/formNewRicetta")
	public String formNewRicetta(Model model) {
		model.addAttribute("ricetta",new Ricetta());
		return "formNewRicetta.html";
	}
	
    //nuova ricetta
	@PostMapping("/ricetta")
	public String newRicetta(@Valid @ModelAttribute("ricetta")Ricetta ricetta,
			                 BindingResult bindingResult ,
			                 @ModelAttribute("currentUser") Utente currentUser, Model model) {
		
		//se ci sono errori (es titolo vuoti ), ricarica il form
		if(bindingResult.hasErrors()) {
			return "formNewRicetta.html";
		}
		//utente non loggato
			if (currentUser == null) {
	            return "redirect:/login";
	        }
		//salvataggio, collgea la ricetta all'autore e gli ingredienti alla ricetta
		this.ricettaService.saveRicetta(ricetta,currentUser);
		//model.addAttribute("ricetta",ricetta);
		return "redirect:ricetta/" + ricetta.getId();
	}
	
	@GetMapping("/formSearchRicetta")
	public String formSearchRicette() {
		return "formSearchRicetta.html";
	}
	
	@PostMapping("/searchRicetta")
	public String searchRicette(Model model, @RequestParam String titolo) {
		model.addAttribute("ricette", this.ricettaService.findByTitolo(titolo));
		return "ricette.html";
	}
	
	
	
	// -------------------------------------------------------------------------
    // METODI PRIVATI (Helper)
    // -------------------------------------------------------------------------

    /**
     * Controlla se l'utente corrente ha il permesso di modificare/cancellare la ricetta.
     * Restituisce true se l'utente è l'autore della ricetta o se è un admin.
     */
    private boolean isAuthorized(Ricetta ricetta, Utente currentUser) {
        // 1. Controlli preliminari (Se non sei loggato o la ricetta non esiste, ciao)
        if (currentUser == null || ricetta == null) {
            return false;
        }

        // 2. Controllo Autore
        // Nota: ricetta.getAutore() potrebbe essere null se hai ricette vecchie senza autore
        boolean isAuthor = false;
        if (ricetta.getAutore() != null && ricetta.getAutore().getId().equals(currentUser.getId())) {
            isAuthor = true;
        }

        // 3. Controllo Admin
        // Va a vedere nel contesto di Spring Security se hai il "badge" ADMIN
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Credentials.ADMIN_ROLE));

        return isAuthor || isAdmin;
    }
}
