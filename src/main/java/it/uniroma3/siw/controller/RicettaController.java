package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.service.RicettaService;



@Controller
public class RicettaController {
	
	//inietta dipendenze 
	@Autowired 
	private RicettaService ricettaService;

	//Ricetta singola
	@GetMapping("/ricetta/{id}")
	public String getRicetta(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ricetta",this.ricettaService.findById(id));
		return "ricetta.html";
	}
	
	//lista ricette
	@GetMapping("/ricette")
	public String showRicette(Model model) {
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
	public String newRicetta(@ModelAttribute("ricetta")Ricetta ricetta, Model model) {
		this.ricettaService.save(ricetta);
		model.addAttribute("ricetta",ricetta);
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
}
