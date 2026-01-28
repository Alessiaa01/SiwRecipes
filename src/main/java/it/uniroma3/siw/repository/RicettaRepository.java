package it.uniroma3.siw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Ricetta;

public interface RicettaRepository extends JpaRepository<Ricetta, Long> {

}
