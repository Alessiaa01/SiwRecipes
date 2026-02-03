package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.RecensioneService;
import it.uniroma3.siw.service.RicettaService;
import jakarta.validation.Valid;

@Controller
public class RecensioneController {
	
	@Autowired
	private RecensioneService recensioneService;
	
	@Autowired
	private RicettaService ricettaService;

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        // Blocca l'id (per non cambiare identità alla recensione)
        // Blocca l'autore (per non poter dire "questa recensione l'ha scritta un altro")
        binder.setDisallowedFields("id", "autore", "autore.id", "ricetta", "ricetta.id");
    }
	
	@PostMapping("/ricetta/{ricettaId}/recensione")
public String addRecensione(@PathVariable("ricettaId") Long ricettaId,
                        @Valid @ModelAttribute("nuovaRecensione") Recensione recensione,
                        BindingResult bindingResult,
                        @ModelAttribute("currentUser") Utente currentUser, 
                        Model model) {

    Ricetta ricetta = this.ricettaService.findById(ricettaId);
    
    if (ricetta == null) {
        return "redirect:/ricette";
    }

    // --- GESTIONE ERRORE ---
    if (bindingResult.hasErrors()) {
        // 1. Rimetto la ricetta nel model
        model.addAttribute("ricetta", ricetta);
        
        // 2. CORREZIONE IMPORTANTE: Ricarico anche le recensioni GIÀ ESISTENTI
        // Altrimenti se sbaglio a scrivere, spariscono i commenti degli altri!
        model.addAttribute("recensioni", ricetta.getRecensioni()); // O come hai chiamato la lista nel template
        
        // 3. CORREZIONE: Tolgo ".html"
        return "ricetta"; 
    }

    // --- SALVATAGGIO ---
    recensione.setAutore(currentUser); 
    recensione.setRicetta(ricetta);
    
    // Opzionale: Aggiungo la recensione alla lista della ricetta (per coerenza in memoria)
    ricetta.getRecensioni().add(recensione); 
    
    this.recensioneService.save(recensione);

    // 4. Redirect: Assicurati che questo indirizzo esista nel tuo Controller GET
    return "redirect:/ricetta/" + ricettaId;
}
	
	@PostMapping("/ricetta/{ricettaId}/recensione/{recensioneId}/delete")
	public String deleteRecensione(@PathVariable("ricettaId") Long ricettaId,
	                               @PathVariable("recensioneId") Long recensioneId,
	                               // Spring recupera questi due oggetti direttamente dal tuo GlobalController!
	                               @ModelAttribute("currentUser") Utente currentUser,
	                               @ModelAttribute("credentials") Credentials credentials) {

	    // 1. Recupero la recensione dal DB
	    Recensione recensione = recensioneService.findById(recensioneId);
	    
	    // Controllo di sicurezza base
	    if (recensione == null || currentUser == null) {
	        return "redirect:/ricetta/" + ricettaId;
	    }

	    // 2. Controllo se sono ADMIN usando l'oggetto credentials che hai già
	    // (Assumo che tu abbia una costante o una stringa per il ruolo ADMIN)
	    boolean isAdmin = credentials.getRuolo().equals(Credentials.ADMIN_ROLE); 
	    // Oppure se usi stringhe semplici: credentials.getRole().equals("ADMIN");

	    // 3. Controllo se sono l'AUTORE
	    // Nota: uso gli ID per sicurezza massima nel confronto
	    boolean isAutore = recensione.getAutore().getId().equals(currentUser.getId());

	    // 4. Se sono Admin O sono l'Autore -> Cancello
	    if (isAdmin || isAutore) {
	        // Rimuovo dalla lista della ricetta per mantenere coerenza (se necessario per il tuo JPA)
	        recensione.getRicetta().getRecensioni().remove(recensione);
	        
	        // Cancello effettivamente
	        recensioneService.delete(recensione);
	    }

	    return "redirect:/ricetta/" + ricettaId;
	}
	
	// 1. GET: Quando clicco "Modifica", ricarico la pagina ricetta MA con i dati vecchi nel form
	@GetMapping("/recensione/modifica/{id}")
	public String modificaRecensione(@PathVariable("id") Long id,
	                                 @ModelAttribute("currentUser") Utente currentUser,
	                                 Model model) {
	    
	    Recensione recensione = this.recensioneService.findById(id);

	    // Sicurezza
	    if (recensione == null || !recensione.getAutore().getId().equals(currentUser.getId())) {
	        return "redirect:/ricette";
	    }

	    // Carico la ricetta (perché la pagina ricetta.html ne ha bisogno per disegnarsi)
	    Ricetta ricetta = recensione.getRicetta();
	    model.addAttribute("ricetta", ricetta);

	    // TRUCCO: Passo al model la recensione specifica da modificare.
	    // Nell'HTML useremo questo oggetto per capire se mostrare il form di modifica o quello di aggiunta.
	    model.addAttribute("recensioneDaModificare", recensione);
	    
	    return "ricetta"; // Ritorno alla STESSA pagina, non a un'altra vista
	}

	// 2. POST: Questo rimane UGUALE a prima (riceve i dati e salva)
	@PostMapping("/recensione/modifica/{id}")
	public String updateRecensione(@PathVariable("id") Long id,
	                               @Valid @ModelAttribute("recensioneDaModificare") Recensione recensioneForm,
	                               BindingResult bindingResult,
	                               @ModelAttribute("currentUser") Utente currentUser,
	                               Model model) {

	    Recensione originale = this.recensioneService.findById(id);

	    // Sicurezza
	    if (originale == null || !originale.getAutore().getId().equals(currentUser.getId())) {
	        return "redirect:/ricette";
	    }

	    if (bindingResult.hasErrors()) {
	        // Se c'è errore, devo ricaricare tutto ciò che serve alla pagina ricetta
	        model.addAttribute("ricetta", originale.getRicetta());
	        
	        // AGGIUNTA IMPORTANTE: Serve perché la pagina ricetta ha anche il form "nuovaRecensione" in basso
	        model.addAttribute("nuovaRecensione", new Recensione()); 
	        
	        return "ricetta"; 
	    }

	    // Aggiornamento
	    originale.setTitolo(recensioneForm.getTitolo());
	    originale.setVoto(recensioneForm.getVoto());
	    originale.setTesto(recensioneForm.getTesto());
	    
	    this.recensioneService.save(originale);

	    return "redirect:/ricetta/" + originale.getRicetta().getId();
	}
}
