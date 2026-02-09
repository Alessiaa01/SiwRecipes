package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.service.IngredienteService;

@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;

	@GetMapping("/admin/manageIngredienti")
	public String adminIngredienti(Model model) {
		// Passiamo un oggetto vuoto per il form
		model.addAttribute("ingrediente", new Ingrediente());
		// Passiamo la lista completa per far vedere cosa c'è già nella dispensa
		model.addAttribute("listaIngredienti", this.ingredienteService.findAll());
		
		return "admin/manageIngredienti.html";
	}

	@PostMapping("/admin/ingrediente")
	public String newIngrediente(@ModelAttribute("ingrediente") Ingrediente ingrediente, Model model) {
		
		// Il tuo service restituisce null se l'ingrediente esiste già
		Ingrediente salvato = this.ingredienteService.saveIngrediente(ingrediente);
		
		if (salvato == null) {
			model.addAttribute("messaggioErrore", "Questo ingrediente esiste già nella dispensa!");
			// Ricarichiamo la lista per non farla sparire
			model.addAttribute("listaIngredienti", this.ingredienteService.findAll());
			return "admin/manageIngredienti.html";
		}
		
		// Se tutto va bene, ricarichiamo la pagina pulita
		return "redirect:/admin/manageIngredienti";
	}
	// Cancellazione Ingrediente dalla Dispensa
	@PostMapping("/admin/ingrediente/delete/{id}")
    public String deleteIngrediente(@PathVariable("id") Long id) {
        // Cancella direttamente. Se fallisce, Spring mostrerà l'errore standard.
        this.ingredienteService.delete(id); 
        return "redirect:/admin/manageIngredienti";
    }
}