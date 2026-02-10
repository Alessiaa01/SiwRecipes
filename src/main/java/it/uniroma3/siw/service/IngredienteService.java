package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Ingrediente;
import it.uniroma3.siw.repository.IngredienteRepository;

@Service 
public class IngredienteService {

	@Autowired 
	private IngredienteRepository ingredienteRepository;
	
	//SALVA un nuovo ingrediente nel sistema (operazione admin)
	@Transactional
	public Ingrediente saveIngrediente(Ingrediente ingrediente) {
		// Controllo duplicati (Case Insensitive: "Uova" == "uova")
		if (this.ingredienteRepository.existsByNomeIgnoreCase(ingrediente.getNome())) {
			return null; 
		}
		return this.ingredienteRepository.save(ingrediente);
	}
	
	//Recupera TUTTI gli ingredienti disponibili
	public List<Ingrediente> findAll() {
		return this.ingredienteRepository.findAll();
	}

	public Ingrediente findById(Long id) {
		return this.ingredienteRepository.findById(id).orElse(null);
	}
	
	public Ingrediente findByNome(String nome) {
		return this.ingredienteRepository.findByNome(nome).orElse(null);
	}
	
	public void delete(Long id) {
        this.ingredienteRepository.deleteById(id);
    }
}