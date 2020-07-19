package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

//
//@author heckenmann.de
//
public class Knoten extends Point implements Comparable {

	//
	// Name des Knoten
	//
	private final String name;

	//
	// Knoten, die direkt über eine Kante mit diesem Knoten verbunden sind.
	//
	private final List<Knoten> nachbarn;

	//
	// Vorgänger
	//
	private Knoten vorgaenger;

	//
	// Legt fest, ob der Knoten der Startpunkt ist.
	//
	private final boolean startpunkt;

	//
	// Legt fest, ob der Knoten der Zielpunkt ist.
	//
	private final boolean zielpunkt;

	//
	// Konstruktor.
	//
	// @param name Name des Knoten
	// @param x x-Position
	// @param y y-Position
	// @param startpunkt Legt diesen Knoten als Startpunkt fest
	// @param zielpunkt Legt diesen Knoten als Zielpunkt fest
	//
	public Knoten(final String name, final int x, final int y, final boolean startpunkt, final boolean zielpunkt) {
		super(x, y);
		this.name = name;
		this.startpunkt = startpunkt;
		this.zielpunkt = zielpunkt;
		this.nachbarn = new ArrayList<>();
	}

	//
	// Fügt diesem Knoten einen Nachbarn hinzu und fügt dem Nachbarn diesen
	// Knoten als Nachbar hinzu.
	//
	// @param nachbar Der neue Nachbarknoten. Darf nicht null sein!
	//
	public void addNachbar(final Knoten nachbar) {
		// Nachbar hinzufügen
		if (!this.nachbarn.contains(nachbar)) {
			this.nachbarn.add(nachbar);
		}
		// Diesen Knoten als Nachbar hinzufügen
		if (!nachbar.isNachbar(this)) {
			nachbar.addNachbar(this);
		}
	}

	//
	// Prüft, ob der übergebene Knoten ein Nachbar ist.
	//
	// @param k Zu prüfender Nachbar.
	// @return True, wenn der übergebene Knoten ein Nachbar ist.
	//
	public boolean isNachbar(final Knoten k) {
		return this.nachbarn.contains(k);
	}

	//
	// Gibt die Nachbarn zurück.
	//
	// @return Liste der Nachbarn
	//
	public List<Knoten> getNachbarn() {
		return nachbarn;
	}

	//
	// Gibt den Vorgänger zurück.
	//
	// @return Vorgänger
	//
	public Knoten getVorgaenger() {
		return this.vorgaenger;
	}

	//
	// Setzt den Vorgänger.
	//
	// @param vorgaenger Vorgänger
	//
	public void setVorgaenger(Knoten vorgaenger) {
		this.vorgaenger = vorgaenger;
	}

	//
	// Berechnet die Entfernung zum Startpunkt. Wird verwendet anstatt einer
	// statisch gesetzten Entfernung.
	//
	// @return Entfernung zum Startpunkt. -1 falls keine Route zum Startpunkt
	// existiert.
	//
	public double berechneEntfernungRekursiv() {
		double entfernung = 0;
		if (this.vorgaenger != null) {
			entfernung += vorgaenger.distance(this);

			double entfernungVorgaenger = vorgaenger.berechneEntfernungRekursiv();
			// Falls momentan keine Route bis zum Startpunkt existiert, wird -1
			// zurückgegeben
			if (entfernungVorgaenger != -1) {
				entfernung += entfernungVorgaenger;
			} else {
				entfernung = entfernungVorgaenger;
			}

		} else if (!this.startpunkt) {
			// Kein Vorgänger und kein Startpunkt
			entfernung = -1;
		} else {
			// Es handelt sich um den Startpunkt
			entfernung = 0;
		}
		return entfernung;
	}

	@Override
	public int compareTo(Object o) {
		// NullPointerException und ClassCastException dürfen von der Methode geworfen
		// werden
		Knoten k = (Knoten) o;
		double differenz = this.berechneEntfernungRekursiv() - k.berechneEntfernungRekursiv();
		if (differenz == 0) {
			return 0;
		} else if (differenz > 0) {
			return 1;
		} else {
			return -1;
		}
	}

	//
	// Gibt den Namen zurück.
	//
	// @return
	//
	public String getName() {
		return this.name;
	}
}
