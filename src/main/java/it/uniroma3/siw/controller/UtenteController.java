package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.RicettaService;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class UtenteController {
	
	@Autowired 
	private UtenteService utenteService;
	
	@Autowired
	private RicettaService ricettaService;

	// Se cambi questo URL, devi cambiare anche il link nell'HTML!
    @GetMapping("/profiloAutore/{utenteId}")
    public String getProfiloAutore(@PathVariable("utenteId") Long utenteId, Model model) {   
        Utente utente = this.utenteService.getUtente(utenteId);
        
        if (utente != null) {
            model.addAttribute("utente", utente);
            return "profiloAutore"; 
        } else {
            return "redirect:/";
        }
    }
    
    @GetMapping("/manageProfilo")
    public String getProfilo(Model model) {
    	return "manageProfilo";
    }
    
    @PostMapping("/utente/ricetta/{id}/remove")
    public String removePreferitoAdmin(@PathVariable("id") Long id, Model model) {
        Utente admin = (Utente) model.getAttribute("currentUser"); // Recuperato dal GlobalController
        Ricetta ricetta = ricettaService.findById(id);
        
        if (admin != null && ricetta != null) {
            admin.getRicetteSalvate().remove(ricetta);
            utenteService.saveUtente(admin);
        }
        
        // Ritorna alla pagina di gestione per vedere l'aggiornamento
        return "redirect:/admin/manageRicette";
    }
}
