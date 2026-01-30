package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.RicettaIngrediente;
import it.uniroma3.siw.repository.RicettaIngredienteRepository;

//Anche se spesso salverai queste righe automaticamente salvando la Ricetta (grazie al CascadeType.ALL), 
//avere un Service dedicato è utile:
//Se vuoi cancellare solo la riga "Zucchero" da una torta senza risalvare tutta la torta.

@Service
public class RicettaIngredienteService {
	
	@Autowired
	private RicettaIngredienteRepository ricettaIngredienteRepository;
	
	@Transactional
	public RicettaIngrediente save(RicettaIngrediente ricettaIngrediente) {
		return this.ricettaIngredienteRepository.save(ricettaIngrediente);
	}
	
	//Metodo utile nell'editing della ricetta. Se l'utente clicca "Rimuovi" su una riga di ingrediente,chiama questo metodo
	@Transactional
	public void delete(RicettaIngrediente ricettaIngrediente) {
		this.ricettaIngredienteRepository.delete(ricettaIngrediente);
	}
	
	@Transactional
	public void deleteById(Long id) {
		this.ricettaIngredienteRepository.deleteById(id);
	}

	public RicettaIngrediente findById(Long id) {
		return this.ricettaIngredienteRepository.findById(id).orElse(null);
	}
}
