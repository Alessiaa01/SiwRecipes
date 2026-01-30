package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.RicettaIngrediente;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.RicettaRepository;

@Service
public class RicettaService {
	
	@Autowired
	private RicettaRepository ricettaRepository;

	//Metodo base, salvataggio semplice. Solo se la ricetta è già compelta di tutto 
	@Transactional
	public void save(Ricetta ricetta) {
		this.ricettaRepository.save(ricetta);
	}
	
	//Metodo++
	//Gestisce l'associazione con l'autore e fissa i collegamenti degli ingredienti 
	
	@Transactional
	public Ricetta saveRicetta(Ricetta ricetta, Utente autore) {
		
		// 1. Associo l'autore alla ricetta
		//Nel db, la tabella ricetta ha una colonna chiamata autore_id, e senza set quando salvi rimarrà nulla
		ricetta.setAutore(autore);
		
		// 2. AGGIUNGIAMO ANCHE LA RICETTA ALLA LISTA DELL'AUTORE
		//Serve a mantenere la coerenza bidirezionale: la ricetta sa chi è l'autore, e l'autore sa che ha unsa nuova ricetta nella sua lista
		autore.getRicetteScritte().add(ricetta);

		// 3. IL "FIX" FONDAMENTALE PER GLI INGREDIENTI
		// Quando arrivano dal form, gli oggetti RicettaIngrediente sono "orfani".
		// Sanno la quantità, ma il campo "ricetta" dentro di loro è null.
		// Dobbiamo dire a ogni riga: "Ehi, la tua ricetta padre è QUESTA qui".
		if (ricetta.getIngredienti() != null) {
			for (RicettaIngrediente riga : ricetta.getIngredienti()) {
				riga.setRicetta(ricetta); // <--- SENZA QUESTO, IL DB NON SALVA IL COLLEGAMENTO
			}
		}

		// 4. Salvo tutto (grazie al CascadeType.ALL salverà anche gli ingredienti)
		return this.ricettaRepository.save(ricetta);
	}
	
	public Ricetta findById(Long id) {
		return ricettaRepository.findById(id).orElse(null);
	}
	
	public List<Ricetta> findAll() {
	    return ricettaRepository.findAll();
	}
	
	public List<Ricetta> findByTitolo(String titolo){
		return ricettaRepository.findByTitoloContainingIgnoreCase(titolo);
	}
	// Metodo utile per la pagina "Le mie ricette"
		public List<Ricetta> findByAutore(Utente autore) {
			return ricettaRepository.findByAutore(autore);
		}
	
	public boolean existByTitle(String titolo) {
		return ricettaRepository.existsByTitolo(titolo);
	}
	
}
