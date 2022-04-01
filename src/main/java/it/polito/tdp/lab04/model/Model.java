package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {

	CorsoDAO corsoDao;
	StudenteDAO studenteDao;
	
	public Model() {
		corsoDao = new CorsoDAO();
		studenteDao = new StudenteDAO();
	}
	
	/* DA CORSO DAO A MODEL */
	public List<Corso> getTuttiICorsi() {
		return corsoDao.getTuttiICorsi();
	}
	
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {
		return corsoDao.getStudentiIscrittiAlCorso(corso);
	}
	
	public boolean studenteIscrittoAlCorso(int matricola, Corso corso) {
		return corsoDao.studenteIscrittoAlCorso(matricola, corso);
	}
	
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		return corsoDao.inscriviStudenteACorso(studente, corso);
	}
	
	/* DA STUDENTE DAO A MODEL */
	public Studente ricercaStudente(int matricola) {
		return studenteDao.ricercaStudente(matricola);
	}
	
	public List<Studente> getTuttiStudenti() {
		return studenteDao.studentiTutti();
	}
	
	public List<Corso> studenteIscrittoA(int matricola) {
		return studenteDao.studentiIscrittoA(matricola);
	}
	
}
