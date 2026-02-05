package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Recensione;
import it.uniroma3.siw.repository.RecensioneRepository;

@Service
public class RecensioneService {

	@Autowired
	private RecensioneRepository recensioneRepository;
	
	@Transactional
	public Recensione save(Recensione recensione) {
		return this.recensioneRepository.save(recensione);
	}
	

	public Recensione findById(Long id) {
		return recensioneRepository.findById(id).orElse(null);
	}
	
	public void delete(Recensione recensione) {
        this.recensioneRepository.delete(recensione);
    }
	
	public List<Recensione> findAll(){
		return this.recensioneRepository.findAll();
	}
}
