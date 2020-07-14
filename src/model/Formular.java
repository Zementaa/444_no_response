package model;

public class Formular extends KartenElement {
	private int id;

	public Formular(Koordinate koordinate, int id) {
		super(koordinate);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
