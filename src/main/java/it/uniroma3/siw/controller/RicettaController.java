package it.uniroma3.siw.controller;

import java.time.LocalDate;
import java.util.List;

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
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;



@Controller
public class RicettaController {
	
	//inietta dipendenze 
	@Autowired 
	private RicettaService ricettaService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private IngredienteService ingredienteService;

	//---UTENTE GENERICO---
	//Ricetta singola
	@GetMapping("/ricetta/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
	Ricetta ricetta= this.ricettaService.findById(id);
	model.addAttribute("ricetta",ricetta);
	model.addAttribute("nuovaRecensione", new Recensione());
		return "ricetta.html";
	}
	
	//lista ricette
	@GetMapping("/ricette")
	public String getRicette(Model model) {
		model.addAttribute("ricette",this.ricettaService.findAll());
		return "ricette.html";
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
	
	//---UTENTE REGISTRATO---
	
	//inserimento nuova ricetta (form)
			@GetMapping("/formNewRicetta")
			public String formNewRicetta(Model model) {
				model.addAttribute("ricetta",new Ricetta());
				
				model.addAttribute("listaIngredienti", this.ingredienteService.findAll());
				return "formNewRicetta.html";
			}
			// In RicettaController.java

			@PostMapping("/ricetta")
			public String newRicetta(@Valid @ModelAttribute("ricetta") Ricetta ricetta,
			                         BindingResult bindingResult,
			                         // RIMOSSO: @ModelAttribute("currentUser") Utente currentUser
			                         Model model,
			                         @RequestParam(value = "ingredienteIds", required = false) List<Long> ingredienteIds,
			                         @RequestParam(value = "quantitaIng", required = false) List<Integer> quantitaIng,
			                         @RequestParam(required = false) List<String> unitaIng) {

			    // 1. Recupero l'utente VERO dal model (messo dal ControllerAdvice)
			    Utente currentUser = (Utente) model.getAttribute("currentUser");

			    // Validazione
			    if (bindingResult.hasErrors()) {
			        model.addAttribute("listaIngredienti", this.ingredienteService.findAll());
			        return "formNewRicetta.html"; // Controlla il percorso se è sotto admin/
			    }

			    // 2. Salvataggio Base
			    if (currentUser != null) {
			        ricetta.setAutore(currentUser); // Qui currentUser ha l'ID corretto!
			        ricetta.setDataInserimento(LocalDate.now());
			        this.ricettaService.save(ricetta); // Usa save generico o saveRicetta
			    }

			    // 3. Salvataggio Ingredienti
			    if (ingredienteIds != null) {
			        for (int i = 0; i < ingredienteIds.size(); i++) {
			            this.ricettaService.addIngrediente(ricetta, ingredienteIds.get(i), quantitaIng.get(i), unitaIng.get(i));
			        }
			    }

			    return "redirect:/ricetta/" + ricetta.getId();
			}
			
			
	
	
	//---ACCESSO ADMIN---
	// Lista Ricette per Admin (Tabella gestione)
    @GetMapping("/admin/ricette")
    public String adminManageRicette(Model model) {
        model.addAttribute("ricette", this.ricettaService.findAll());
        return "admin/manageRicette.html";
    }

    @PostMapping("/ricetta/delete/{id}")
    public String deleteRicetta(@PathVariable("id") Long id, 
                                @ModelAttribute("currentUser") Utente currentUser,
                                HttpServletRequest request) { // per decidere il redirect
        
        Ricetta ricetta = this.ricettaService.findById(id);

        if (ricetta == null) 
            return "redirect:/ricette";

        // 1. CONTROLLO DI SICUREZZA: "Posso cancellare?"
        // Usa il tuo metodo helper che controlla se sei Autore o Admin
        if (this.isAuthorized(ricetta, currentUser)) {
            
            // Se sì, cancella
            this.ricettaService.deleteById(id); 
            
            // 2. CONTROLLO DI NAVIGAZIONE: "Dove vado ora?"
            // Se sei Admin -> Pannello Admin
            if (request.isUserInRole("ADMIN")) {
                return "redirect:/admin/ricette"; 
            }
            // Se sei Autore normale -> Le mie ricette
            return "redirect:/myRecipes"; 
  
         } else {
            // Se non sei autorizzato -> Errore
            return "redirect:/ricetta/" + id + "?error=NonAutorizzato";
        }
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
