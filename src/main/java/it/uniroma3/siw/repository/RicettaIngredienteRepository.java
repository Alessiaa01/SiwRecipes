package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.RicettaIngrediente;

public interface RicettaIngredienteRepository extends JpaRepository <RicettaIngrediente,Long> {
 
	//Trova tutte le righe di ingredienti per una specifica ricetta
	public List<RicettaIngrediente> findByRicetta(Ricetta ricetta);
}
