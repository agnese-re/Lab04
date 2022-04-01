package it.polito.tdp.lab04.model;

public class Corso {
	
	private String codins;
	private Integer crediti;
	private String nome;
	private Integer pd;
	
	/* COSTRUTTORE PARAMETRIZZATO */
	public Corso(String codins, Integer crediti, String nome, Integer pd) {
		this.codins = codins;
		this.crediti = crediti;
		this.nome = nome;
		this.pd = pd;
	}
	
	public Corso(String codins) {
		this.codins = codins;
	}

	/* METODI GETTER/SETTER */
	public String getCodins() {
		return codins;
	}

	public void setCodins(String codins) {
		this.codins = codins;
	}

	public Integer getCrediti() {
		return crediti;
	}

	public void setCrediti(Integer crediti) {
		this.crediti = crediti;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getPd() {
		return pd;
	}

	public void setPd(Integer pd) {
		this.pd = pd;
	}
	
	/* METODO TOSTRING PER DESCRIZIONE CORSO */
	@Override
	public String toString() {
		return codins + " " + crediti + " " + nome + " " + pd;
	}
}
