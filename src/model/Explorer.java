package model;

/**
 * 
 * @author C.Camier
 * @author D.Kleemann
 * @author C.Peters
 * @author L.Wascher
 * 
 * Feststellung der Wertigkeit von den bekannten Feldern
 * Deklarierung und Initialisierung der Attribute
 */
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

	public Explorer(String feldStatus, int playerId, int countForms) {
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
	}
	
	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @return
	 */
	public int getExploitationsZahl() {
		return exploitationsZahl;
	}
	
	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @param exploitationsZahl
	 */
	public void setExploitationsZahl(int exploitationsZahl) {
		this.exploitationsZahl = exploitationsZahl;
	}

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @return
	 */
	public int getExplorationsZahl() {
		return explorationsZahl;
	}

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @param explorationsZahl
	 */
	public void setExplorationsZahl(int explorationsZahl) {
		this.explorationsZahl = explorationsZahl;
	}

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * @return
	 */
	public String getFeldStatus() {
		return feldStatus;
	}

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @param feldStatus
	 * @param playerId
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