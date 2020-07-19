package model;

public class Explorer {
	// Explorationszahl: Jedes Feld wird mit einer Wertigkeit initialisiert
	// WALL=0 (wertlos)
	// Unbesuchter FORM - 7
	// Unbesuchter FLOOR - 5
	// FINISH - 6 (falls alle Formular gesammelt, sonst wie unten)

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

	public Explorer(String feldStatus, int playerId) {
		super();

		this.exploitationsZahl = 696969;

		if (feldStatus.equals("WALL")) {
			this.explorationsZahl = 0;
			this.exploitationsZahl = 999999;
		} else
		// TODO passendes Form
		if (feldStatus.contains("FORM " + playerId)) {
			this.explorationsZahl = 7;
		} else
		// Finish
		if (feldStatus.contains("FINISH " + playerId)) {
			this.exploitationsZahl = 0;
			this.explorationsZahl = 6;
		} else {
			this.explorationsZahl = 5;

		}
		this.feldStatus = feldStatus;
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

	public void setFeldStatus(String feldStatus) {
		this.feldStatus = feldStatus;
	}

}
