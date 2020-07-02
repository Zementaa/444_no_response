package model;

public class Zug {
	private int schrittzahl = 0;
	private Koordinaten koordinaten;
	private String vorzug = "";

	public Zug(int startX, int startY) {
		super();
		koordinaten = new Koordinaten();
		this.koordinaten.setX(startX);
		this.koordinaten.setY(startY);
	}

	public void aktualisieren(String richtung) {
		schrittzahl++;

		switch (richtung) {
		case "go north":
			koordinaten.setY(koordinaten.getY() - 1);
			setVorzug("go south");
			break;
		case "go east":
			koordinaten.setX(koordinaten.getY() + 1);
			setVorzug("go west");
			break;
		case "go south":
			koordinaten.setY(koordinaten.getY() + 1);
			setVorzug("go north");
			break;
		default:
			koordinaten.setX(koordinaten.getY() - 1);
			setVorzug("go east");

		}

	}

	public int getSchrittzahl() {
		return schrittzahl;
	}

	public void setSchrittzahl(int schrittzahl) {
		this.schrittzahl = schrittzahl;
	}

	public Koordinaten getKoordinaten() {
		return koordinaten;
	}

	public void setKoordinaten(Koordinaten koordinaten) {
		this.koordinaten = koordinaten;
	}

	public String getVorzug() {
		return vorzug;
	}

	public void setVorzug(String vorzug) {
		this.vorzug = vorzug;
	}

}
