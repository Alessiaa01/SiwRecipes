package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.RicettaIngrediente;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.IngredienteRepository;
import it.uniroma3.siw.repository.RicettaIngredienteRepository;
import it.uniroma3.siw.repository.RicettaRepository;

@Service
public class RicettaService {
	
	@Autowired
	private RicettaRepository ricettaRepository;
	
	@Autowired
	private IngredienteRepository ingredienteRepository;
	
	@Autowired
	private RicettaIngredienteRepository ricettaIngredienteRepository;

	//Metodo base, salvataggio semplice. Solo se la ricetta è già compelta di tutto 
	@Transactional
	public Ricetta save(Ricetta ricetta) {
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
	
		// Cambia 'existByTitle' in 'existsByTitolo'
		public boolean existsByTitolo(String titolo) {
		    return ricettaRepository.existsByTitoloIgnoreCase(titolo);
		}

	public void deleteById(Long id) {
	    ricettaRepository.deleteById(id);
	}
	
	@Transactional
	public void addIngrediente(Ricetta ricetta, Long idIngrediente, Integer quantita, String unita) {
	    Ingrediente ing = ingredienteRepository.findById(idIngrediente).orElse(null);
	    
	    if (ing != null) {
	        RicettaIngrediente relazione = new RicettaIngrediente();
	        relazione.setRicetta(ricetta);
	        relazione.setIngrediente(ing);
	        relazione.setQuantita(quantita);
	        relazione.setUnita(unita); // Salva l'unità passata dal form ("g", "ml", ecc.)
	        
	        ricettaIngredienteRepository.save(relazione);
	    }
	}
}
