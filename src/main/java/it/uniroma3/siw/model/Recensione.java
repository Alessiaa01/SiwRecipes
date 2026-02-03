	package it.uniroma3.siw.model;

	import jakarta.persistence.Entity;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.ManyToOne;

	@Entity  
	public class Recensione {

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;

	    private String titolo;
	    private String testo;
	    private Integer voto;
	    
	    @ManyToOne
	    private Utente autore;
	 
		// Collegamento inverso verso la Ricetta
	    @ManyToOne 
	    private Ricetta ricetta;

	    // Getter e Setter (obbligatori)
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }
	    
	    public String getTitolo() { return titolo; }
	    public void setTitolo(String titolo) { this.titolo = titolo; }
	    
	    public String getTesto() { return testo; }
	    public void setTesto(String testo) { this.testo = testo; }
	    
	    public Ricetta getRicetta() { return ricetta; }
	    public void setRicetta(Ricetta ricetta) { this.ricetta = ricetta; }
	    
	    public Integer getVoto() {
			return voto;
		}
		public void setVoto(Integer voto) {
			this.voto = voto;
		}
		public Utente getAutore() {
			return autore;
		}
		public void setAutore(Utente autore) {
			this.autore = autore;
		}
	}
