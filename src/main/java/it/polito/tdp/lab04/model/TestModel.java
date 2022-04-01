package it.polito.tdp.lab04.model;

import java.util.List;

public class TestModel {	// per testare il modello

	public static void main(String[] args) {

		Model model = new Model();
		
		List<Corso> listaCorsi = model.getTuttiICorsi();
		
		Studente studenteCercato = model.ricercaStudente(169195);
		System.out.println(studenteCercato.toString());

	}

}
