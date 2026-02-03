package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Utente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome;
	
	private String cognome;
	
	private String email;
	
	private LocalDate dataDiNascita;

	@OneToMany(mappedBy="autore", cascade= CascadeType.ALL)
	private List <Ricetta> ricetteScritte = new ArrayList<>();
	
	@OneToOne(mappedBy = "utente", cascade = CascadeType.ALL)
    private Credentials credentials;
	
	@ManyToMany
	private List<Ricetta> ricetteSalvate;
	
	// COSTRUTTORE
	public Utente() {
		this.ricetteScritte=new ArrayList<>();
	}
	
	//METODI GETTER E SETTER
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	
	public List<Ricetta> getRicetteScritte() {
		return ricetteScritte;
	}

	public void setRicetteScritte(List<Ricetta> ricetteScritte) {
		this.ricetteScritte = ricetteScritte;
	}

	
	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public List<Ricetta> getRicetteSalvate() {
		return ricetteSalvate;
	}

	public void setRicetteSalvate(List<Ricetta> ricetteSalvate) {
		this.ricetteSalvate = ricetteSalvate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utente other = (Utente) obj;
		return Objects.equals(email, other.email) && Objects.equals(nome, other.nome);
	}
	
}
