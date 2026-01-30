package it.uniroma3.siw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Ricetta;
import it.uniroma3.siw.model.Utente;

public interface RicettaRepository extends JpaRepository<Ricetta, Long> {

	public boolean existsByTitolo(String titolo);
	
	//trova tutte le ricette che contengono una parola nel titolo
	List <Ricetta> findByTitoloContainingIgnoreCase(String titolo);
	
	//trova tutte le ricette di un certo autore 
	public List<Ricetta> findByAutore(Utente autore);
	
	
}
