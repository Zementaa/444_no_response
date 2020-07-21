package model;

/**
 * Feststellung der Wertigkeit von den bekannten Feldern Deklarierung und
 * Initialisierung der Attribute.
 * 
 * @author C.Camier
 * @author D.Kleemann
 * @author C.Peters
 * @author L.Wascher
 * 
 */
public class Explorer {
	// Explorationszahl: Jedes Feld wird mit einer Wertigkeit initialisiert
	// WALL=0 (wertlos)
	// Unbesuchter FORM - 7
	// Unbesuchter FLOOR - 5
	// FINISH - 6 (falls alle Formular gesammelt, sonst wie unten)
	// SHEET - 5

	// Werden in Laufzeit initialisiert
	// TODO Was wenn jemand ein Formular in eine Sackgasse kickt
	// Besucht (Sackgasse) - 1
	// Besucht (Gasse) - 2
	// Besucht (3-Feld-Kreuzung) - 3
	// Besucht (4-Feld-Kreuzung) - 4

	// Exploitationszahl: Jedes Feld wird mit einer Wertigkeit initialisiert
	// WALL=999999 (unendlich)
	// alle andere 696969
	private int explorationsZahl;
	private int exploitationsZahl;
	private String feldStatus;
	private int x;
	private int y;

	public Explorer(String feldStatus, int playerId, int countForms, int x, int y) {
		super();

		this.exploitationsZahl = 696969;

		// Wände
		if (feldStatus.equals("WALL")) {
			this.explorationsZahl = 0;
			this.exploitationsZahl = 999999;
		} else
		// Passendes Formular
		if (feldStatus.contains("FORM " + playerId + " " + countForms)) {
			this.explorationsZahl = 7;
		} else
		// Sachbearbeiter
		if (feldStatus.contains("FINISH " + playerId)) {
			this.exploitationsZahl = 0;
			this.explorationsZahl = 6;
		}
		// Alles andere
		else {
			this.explorationsZahl = 5;

		}
		this.feldStatus = feldStatus;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getExploitationsZahl() {
		return exploitationsZahl;
	}

	public void setExploitationsZahl(int exploitationsZahl) {
		this.exploitationsZahl = exploitationsZahl;
	}

	public int getExplorationsZahl() {
		return explorationsZahl;
	}

	public void setExplorationsZahl(int explorationsZahl) {
		this.explorationsZahl = explorationsZahl;
	}

	public String getFeldStatus() {
		return feldStatus;
	}

	/**
	 * Setzen des Feldstatus unter Beachtung von Constraints.
	 * 
	 * Eigenes Formular Explorationszahl auf 7, sonst 5.
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @param feldStatus
	 * @param playerId
	 *
	 */
	public void setFeldStatus(String feldStatus, int playerId) {

		if (feldStatus.contains("FORM " + playerId)) {
			this.explorationsZahl = 7;

		} else {
			this.explorationsZahl = 5;

		}
		this.feldStatus = feldStatus;
	}
}