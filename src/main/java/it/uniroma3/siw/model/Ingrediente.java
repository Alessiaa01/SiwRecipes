package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Ingrediente {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true) //impedisce la creazione di doppioni
	private String nome;
	
	@OneToMany(mappedBy = "ingrediente", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<RicettaIngrediente> ricettaIngredienti = new ArrayList<>();
	//costruttore vuoto
	public Ingrediente () {
		
	}
	
	public Ingrediente(String nome) {
		this.nome = nome;
		
	}

	//METODI GETTER E SETTER
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	//Non voglio avere due righe chiamate nello stesso modo 
	@Override
	public int hashCode() {
		return Objects.hash(nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ingrediente other = (Ingrediente) obj;
		return Objects.equals(nome, other.nome);
	}

	
	
}
