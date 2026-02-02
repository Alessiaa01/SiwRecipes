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
import it.uniroma3.siw.model.RicettaIngrediente;
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
	//VISUALIZZA SINGOLA RICETTA
	@GetMapping("/ricetta/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
	Ricetta ricetta= this.ricettaService.findById(id);
	model.addAttribute("ricetta",ricetta);
	model.addAttribute("nuovaRecensione", new Recensione());
		return "ricetta.html";
	}
	
	//VISUALIZZA TUTTE LE RICETTE
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
	
           //Prepara la pagina con il form per creare una nuova ricetta
			@GetMapping("/formNewRicetta")
			public String formNewRicetta(Model model) {
				model.addAttribute("ricetta",new Ricetta());
				
				model.addAttribute("listaIngredienti", this.ingredienteService.findAll());
				return "formNewRicetta.html";
			}
			
			
			@PostMapping("/newRicetta") 
		    public String newRicetta(@ModelAttribute("ricetta") Ricetta ricetta, Model model) {

		        // 1. Recupero l'utente loggato (Grazie al GlobalController!)
		        Utente currentUser = (Utente) model.getAttribute("currentUser");

		        // 2. Logica: MODIFICA o NUOVO inserimento?
		        if (ricetta.getId() != null) {
		            // --- MODIFICA ---
		            Ricetta ricettaEsistente = this.ricettaService.findById(ricetta.getId());

		            // SECURITY CHECK: Se la ricetta esiste ma non sei l'autore (e non sei admin), BLOCCO.
		            if (ricettaEsistente != null && !this.isAuthorized(ricettaEsistente, currentUser)) {
		                return "redirect:/login"; // O una pagina di errore
		            }
		            
		            // Re-imposto i dati che il form non invia
		            if (ricettaEsistente != null) {
		                ricetta.setAutore(ricettaEsistente.getAutore());
		                ricetta.setDataInserimento(ricettaEsistente.getDataInserimento());
		               // ricetta.setUtentiCheHannoSalvato(ricettaEsistente.getUtentiCheHannoSalvato());
		            }

		        } else {
		            // --- NUOVA RICETTA ---
		            if (currentUser != null) {
		                ricetta.setAutore(currentUser); // Il cuoco è l'utente loggato
		            }
		            ricetta.setDataInserimento(LocalDate.now());
		        }

		        // 3. Gestione della lista ingredienti (RicettaIngrediente)
		        if (ricetta.getRicettaIngredienti() != null) {
		            
		            // A. Pulizia: rimuovo le righe vuote
		            ricetta.getRicettaIngredienti().removeIf(riga -> riga.getIngrediente() == null);
		            
		            // B. Associazione Bidirezionale
		            for (RicettaIngrediente riga : ricetta.getRicettaIngredienti()) {
		                riga.setRicetta(ricetta);
		            }
		        }

		        // 4. Salvataggio finale
		        Ricetta nuovaRicetta = this.ricettaService.save(ricetta);

		        // 5. Redirect alla pagina di dettaglio
		        return "redirect:/ricetta/" + nuovaRicetta.getId();
		    }

			
			@GetMapping("/ricetta/{id}/edit")
		    public String editRicetta(@PathVariable("id") Long id, Model model) {
		        
		        // 1. Cerco la ricetta nel DB
		        Ricetta ricetta = this.ricettaService.findById(id);
		        
		        // 2. Recupero l'utente corrente (già caricato dal GlobalController)
		        Utente currentUser = (Utente) model.getAttribute("currentUser");

		        // 3. Controllo se l'utente ha il permesso (è l'autore o è admin?)
		        if (this.isAuthorized(ricetta, currentUser)) {
		            
		            // --- ACCESSO CONSENTITO ---
		            
		            // Passo la ricetta al model (così i campi del form si pre-compilano!)
		            model.addAttribute("ricetta", ricetta);
		            
		            // Passo la lista di tutti gli ingredienti disponibili per le checkbox
		            model.addAttribute("listaIngredienti", this.ingredienteService.findAll());
		            
		            // Restituisco la vista del form (riutilizziamo quella di creazione)
		            return "formNewRicetta";
		        }

		        // --- ACCESSO NEGATO ---
		        // Se non è l'autore, lo rimando alla pagina di visualizzazione della ricetta
		        return "redirect:/ricetta/" + id;
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
