package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithmus {

	private final Knoten startpunkt;
	private final Knoten zielpunkt;
	private final List<Knoten> prioListe; // Knoten sortiert nach Entfernung

	//
	// Konstruktor. Achtung! Die Klasse verändert die Werte in den übergebenen
	// Knoten.
	//
	// @param startpunkt Startpunkt im Graph
	// @param zielpunkt Zielpunkt im Graph
	//
	public Algorithmus(final Knoten startpunkt, final Knoten zielpunkt) {
		this.startpunkt = startpunkt;
		this.zielpunkt = zielpunkt;
		this.prioListe = new ArrayList<>();
		sucheWeg();
	}

	//
	// Sucht den kürzestens Weg zum Zielpunkt.
	//
	private void sucheWeg() {
		this.prioListe.add(this.startpunkt);

		while (!this.prioListe.isEmpty()) {
			this.berechneNaechsteKnoten(this.prioListe.get(0));
		}
	}

	//
	// Hilfsmethode für die Methode sucheWeg.
	//
	private void berechneNaechsteKnoten(final Knoten aktuellerKnoten) {
		System.out.println(aktuellerKnoten.getName() + ": " + aktuellerKnoten.berechneEntfernungRekursiv());
		// Aus der Prioliste entfernen
		this.prioListe.remove(aktuellerKnoten);

		// Falls es sich um den Zielpunkt handelt, wird abgebrochen
		if (aktuellerKnoten.equals(this.zielpunkt)) {
			return;
		}

		// Die Nachbarn nach Entfernung sortieren.
		List<Knoten> kReihenfolge = new ArrayList<>();
		kReihenfolge.addAll(aktuellerKnoten.getNachbarn());
		kReihenfolge.remove(aktuellerKnoten.getVorgaenger());
		Collections.sort(kReihenfolge);

		// Jeden direkten Nachbar durchlaufen.
		for (Knoten aktuellerNachbar : kReihenfolge) {
			// Für jeden Nachbar den Vorgänger setzen, falls eine kürzere Route gefunden
			// wurde.
			double distanz = aktuellerKnoten.distance(aktuellerNachbar);
			if (aktuellerNachbar.berechneEntfernungRekursiv() > aktuellerKnoten.berechneEntfernungRekursiv() + distanz
					|| aktuellerNachbar.berechneEntfernungRekursiv() == -1) {
				aktuellerNachbar.setVorgaenger(aktuellerKnoten);
			}

			// Falls der aktuelleNachbar NICHT in der Prioliste vorhanden ist, wird er
			// hinzugefügt.
			if (!this.prioListe.contains(aktuellerNachbar)) {
				this.prioListe.add(aktuellerNachbar);
			}
			Collections.sort(this.prioListe); // ...muss trotzdem ausgeführt werden, weil sich die Entfernung geändert
												// haben kann.
		}
	}

	//
	// Gibt die Reihenfolge der Knoten zurück.
	// @return Reihenfolge der Knoten des kürzestens Weges.
	//
	public List<Knoten> getErgebnisReihenfolge() {
		List<Knoten> er = new ArrayList<>();
		for (Knoten k = zielpunkt; k != null; k = k.getVorgaenger()) {
			er.add(0, k);
		}
		return er;
	}

	//
	// Gibt die minimale Entfernung zurück.
	//
	// @return Minimale Entfernung
	//
	public double getMinimaleEntfernung() {
		return this.zielpunkt.berechneEntfernungRekursiv();
	}
}