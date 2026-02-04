package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.UtenteService;

@Controller
public class UtenteController {
	
	@Autowired 
	private UtenteService utenteService;

	// Se cambi questo URL, devi cambiare anche il link nell'HTML!
    @GetMapping("/profiloAutore/{utenteId}")
    public String getProfiloAutore(@PathVariable("utenteId") Long utenteId, Model model) {
        System.out.println(">>> DEBUG: Controller chiamato per id " + utenteId);
        
        Utente utente = this.utenteService.getUtente(utenteId);
        
        if (utente != null) {
            model.addAttribute("utente", utente);
            return "profiloAutore"; 
        } else {
            return "redirect:/";
        }
    }
}
