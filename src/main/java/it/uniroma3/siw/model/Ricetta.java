package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Ricetta {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)//viene generato in modo automatico, incrementale assegnato dal DB
    //il valore dell'id viene creato automaticamente la prima volta che l'oggetto viene reso persistente nel DB
	private Long id;
	
	@Column(nullable=false)
	private String titolo;
	
	@Column(length=2000)
	private String descrizione;

	@Column(length=2000)
	private String procedimento;
	
	private String categoria;
	
	private String difficolta;
	
	@Column(length=2000)
	private String imageUrl;
	
	private Integer porzioni;
	
	private Integer tempoDiCottura;
	
	private Integer tempoDiPreparazione;
	
	private LocalDate dataInserimento;
	
	@OneToMany(mappedBy ="ricetta", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<RicettaIngrediente> ricettaIngredienti = new ArrayList<>();
	
	@OneToMany(mappedBy = "ricetta")
    private List<Recensione> recensioni;
	
	@ElementCollection
    private List<String> tags;
	
	@ManyToOne  
    private Utente autore;
	
	@ManyToMany(mappedBy = "ricetteSalvate") 
	private List<Utente> utentiCheHannoSalvato;
	
	//COSTRUTTORE
		public Ricetta() {
			this.ricettaIngredienti = new ArrayList<>();
		}

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

public String getDifficolta() {
	return difficolta;
}

public String getImageUrl() {
	return imageUrl;
}

public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
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

public void setDifficolta(String difficolta) {
	this.difficolta = difficolta;
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

public LocalDate getDataInserimento() {
	return dataInserimento;
}

public void setDataInserimento(LocalDate dataDiInserimento) {
	this.dataInserimento = dataDiInserimento;
}

public void setRicettaIngredienti(List<RicettaIngrediente> ricettaIngredienti) {
	this.ricettaIngredienti = ricettaIngredienti;
}

public List<RicettaIngrediente> getRicettaIngredienti() {
	return ricettaIngredienti;
}

public void setIngredienti(List<RicettaIngrediente> ricettaIngredienti) {
	this.ricettaIngredienti = ricettaIngredienti;
}

public Utente getAutore() {
	return autore;
}

public void setAutore(Utente autore) {
	this.autore = autore;
}

public List<Recensione> getRecensioni() {
	return recensioni;
}

public void setRecensioni(List<Recensione> recensioni) {
	this.recensioni = recensioni;
}

public List<String> getTags() {
    return tags;
}

public void setTags(List<String> tags) {
    this.tags = tags;
}

public List<Utente> getUtentiCheHannoSalvato() {
	return utentiCheHannoSalvato;
}

public void setUtentiCheHannoSalvato(List<Utente> utentiCheHannoSalvato) {
	this.utentiCheHannoSalvato = utentiCheHannoSalvato;
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