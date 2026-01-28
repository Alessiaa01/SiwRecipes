package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ricetta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String titolo;
	
	private String descrizione;
	
	private String procedimento;
	
	private String categoria;
	
	private String difficoltà;
	
	private String imageUrl;
	
	private Integer porzioni;
	
	private Integer tempoDiCottura;
	
	private Integer tempoDiPreparazione;


//METODI GETTER E SETTER 

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
	}

public String getTitolo() {
	return titolo;
}

public void setTitolo(String titolo) {
	this.titolo = titolo;
}

public String getDescrizione() {
	return descrizione;
}

public String getProcedimento() {
	return procedimento;
}

public String getCategoria() {
	return categoria;
}

public String getDifficoltà() {
	return difficoltà;
}

public String getImageUrl() {
	return imageUrl;
}

public Integer getPorzioni() {
	return porzioni;
}

public Integer getTempoDiCottura() {
	return tempoDiCottura;
}

public Integer getTempoDiPreparazione() {
	return tempoDiPreparazione;
}

public void setDescrizione(String descrizione) {
	this.descrizione = descrizione;
}

public void setProcedimento(String procedimento) {
	this.procedimento = procedimento;
}

public void setCategoria(String categoria) {
	this.categoria = categoria;
}

public void setDifficoltà(String difficoltà) {
	this.difficoltà = difficoltà;
}

public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}

public void setPorzioni(Integer porzioni) {
	this.porzioni = porzioni;
}

public void setTempoDiCottura(Integer tempoDiCottura) {
	this.tempoDiCottura = tempoDiCottura;
}

public void setTempoDiPreparazione(Integer tempoDiPreparazione) {
	this.tempoDiPreparazione = tempoDiPreparazione;
}

@Override
public int hashCode() {
	return Objects.hash(id, titolo);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Ricetta other = (Ricetta) obj;
	return Objects.equals(id, other.id) && Objects.equals(titolo, other.titolo);
}

}