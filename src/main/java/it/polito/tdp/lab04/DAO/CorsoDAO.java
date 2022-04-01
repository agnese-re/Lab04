package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/* 0) Ottengo tutti i corsi salvati nel DB. Memorizzo all'interno di una lista */ 
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				System.out.println(codins + " " + numeroCrediti + " " + nome + " " + periodoDidattico);	// per testare con TestDB

				// Crea un nuovo JAVA Bean Corso
				Corso corso = new Corso(codins, numeroCrediti,nome,periodoDidattico);
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				corsi.add(corso);
			}
			
			rs.close();
			st.close();
			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/* Dato un codice insegnamento, ottengo il corso. Stampa del corso trovato con una
	 		System.out.println() poiche' si richiede void che tipo di ritorno */
	public void getCorso(Corso corso) {
		String sql = "SELECT * "
				+ "FROM corso C "
				+ "WHERE C.codins = ? ";
		Corso corsoCercato = null;
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, corso.getCodins());
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
				corsoCercato = new Corso(rs.getString("codins"), rs.getInt("crediti"), 
						rs.getString("nome"), rs.getInt("pd"));
			System.out.println(corsoCercato.toString());	// stampa del corso
			rs.close();
			pst.close();
			conn.close();
			return;
		} catch(SQLException sqle) {
			System.err.println("Errore nel DAO Corso");
			sqle.printStackTrace();
			return;
		}
	}

	/* 3) Ottengo tutti gli studenti iscritti al Corso. Memorizzo in una lista */
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		String sql = "SELECT S.matricola, S.cognome, S.nome, S.CDS "
				+ "FROM studente S, iscrizione I "
				+ "WHERE S.matricola = I.matricola AND I.codins = ?";
		List<Studente> studentiLista = new LinkedList<Studente>();
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, corso.getCodins()); 	// setto il codice del corso, al posto del ?
			ResultSet rs = pst.executeQuery();	
			
			while(rs.next())	// UN SOLO STUDENTE POICHE' MATRICOLA PK. ATTENZIONE AL CURSORE DEL RESULTSET
				studentiLista.add(new Studente(rs.getInt("matricola"), rs.getString("cognome"),
					rs.getString("nome"), rs.getString("CDS")));
			// for(Studente s: studentiLista)	// stampa della lista
				// System.out.println(s.toString());
			
			rs.close();
			pst.close();
			conn.close();
			return studentiLista;
		} catch(SQLException sqle) {
			System.err.println("Errore nel DAO Corso");
			sqle.printStackTrace();
			return null;
		}
	}

	/* 5) Verifica se uno studente, di cui e' data la matricola, e' iscritto al corso selezionato */
	public boolean studenteIscrittoAlCorso(int matricola, Corso corso) {
		
		boolean studenteGiaIscritto = false;
		
		// Considero tutti gli studenti iscritti a quel corso
		List<Studente> studentiIscritti = this.getStudentiIscrittiAlCorso(corso);
		// Controllo se fra gli studenti iscritti al corso ne esiste uno con la matricola passata come parametro
		for(Studente studente: studentiIscritti)
			if(studente.getMatricola() == matricola)
				studenteGiaIscritto = true;
		return studenteGiaIscritto;
	}
	
	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		boolean iscrizioneAvvenuta = false;
		String sql = "INSERT INTO iscrizione (matricola, codins) "
				+ "VALUES (?, ?)";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, studente.getMatricola());
			pst.setString(2, corso.getCodins());
			int res = pst.executeUpdate();
			
			iscrizioneAvvenuta = true;
			pst.close();
			conn.close();
		} catch(SQLException sqle) {
			System.err.println("Errore nel DAO Corso");
			sqle.printStackTrace();
		}
		
		return iscrizioneAvvenuta;
	}

}
