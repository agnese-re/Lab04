package it.polito.tdp.lab04.model;

public class Studente implements Comparable<Studente>{

	private Integer matricola;
	private String cognome;
	private String nome;
	private String CDS;
	
	public Studente(Integer matricola, String cognome, String nome, String CDS) {
		this.matricola = matricola;
		this.cognome = cognome;
		this.nome = nome;
		this.CDS = CDS;
	}

	public Integer getMatricola() {
		return matricola;
	}

	public void setMatricola(Integer matricola) {
		this.matricola = matricola;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCDS() {
		return CDS;
	}

	public void setCDS(String CDS) {
		this.CDS = CDS;
	}
	
	@Override
	public String toString() {
		return matricola + " " + cognome + " " + nome + " " + CDS;
	}
	
	@Override
	public int compareTo(Studente s) {
		if(!this.getCognome().equals(s.getCognome()))	// cognome diverso
			return this.getCognome().compareTo(s.getCognome());
		else	// stesso cognome. Ordino per nome
			return this.getNome().compareTo(s.getNome());
	}
	
}
