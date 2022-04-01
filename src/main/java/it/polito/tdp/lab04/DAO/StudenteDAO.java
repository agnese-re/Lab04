package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	/* 2) Implementa completamento automatico. Data la matricola mostra cognome e nome.
	 		Metodo che ritorna un oggetto di classe Studente data la matricola */
	public Studente ricercaStudente(int matricola) {
		String sql = "SELECT * "
				+ "FROM studente S "
				+ "WHERE S.matricola = ?";
		Studente studenteCercato = null;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, matricola);	// setto la matricola (al posto del ?)
			ResultSet rs = pst.executeQuery();	
			
			while(rs.next())	// UN SOLO STUDENTE POICHE' MATRICOLA PK. ATTENZIONE AL CURSORE DEL RESULTSET
				studenteCercato = new Studente(rs.getInt("matricola"), rs.getString("cognome"),
					rs.getString("nome"), rs.getString("CDS"));
			
			rs.close();
			pst.close();
			conn.close();
			return studenteCercato;
		} catch(SQLException sqle) {
			System.err.println("Errore nel DAO Studente");
			sqle.printStackTrace();
			return null;
		}
	}
	
	/* Memorizza tutti gli studenti esistenti nel DB -> per controllo esistenza matricola */
	public List<Studente> studentiTutti() {
		String sql = "SELECT * FROM STUDENTE";
		List<Studente> studentiLista = new LinkedList<Studente>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
				studentiLista.add(new Studente(rs.getInt("matricola"), rs.getString("cognome"),
					rs.getString("nome"), rs.getString("CDS")));
			
			rs.close();
			pst.close();
			conn.close();
			return studentiLista;
		} catch(SQLException sqle) {
			System.err.println("Errore nel DAO Studente");
			sqle.printStackTrace();
			return null;
		}
	}
	
	/* 4) Memorizza i corsi a cui e' iscritto uno studente, data la matricola */
	public List<Corso> studentiIscrittoA(int matricola) {
		String sql = "SELECT C.codins, C.crediti, C.nome, C.pd "
				+ "FROM studente S, iscrizione I, corso C "
				+ "WHERE S.matricola = I.matricola AND "
				+ "I.codins = C.codins AND "
				+ "S.matricola = ? ";
		List<Corso> listaCorsi = new LinkedList<Corso>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, matricola);
			ResultSet rs = pst.executeQuery();
			while(rs.next())
				listaCorsi.add(new Corso(rs.getString("codins"), rs.getInt("crediti"),
						rs.getString("nome"), rs.getInt("pd")));
			
			rs.close();
			pst.close();
			conn.close();
			return listaCorsi;
		} catch(SQLException sqle) {
			System.err.println("Errore nel DAO Studente");
			sqle.printStackTrace();
			return null;
		}	
	}
	
}
