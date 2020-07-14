package model;

public abstract class KartenElement {

	private Koordinate koordinaten;

	public KartenElement(Koordinate koordinaten) {
		super();
		this.koordinaten = koordinaten;
	}

	public Koordinate getKoordinaten() {
		return koordinaten;
	}

	public void setKoordinaten(Koordinate koordinaten) {
		this.koordinaten = koordinaten;
	}

}
