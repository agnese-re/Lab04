/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.lab04;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model modello;	// per interagire con il modello
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbCorsi"
    private ComboBox<String> cmbCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtRisultato"
    private TextArea txtRisultato; // Value injected by FXMLLoader

    @FXML
    void handleCercaCorsiStudente(ActionEvent event) {
    	txtRisultato.clear();
    	
    	String matricolaInserita = txtMatricola.getText();
    	int matricolaNumerica;
    	boolean esisteStudente = false;
    	
    	// Controllo che il campo matricola non sia vuoto
    	if(matricolaInserita.equals("") || matricolaInserita == null) {
    		txtRisultato.appendText("Inserisci una matricola");
    		return;
    	}
    	
    	// Controllo che la matricola inserita sia numerica
    	try {
    		matricolaNumerica = Integer.parseInt(matricolaInserita);
    	} catch(NumberFormatException nfe) {
    		txtRisultato.appendText("Inserisci una matricola numerica");
    		return;
    	}
    	
    	// Controllo che la matricola inserita corrisponda ad uno studente esistente
    	for(Studente studente: modello.getTuttiStudenti())
    		if(studente.getMatricola() == matricolaNumerica) 
    			esisteStudente = true;
    	if(!esisteStudente) {
    		txtRisultato.appendText("La matricola inserita NON e' associata "
    				+ "ad alcun studente esistente");
    		return;
    	}
    	
    	// Se la matricola soddisfa i controlli precedenti
    	List<Corso> corsiACuiEIscritto = modello.studenteIscrittoA(matricolaNumerica);
    	txtRisultato.appendText("Lo studente e' iscritto a " + corsiACuiEIscritto.size() + " corsi" + "\n");
    	for(Corso corso: corsiACuiEIscritto)
    		txtRisultato.appendText(corso.toString() + "\n");	
    }

    @FXML
    void handleCompleta(ActionEvent event) {
    	txtRisultato.clear();
    	
    	String matricolaInserita = txtMatricola.getText();
    	int matricolaNumerica;
    	boolean esisteStudente = false;
    	
    	// Controllo che il campo matricola non sia vuoto
    	if(matricolaInserita.equals("") || matricolaInserita == null) {
    		txtRisultato.appendText("Inserisci una matricola");
    		return;
    	}
    	
    	// Controllo che la matricola inserita sia numerica
    	try {
    		matricolaNumerica = Integer.parseInt(matricolaInserita);
    	} catch(NumberFormatException nfe) {
    		txtRisultato.appendText("Inserisci una matricola numerica");
    		return;
    	}
    	
    	// Controllo che la matricola inserita corrisponda ad uno studente esistente
    	for(Studente studente: modello.getTuttiStudenti())
    		if(studente.getMatricola() == matricolaNumerica) 
    			esisteStudente = true;
    	if(!esisteStudente) {
    		txtRisultato.appendText("La matricola inserita NON e' associata "
    				+ "ad alcun studente esistente");
    		return;
    	}
    	
    	// Se la matricola e' numerica e lo studente esiste
    	Studente studenteCercato = modello.ricercaStudente(matricolaNumerica);
    	txtCognome.setText(studenteCercato.getCognome());
    	txtNome.setText(studenteCercato.getNome());
    }

    @FXML
    void handleIscrittiCorso(ActionEvent event) {
    	txtRisultato.clear();
    	
    	String nomeCorsoScelto = cmbCorsi.getValue();
    	// Cerco il corso a cui e' associato quel nome
    	Corso corsoScelto = null;
    	for(Corso corso: modello.getTuttiICorsi())
    		if(corso.getNome().equals(nomeCorsoScelto))
    			corsoScelto = corso;
    	
    	if(corsoScelto == null) {
    		txtRisultato.appendText("Errore: non e' stato scelto alcun corso");
    		return;
    	}
    	
    	// Stampa studenti iscritti al corso
    	List<Studente> studentiIscritti = modello.getStudentiIscrittiAlCorso(corsoScelto);
    	Collections.sort(studentiIscritti);		// ordino per cognome e nome
    	txtRisultato.appendText(studentiIscritti.size() + " studenti iscritti" + "\n");
    	for(Studente studente: studentiIscritti)
    		txtRisultato.appendText(studente.toString() + "\n");
    }

    @FXML
    void handleIscriviStudenteCorso(ActionEvent event) {
    	txtRisultato.clear();
    	
    	String matricolaInserita = txtMatricola.getText();
    	Studente studenteScelto = null;
    	
    	String nomeCorsoScelto = cmbCorsi.getValue();
    	// Cerco il corso a cui e' associato quel nome
    	Corso corsoScelto = null;
    	for(Corso corso: modello.getTuttiICorsi())
    		if(corso.getNome().equals(nomeCorsoScelto))
    			corsoScelto = corso;
    	
    	int matricolaNumerica;
    	boolean esisteStudente = false;
    	
    	// Controllo che il campo matricola non sia vuoto
    	if(matricolaInserita.equals("") || matricolaInserita == null) {
    		txtRisultato.appendText("Inserisci una matricola");
    		return;
    	}
    	
    	// Controllo che la matricola inserita sia numerica
    	try {
    		matricolaNumerica = Integer.parseInt(matricolaInserita);
    	} catch(NumberFormatException nfe) {
    		txtRisultato.appendText("Inserisci una matricola numerica");
    		return;
    	}
    	
    	// Controllo che la matricola inserita corrisponda ad uno studente esistente
    	for(Studente studente: modello.getTuttiStudenti())
    		if(studente.getMatricola() == matricolaNumerica) {
    			studenteScelto = studente;
    			esisteStudente = true;
    		}
    	if(!esisteStudente) {
    		txtRisultato.appendText("La matricola inserita NON e' associata "
    				+ "ad alcun studente esistente");
    		return;
    	}
    	
    	if(modello.studenteIscrittoAlCorso(matricolaNumerica, corsoScelto) == true)
    		txtRisultato.appendText("Studente gia' iscritto a questo corso");
    	else { 	// lo studente non e' ancora iscritto. Lo iscrivo
    		modello.inscriviStudenteACorso(studenteScelto, corsoScelto);
    		txtRisultato.appendText("Studente iscritto al corso!");
    	}
    }

    @FXML
    void handleReset(ActionEvent event) {
    	txtRisultato.clear();
    	txtMatricola.clear();
    	txtCognome.clear();
    	txtNome.clear();
    	cmbCorsi.setValue(null);
    }
    
    public void setModel(Model modello) {
    	this.modello = modello;
    	popolaComboBox();	// popolo comboBox solo dopo aver settato il modello 
    }
    
    /* 1) Popolo comboBox, menu' a tendina */
    public void popolaComboBox() {
    	for(Corso corso: modello.getTuttiICorsi())
    		cmbCorsi.getItems().add(corso.getNome());
    	cmbCorsi.getItems().add("");	// campo vuoto per non selezionare alcun corso
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    
    /* Il metodo viene invocato quando l'oggetto di classe FXMLLoader e' stato creato. 
     	Al momento non e' ancora stato chiamato il metodo setModel dell'oggetto di classe
     		FXMLController, dunque il modello e' ancora a null. Non e' quindi possibile 
     		popolare qui la comboBox, dovendo richiamare il metodo getTuttiICorsi su un
     		oggetto di classe Model ancora a null -> NullPointerException */
    
    void initialize() {
    	assert cmbCorsi != null : "fx:id=\"cmbCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }

}