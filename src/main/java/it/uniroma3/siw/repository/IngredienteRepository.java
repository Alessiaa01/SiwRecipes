package it.uniroma3.siw.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.uniroma3.siw.model.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente,Long> {

	//prima di crearlo, verifica se esiste già un ingrediente con quel nome
	public boolean existsByNomeIgnoreCase(String nome);
	
	//per recuperare un ingrediente specifico 
	public Optional <Ingrediente> findByNome(String nome);
	
	//per cercare un ingrediente
	public List<Ingrediente> findByNomeContainingIgnoreCase(String nome);
}
