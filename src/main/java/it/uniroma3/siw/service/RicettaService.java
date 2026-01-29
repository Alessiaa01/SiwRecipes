package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.repository.RicettaRepository;

@Service
public class RicettaService {
	
	@Autowired
	private RicettaRepository ricettaRepository;

	@Transactional
	public void save(Ricetta ricetta) {
		this.ricettaRepository.save(ricetta);
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
	
	public boolean existByTitle(String titolo) {
		return ricettaRepository.existsByTitolo(titolo);

}
}
