package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
        
        // 2. Ricarico anche le recensioni GIÀ ESISTENTI
        // Altrimenti se sbaglio a scrivere, spariscono i commenti degli altri
        model.addAttribute("recensioni", ricetta.getRecensioni()); 
        
        return "ricetta"; 
    }

    // --- SALVATAGGIO ---
    recensione.setAutore(currentUser); 
    recensione.setRicetta(ricetta);
    
    // Aggiungo la recensione alla lista della ricetta 
    ricetta.getRecensioni().add(recensione); 
    
    this.recensioneService.save(recensione);

    return "redirect:/ricetta/" + ricettaId;
}
	
	@PostMapping("/ricetta/{ricettaId}/recensione/{recensioneId}/delete")
	public String deleteRecensione(@PathVariable("ricettaId") Long ricettaId,
	                               @PathVariable("recensioneId") Long recensioneId,
	                               @ModelAttribute("currentUser") Utente currentUser) { 

	    Recensione recensione = recensioneService.findById(recensioneId);
	    
	    if (recensione == null || currentUser == null) {
	        return "redirect:/ricetta/" + ricettaId;
	    }

	    // Recuperiamo il ruolo direttamente dall'utente loggato
	    boolean isAdmin = currentUser.getCredentials() != null && 
	                      Credentials.ADMIN_ROLE.equals(currentUser.getCredentials().getRuolo()); 

	    boolean isAutore = recensione.getAutore() != null && 
	                       recensione.getAutore().getId().equals(currentUser.getId());

	    if (isAdmin || isAutore) {
	        
	        recensione.getRicetta().getRecensioni().remove(recensione);
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

	    // Carico la ricetta ( ricetta.html ne ha bisogno per disegnarsi)
	    Ricetta ricetta = recensione.getRicetta();
	    model.addAttribute("ricetta", ricetta);

	    // Passo al model la recensione specifica da modificare.
	    // Nell'HTML useremo questo oggetto per capire se mostrare il form di modifica o quello di aggiunta.
	    model.addAttribute("recensioneDaModificare", recensione);
	    
	    return "ricetta"; // Ritorno alla STESSA pagina
	}

	// riceve i dati e salva
	@PostMapping("/recensione/modifica/{id}")
	public String updateRecensione(@PathVariable("id") Long id,
	                               @Valid @ModelAttribute("recensioneDaModificare") Recensione recensioneForm,
	                               BindingResult bindingResult,
	                               Model model) { // <--- RIMOSSO @ModelAttribute("currentUser") dai parametri

	    // 1. Recuperiamo la recensione originale dal database
	    Recensione originale = this.recensioneService.findById(id);

	    // 2. Recuperiamo l'utente loggato in modo sicuro per il controllo
	    // Invece di passarlo come parametro, lo prendiamo dal model (dove lo mette il tuo GlobalController o simile)
	    Utente currentUser = (Utente) model.getAttribute("currentUser");

	    // Sicurezza: controlliamo che chi modifica sia l'autore
	    if (originale == null || currentUser == null || !originale.getAutore().getId().equals(currentUser.getId())) {
	        return "redirect:/ricette";
	    }

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("ricetta", originale.getRicetta());
	        model.addAttribute("nuovaRecensione", new Recensione()); 
	        return "ricetta"; 
	    }

	    // 3. AGGIORNAMENTO MIRATO (Qui è dove risolviamo l'errore)
	    // Copiamo SOLO i campi testuali. NON tocchiamo l'oggetto Autore, nè la Ricetta, nè l'ID.
	    originale.setTitolo(recensioneForm.getTitolo());
	    originale.setVoto(recensioneForm.getVoto());
	    originale.setTesto(recensioneForm.getTesto());
	    
	    // Il campo 'originale' ha ancora il suo vecchio autore (ID 652). 
	    // Non toccandolo, Hibernate non proverà a cambiarlo con l'ID 51.

	    this.recensioneService.save(originale);

	    return "redirect:/ricetta/" + originale.getRicetta().getId();
	}
	
	//GESTIONE ADMIN
	@GetMapping("/admin/recensioni")
    public String adminManageRecensioni(Model model) {
        // Recupera tutte le recensioni 
        model.addAttribute("recensioni", this.recensioneService.findAll());
        return "admin/manageRecensioni.html";
    }
}
