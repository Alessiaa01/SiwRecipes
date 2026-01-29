package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RicettaIngrediente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer quantita;
	
	private String unita;

	@ManyToOne
	@JoinColumn(name = "ricetta_id")
	private Ricetta ricetta;
	
	@ManyToOne
	@JoinColumn(name = "ingrediente_id")
	private Ingrediente ingrediente;
	
	//COSTRUTTORE VUOTO 
	public RicettaIngrediente() {
		
	}
	
	//METODI GETTER E SETTER 
	public Long getId() {
		return id;
	}

	public Integer getQuantita() {
		return quantita;
	}

	public String getUnita() {
		return unita;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setQuantita(Integer quantita) {
		this.quantita = quantita;
	}

	public void setUnita(String unita) {
		this.unita = unita;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RicettaIngrediente other = (RicettaIngrediente) obj;
		return Objects.equals(id, other.id);
	}
	
}
