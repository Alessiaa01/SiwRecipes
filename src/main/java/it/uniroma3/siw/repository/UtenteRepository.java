package it.uniroma3.siw.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Utente;

public interface UtenteRepository extends JpaRepository<Utente,Long>{
	
//controllo "email già in uso"
	public boolean existsByEmail(String email);
	
	public Optional<Utente> findByEmail(String email);
}
