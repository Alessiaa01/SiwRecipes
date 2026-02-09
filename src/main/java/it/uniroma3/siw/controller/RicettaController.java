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

import it.uniroma3.siw.controller.validator.RicettaValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.RicettaIngrediente;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.IngredienteService;
import it.uniroma3.siw.service.RicettaService;
import it.uniroma3.siw.service.UtenteService;
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
	
	@Autowired 
	private UtenteService utenteService;
	
	@Autowired
	private RicettaValidator ricettaValidator; // Assicurati di averlo iniettato in cima alla classe

	//---UTENTE GENERICO---
	//VISUALIZZA SINGOLA RICETTA
	@GetMapping("/ricetta/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
	Ricetta ricetta= this.ricettaService.findById(id);
	model.addAttribute("ricetta",ricetta);
	model.addAttribute("nuovaRecensione", new Recensione()); //serve oggetto vuoto di tipo Recensione
		return "ricetta.html";
	}
	
	//VISUALIZZA TUTTE LE RICETTE
	@GetMapping("/ricette")
	public String getRicette(Model model) {
		List<Ricetta> ricette= this.ricettaService.findAll();
		model.addAttribute("ricette",ricette);
		return "ricette.html";
	}
	
	@GetMapping("/formSearchRicetta")
	public String formSearchRicette() {
		return "formSearchRicetta.html";
	}
	
		
	@GetMapping("/searchRicette")
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
			public String newRicetta(@Valid @ModelAttribute("ricetta") Ricetta ricetta, 
			                         BindingResult bindingResult, // <--- 1. AGGIUNTO (deve essere subito dopo @ModelAttribute)
			                         Model model) {

			    // Recupero l'utente loggato 
			    Utente currentUser = (Utente) model.getAttribute("currentUser");

			    // --- LOGICA DI VALIDAZIONE TITOLO DUPLICATO ---
			    // Eseguiamo la validazione solo se è una NUOVA ricetta (id null)
			    // o se il titolo è stato cambiato (per evitare che la ricetta blocchi se stessa in modifica)
			    if (ricetta.getId() == null) {
			        this.ricettaValidator.validate(ricetta, bindingResult); // <--- 2. CHIAMATA AL VALIDATORE
			    } else {
			        Ricetta esistenteNelDb = this.ricettaService.findById(ricetta.getId());
			        if (esistenteNelDb != null && !esistenteNelDb.getTitolo().equals(ricetta.getTitolo())) {
			            this.ricettaValidator.validate(ricetta, bindingResult); // Valida solo se il titolo è cambiato
			        }
			    }

			    // --- CONTROLLO ERRORI ---
			    if (bindingResult.hasErrors()) { // <--- 3. SE CI SONO ERRORI, TORNA AL FORM
			        // Se avevi bisogno di caricare categorie o ingredienti per il form, rifallo qui
			        return "formNewRicetta"; // Assicurati che sia il nome corretto del tuo template HTML
			    }

			    // --- LOGICA ESISTENTE: MODIFICA o NUOVO inserimento? ---
			    if (ricetta.getId() != null) {
			        Ricetta ricettaEsistente = this.ricettaService.findById(ricetta.getId());

			        if (ricettaEsistente != null && !this.isAuthorized(ricettaEsistente, currentUser)) {
			            return "redirect:/login";
			        }
			        
			        if (ricettaEsistente != null) {
			            ricetta.setAutore(ricettaEsistente.getAutore());
			            ricetta.setDataInserimento(ricettaEsistente.getDataInserimento());
			            ricetta.setRecensioni(ricettaEsistente.getRecensioni());
			        }
			    } else {
			        if (currentUser != null) {
			            ricetta.setAutore(currentUser);
			        }
			        ricetta.setDataInserimento(LocalDate.now());
			    }

			    // 3. Gestione della lista ingredienti
			    if (ricetta.getRicettaIngredienti() != null) {
			        ricetta.getRicettaIngredienti().removeIf(riga -> riga.getIngrediente() == null);
			        for (RicettaIngrediente riga : ricetta.getRicettaIngredienti()) {
			            riga.setRicetta(ricetta);
			        }
			    }

			    // 4. Salvataggio finale
			    Ricetta nuovaRicetta = this.ricettaService.save(ricetta);

			    return "redirect:/ricetta/" + nuovaRicetta.getId();
			}

			//MOSTRA LA PAGINA DI MODIFICA
			@GetMapping("/ricetta/{id}/edit")
		    public String editRicetta(@PathVariable("id") Long id, Model model) {
		        
		        // 1. Cerco la ricetta nel DB
		        Ricetta ricetta = this.ricettaService.findById(id);
		        
		        // 2. Recupero l'utente corrente 
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
			
			@PostMapping("/ricetta/delete/{id}")
		    public String deleteRicetta(@PathVariable("id") Long id, Model model) {
		        
		        // 1. Recupero la ricetta e l'utente corrente
		        Ricetta ricetta = this.ricettaService.findById(id);
		        Utente currentUser = (Utente) model.getAttribute("currentUser");

		        // Se la ricetta non esiste, torno alla lista
		        if (ricetta == null) {
		            return "redirect:/ricette";
		        }

		        // Se l'utente non è loggato, via al login
		        if (currentUser == null) {
		            return "redirect:/login";
		        }

		        // 2. Controllo Permessi: Sei l'Autore o sei un Admin?
		        boolean isOwner = currentUser.getId().equals(ricetta.getAutore().getId());
		        
		        // Controllo Admin
		        boolean isAdmin = currentUser.getCredentials() != null && 
		                          currentUser.getCredentials().getRuolo().equals(Credentials.ADMIN_ROLE);

		        if (isOwner || isAdmin) {
		            
		            // 3. Cancellazione secca
		            this.ricettaService.deleteById(id);
		            
		            // 4. Redirect
		            if (isAdmin) {
		                return "redirect:/admin/manageRicette";
		            } else {
		                return "redirect:/ricette";
		            }
		        }

		        // Se arrivi qui, non avevi i permessi
		        return "redirect:/ricetta/" + id + "?error=NonAutorizzato";
		    }

		    // ---------------------------------------------------------
		    // AGGIUNGI AI PREFERITI
		    // ---------------------------------------------------------
		    @PostMapping("/ricetta/{ricettaId}/preferiti/aggiungi")
		    public String addPreferiti(@PathVariable("ricettaId") Long ricettaId, 
		                               @ModelAttribute("currentUser") Utente currentUser,
		                               Model model) {
		        
		        // 1. Ricarico l'utente "fresco" dal DB per sicurezza
		        // Questo evita errori "detached entity" o conflitti di sessione
		        Utente utente = this.utenteService.findById(currentUser.getId());
		        Ricetta ricetta = this.ricettaService.findById(ricettaId);

		        if (utente != null && ricetta != null) {
		            // 2. Aggiungo solo se non c'è già (per evitare duplicati)
		            if (!utente.getRicetteSalvate().contains(ricetta)) {
		                utente.getRicetteSalvate().add(ricetta);
		                // Opzionale: mantieni la coerenza in memoria sull'altro lato
		                ricetta.getUtentiCheHannoSalvato().add(utente); 
		                
		                this.utenteService.saveUtente(utente); // Salvo l'utente (che possiede la lista)
		            }
		        }
		        
		        // Torno alla pagina della ricetta
		        return "redirect:/ricette" ;
		    }

		    // ---------------------------------------------------------
		    // RIMUOVI DAI PREFERITI
		    // ---------------------------------------------------------
		    @PostMapping("/ricetta/{ricettaId}/preferiti/rimuovi")
		    public String removePreferiti(@PathVariable("ricettaId") Long ricettaId, 
		                                  @RequestParam(value = "redirect", required = false) String redirect,
		                                  @ModelAttribute("currentUser") Utente currentUser) {
		        
		        Utente utente = this.utenteService.findById(currentUser.getId());
		        Ricetta ricetta = this.ricettaService.findById(ricettaId);

		        if (utente != null && ricetta != null) {
		            if (utente.getRicetteSalvate().contains(ricetta)) {
		                utente.getRicetteSalvate().remove(ricetta);
		                this.utenteService.saveUtente(utente);
		            }
		        }

		        // Se il parametro redirect è 'profile', resta in manageProfilo
		        if ("profile".equals(redirect)) {
		            return "redirect:/manageProfilo";
		        }  
		        
		        // Altrimenti (es. se cliccato dalla lista generale) torna a /ricette
		        return "redirect:/ricette";
		    }
		    
		    
	//---ACCESSO ADMIN---
	// Lista Ricette per Admin (Tabella gestione)
    @GetMapping("/admin/ricette")
    public String adminManageRicette(Model model) {
        model.addAttribute("ricette", this.ricettaService.findAll());
        return "admin/manageRicette.html";
    }
    
	
	// -------------------------------------------------------------------------
    // METODI PRIVATI (Helper)
    // -------------------------------------------------------------------------

    /**
     * Controlla se l'utente corrente ha il permesso di modificare/cancellare la ricetta.
     * Restituisce true se l'utente è l'autore della ricetta o se è un admin.
     */
    private boolean isAuthorized(Ricetta ricetta, Utente currentUser) {
        // 1. Controlli preliminari (Se non sei loggato o la ricetta non esiste)
        if (currentUser == null || ricetta == null) {
            return false;
        }

        // 2. CONTROLLO AUTORE
        // controlla se coincide l'id dell'utente loggato con l'autore della ricetta--> proprietario
        boolean isOwner = false;
        if (ricetta.getAutore() != null && ricetta.getAutore().getId().equals(currentUser.getId())) {
            isOwner = true;
        }

        // 3. CONTROLLO ADMIN
        //per vedere se l'utente ha il ruolo di admin
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(Credentials.ADMIN_ROLE));

        return isOwner || isAdmin; //se è autore o admin restituisce true 
    }
}
