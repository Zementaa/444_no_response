package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Algorithmus {

	private final Knoten startpunkt;
	private final Knoten zielpunkt;
	private final List<Knoten> prioListe; // Knoten sortiert nach Entfernung

	//
	// Konstruktor. Achtung! Die Klasse ver�ndert die Werte in den �bergebenen
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
	// Sucht den k�rzestens Weg zum Zielpunkt.
	//
	private void sucheWeg() {
		this.prioListe.add(this.startpunkt);

		while (!this.prioListe.isEmpty()) {
			this.berechneNaechsteKnoten(this.prioListe.get(0));
		}
	}

	//
	// Hilfsmethode f�r die Methode sucheWeg.
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
			// F�r jeden Nachbar den Vorg�nger setzen, falls eine k�rzere Route gefunden
			// wurde.
			double distanz = aktuellerKnoten.distance(aktuellerNachbar);
			if (aktuellerNachbar.berechneEntfernungRekursiv() > aktuellerKnoten.berechneEntfernungRekursiv() + distanz
					|| aktuellerNachbar.berechneEntfernungRekursiv() == -1) {
				aktuellerNachbar.setVorgaenger(aktuellerKnoten);
			}

			// Falls der aktuelleNachbar NICHT in der Prioliste vorhanden ist, wird er
			// hinzugef�gt.
			if (!this.prioListe.contains(aktuellerNachbar)) {
				this.prioListe.add(aktuellerNachbar);
			}
			Collections.sort(this.prioListe); // ...muss trotzdem ausgef�hrt werden, weil sich die Entfernung ge�ndert
												// haben kann.
		}
	}

	//
	// Gibt die Reihenfolge der Knoten zur�ck.
	// @return Reihenfolge der Knoten des k�rzestens Weges.
	//
	public List<Knoten> getErgebnisReihenfolge() {
		List<Knoten> er = new ArrayList<>();
		for (Knoten k = zielpunkt; k != null; k = k.getVorgaenger()) {
			er.add(0, k);
		}
		return er;
	}

	//
	// Gibt die minimale Entfernung zur�ck.
	//
	// @return Minimale Entfernung
	//
	public double getMinimaleEntfernung() {
		return this.zielpunkt.berechneEntfernungRekursiv();
	}
}