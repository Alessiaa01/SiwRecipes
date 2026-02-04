package it.uniroma3.siw.model;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
//import jakarta.validation.constraints.NotBlank;
@Entity
public class Credentials {
	public static final String DEFAULT_ROLE = "DEFAULT";
	public static final String ADMIN_ROLE = "ADMIN";
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
    @Column(nullable = false, unique=true) 
	private String username;
    

	private String password;
    
    @Column(nullable=false)
	private String ruolo;

	@OneToOne(cascade = CascadeType.ALL)
	private Utente utente;
	

	// true = attivo, false = bannato
    private boolean enabled = true;
    
    //COSTRUTTORE
    public Credentials() {
    	
    }

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

	//non possono esistere due utenti con lo stesso username nel sistema 
	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Credentials other = (Credentials) obj;
		return Objects.equals(username, other.username);
	}	
	
}
