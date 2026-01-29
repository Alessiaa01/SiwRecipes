package it.uniroma3.siw.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
//import jakarta.validation.constraints.NotBlank;

public class Credentials {
	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    @Column(nullable = false, unique=true) 
	private String username;
    
    @Column(nullable=false)
	private String password;
    
    @Column(nullable=false)
	private String ruolo;

	@OneToOne(cascade = CascadeType.ALL)
	private Utente utente;
	
	
	// true = attivo, false = bannato
    private boolean enabled = true;

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getRuolo() {
		return ruolo;
	}

	public Utente getUtente() {
		return utente;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
