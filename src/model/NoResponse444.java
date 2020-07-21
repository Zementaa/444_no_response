
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Klasse des Bots namens NoResponse444 und Deklarierung und teilweise
 * Initialisierung der Attribute.
 *
 * @author C.Camier
 * @author D.Kleemann
 * @author C.Peters
 * @author L.Wascher
 *
 */
public class NoResponse444 {

	// Collections
	private static List<Vertex> liste = new ArrayList<>();
	private static List<String> cellStati = new ArrayList<>();
	private static List<String> tempCellStati = new ArrayList<>();
	private static Explorer[][] karte;

	// Wertsemantik
	private static int playerId;
	private static int jetztX;
	private static int jetztY;
	private static int vorherX;
	private static int vorherY;
	private static int zielX;
	private static int zielY;
	private static int kartenGroesse;
	private static int sizeX;
	private static int sizeY;
	private static int zieleAufgedeckt = 0;
	private static int floorCount;
	private static int formCount = -1;
	private static int countForms = 0;
	private static boolean habeMichBewegt = false;
	private static boolean exploitation = false;
	private static int anzahlSheets = 0;
	private static boolean sachbearbeiterGefunden = false;
	private static int schrittZaehler = 0;
	private static int listZaehler = 0;

	// Referenzsemantik
	private static Kombinatorik kombinator;
	private static String ausgabe = "";
	private static String meinFinish;
	private static String meinForm;
	private static String lastActionsResult;
	private static String currentCellStatus;

	/**
	 * Initialisierung der Eingabe-Attribute
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Scanner zum Auslesen der Standardeingabe, welche Initialisierungs- und
		// Rundendaten liefert
		Scanner input = new Scanner(System.in);

		// INIT - Auslesen der Initialdaten
		init(input);

		// TURN (Wiederholung je Runde notwendig)
		turn(input);

		// Eingabe schliessen (letzte Aktion)
		input.close();

	}

	/**
	 * Eingabe der Informationen zum Labyrinth und dem eigenen Spieler (Bot)
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @param input
	 */
	private static void init(Scanner input) {
		// 1. Zeile: Maze Infos
		sizeX = input.nextInt(); // X-Groesse des Spielfeldes (Breite)
		sizeY = input.nextInt(); // Y-Groesse des Spielfeldes (Hoehe)
		int level = input.nextInt(); // Level des Matches
		input.nextLine(); // Beenden der ersten Zeile
		// 2. Zeile: Player Infos
		playerId = input.nextInt(); // id dieses Players / Bots
		jetztX = input.nextInt(); // X-Koordinate der Startposition dieses Player
		jetztY = input.nextInt(); // Y-Koordinate der Startposition dieses Players
		if (level == 5) {
			anzahlSheets = input.nextInt();
		}

		input.nextLine(); // Beenden der zweiten Zeile

		kartenGroesse = sizeX * sizeY;
		meinFinish = "FINISH " + playerId + " ";
		meinForm = "FORM " + playerId + " ";
		karte = new Explorer[sizeX][sizeY];

		// Kartenelemente initialisieren
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				karte[i][j] = new Explorer("INIT", playerId, -9, i, j);
			}
		}

		// FIXME erstes Feld
		karte[jetztX][jetztY].setExplorationsZahl(1);
		karte[jetztX][jetztY].setFeldStatus("START", level);
		kombinator = new Kombinatorik(playerId, sizeX, sizeY);
		zieleAufgedeckt++;
		zielX = jetztX;
		zielY = jetztY;

	}

	/**
	 * Art und Weise, wie der Bot läuft
	 *
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @param input
	 */
	private static void turn(Scanner input) {

		double epsilon = 1;
		double randomNumber;

		while (input.hasNext()) {

			// Rundeninformationen auslesen
			lastActionsResult = input.nextLine();
			// TODO lastactionresult abprüfen

			epsilon = 1 - zieleAufgedeckt / (double) kartenGroesse;

			randomNumber = Math.random();

			aktualsiereStatusMeldungen(input);

			StringBuilder sb = new StringBuilder();
			sb.append("LastActionResult: " + lastActionsResult + "\nZiele aufgedeckt: " + zieleAufgedeckt + " von "
					+ kartenGroesse + " Epsilon:" + epsilon + "\nPosition X: " + jetztX + " Y: " + jetztY
					+ " Feldstatus " + karte[jetztX][jetztY].getFeldStatus() + " Explorationszahl: "
					+ karte[jetztX][jetztY].getExplorationsZahl() + " Exploitationszahl: "
					+ karte[jetztX][jetztY].getExploitationsZahl() + "\nFloorCount: " + floorCount
					+ "\nFormulare gefunden: " + countForms + " Von: " + formCount + " Kombinator.meinForm: "
					+ kombinator.getMeinForm() + " Kombinator.meinFinish: " + kombinator.getMeinFinish()
					+ "\nSchrittzaehler: " + schrittZaehler + " Hab mich bewegt: " + habeMichBewegt
					+ "\nRandom Number: " + randomNumber + "\nExploitation ja? " + exploitation
					+ "\nExplorationszahl Norden: "
					+ karte[jetztX][((jetztY - 1) + sizeY) % sizeY].getExplorationsZahl() + "\nExplorationszahl Osten: "
					+ karte[((jetztX + 1) + sizeX) % sizeX][jetztY].getExplorationsZahl() + "\nExplorationszahl Süden: "
					+ karte[jetztX][((jetztY + 1) + sizeY) % sizeY].getExplorationsZahl()
					+ "\nExplorationszahl Westen: "
					+ karte[((jetztX - 1) + sizeX) % sizeX][jetztY].getExplorationsZahl() + "\nExplorationszahl HIER: "
					+ karte[jetztX][jetztY].getExplorationsZahl() + "\nExploitationszahl HIER: "
					+ karte[jetztX][jetztY].getExploitationsZahl() + "\nExploitationszahl Norden: "
					+ karte[jetztX][((jetztY - 1) + sizeY) % sizeY].getExploitationsZahl()
					+ "\nExploitationszahl Osten: "
					+ karte[((jetztX + 1) + sizeX) % sizeX][jetztY].getExploitationsZahl()
					+ "\nExploitationszahl Süden: "
					+ karte[jetztX][((jetztY + 1) + sizeY) % sizeY].getExploitationsZahl()
					+ "\nExploitationszahl Westen: "
					+ karte[((jetztX - 1) + sizeX) % sizeX][jetztY].getExploitationsZahl() + "\nFeldstatus Norden: "
					+ karte[jetztX][((jetztY - 1) + sizeY) % sizeY].getFeldStatus() + "\nFeldstatus Osten: "
					+ karte[((jetztX + 1) + sizeX) % sizeX][jetztY].getFeldStatus() + "\nFeldstatus Süden: "
					+ karte[jetztX][((jetztY + 1) + sizeY) % sizeY].getFeldStatus() + "\nFeldstatus Westen: "
					+ karte[((jetztX - 1) + sizeX) % sizeX][jetztY].getFeldStatus() + "\nKartenlänge: " + karte.length
					+ " Liste des kürzesten Wegs: " + liste.toString());

			// Debug Information ausgeben (optional möglich)
			System.err.println(sb.toString());

			switch (lastActionsResult) {
			case "NOK BLOCKED":

			case "NOK NOTYOURS":

			case "NOK EMPTY":

			case "NOK WRONGORDER":

			case "NOK TAKING":

			case "NOK TALKING":

			case "NOK NOTSUPPORTED":
				// Alle Fälle werden eig abgehandelt, aber zur Sicherheit Koordinaten überprüfen
				jetztX = vorherX;
				jetztY = vorherY;

			default:

				// Wo stehe ich?
				if (steheAufEinemInteressantenFeld()) {
					habeMichBewegt = false;
				} else
				// Habe ich alle Formulare und kenne ich den Weg zum Sachbearbeiter?

				if (countForms == formCount && sachbearbeiterGefunden) {
					direktenWegGehen();
				} else if (exploitation) {
					// liste = exploitation(liste, zielX, zielY);
				} else if (randomNumber > epsilon) {

					// liste = exploitation(liste, zielX, zielY);
					exploration();

				} else {
					// Zufälliges Explorieren
					exploration();
				}

			}
			ausgabeValidieren();
			System.out.println(ausgabe);
		}
	}

	/**
	 * Mitschreiben der gewählten Richtung.
	 *
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * 
	 */
	private static void ausgabeValidieren() {
		vorherX = jetztX;
		vorherY = jetztY;
		switch (ausgabe) {
		case "go north":
			jetztY = ((jetztY - 1) + sizeY) % sizeY;
			break;
		case "go east":
			jetztX = ((jetztX + 1) + sizeX) % sizeX;
			break;
		case "go south":
			jetztY = ((jetztY + 1) + sizeY) % sizeY;
			break;
		case "go west":
			jetztX = ((jetztX - 1) + sizeX) % sizeX;
			break;
		default:
		}

	}

	/**
	 * Direkter Weg zum Sachbearbeiter wenn alle Formulare eingesammelt.
	 * 
	 * Falls der Bot alle Formulare hat und den Weg zum Sachbearbeiter kennt, geht
	 * er den direkten Weg
	 *
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 */
	private static void direktenWegGehen() {

		int exploitationszahlHier = karte[jetztX][jetztY].getExploitationsZahl();
		// Norden
		if (karte[jetztX][jetztY - 1].getExploitationsZahl() == (exploitationszahlHier - 1)) {
			ausgabe = "go north";
		} else

		// Osten
		if (karte[jetztX + 1][jetztY].getExploitationsZahl() == (exploitationszahlHier - 1)) {
			ausgabe = "go east";
		} else

		// Sueden
		if (karte[jetztX][jetztY + 1].getExploitationsZahl() == (exploitationszahlHier - 1)) {
			ausgabe = "go south";
		}

		// Westen
		else
			ausgabe = "go west";

	}

	/**
	 * Auswertung des Feldstatus vom aktuellen Standort.
	 *
	 * Herausfinden, auf welche Art von Feld der Bot sich befindet z.B. ob dort sein
	 * eigenes Formular liegt. Sheets werden bewusst gekickt um ggf verdeckte Forms
	 * frei zu legen.
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 *
	 * @return boolean true, falls man auf einem besonderen Feld steht
	 */
	private static boolean steheAufEinemInteressantenFeld() {

		// Befinde ich mich mit einem anderen Bot auf einem Feld?
		// Bewusste Entscheidung anderen Bots nicht aus dem weg zu gehen, wissen wir
		// nicht wohin er geht und ggf. geht er uns aus dem Weg.
		char ausrufezeichen = currentCellStatus.charAt(currentCellStatus.length() - 1);
		if (ausrufezeichen == '!' && !lastActionsResult.equals("NOK TALKING")) {
			ausgabe = "position";
			return true;
		} else
		// Sheet kicken, bewusste Entscheidung nur bekommene Sheets abzulegen, daher
		// werden Sheets gekick um Form darunter freizulegen
		if (currentCellStatus.contains("SHEET") && !karte[jetztX][jetztY].getFeldStatus().contains("EIGENESSHEET")) {

			if (karte[jetztX][((jetztY - 1) + sizeY) % sizeY].getFeldStatus().contains("FLOOR")) {
				ausgabe = "kick north";
			} else if (karte[((jetztX + 1) + sizeX) % sizeX][jetztY].getFeldStatus().contains("FLOOR")) {
				ausgabe = "kick east";
			} else if (karte[jetztX][((jetztY + 1) + sizeY) % sizeY].getFeldStatus().contains("FLOOR")) {
				ausgabe = "kick south";
			} else if (karte[((jetztX - 1) + sizeX) % sizeX][jetztY].getFeldStatus().contains("FLOOR")) {
				ausgabe = "kick west";
			}
			return true;
		} else

		// Stehe ich auf einem Finish? (IMPLIZIERT, dass ich alle Formulare habe)
		if (currentCellStatus.contains(meinFinish + formCount))

		{

			sachbearbeiterGefunden = true;

			if (countForms == formCount) {
				ausgabe = "finish";

				return true;
			}

		}
		// Stehe ich auf einem Formular? (IMPLIZIERT, dass ich dieses Formular als
		// nächstes brauche)
		else if (currentCellStatus.contains(meinForm + (countForms + 1)))

		{
			ausgabe = "take";
			karte[jetztX][jetztY].setFeldStatus("FLOOR", playerId);
			countForms++;
			kombinator.setMeinForm(meinForm + (countForms + 1));

			return true;

		}
		// lege Sheets auf die Formulare meiner Gegner
		// bewusste Entscheidung nur bekommene Sheets abzulegen, um dadurch weniger
		// Zuege zu brauchen
		else if (currentCellStatus.contains("FORM") && anzahlSheets > 0 && !currentCellStatus.contains(meinForm))

		{

			ausgabe = "put";
			karte[jetztX][jetztY].setFeldStatus("EIGENESSHEET", playerId);
			anzahlSheets--;

			return true;

		}
		return false;
	}

	/**
	 * Statusmeldungen, der Felder um den Bot, aktualisieren.
	 *
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @param input
	 */
	private static void aktualsiereStatusMeldungen(Scanner input) {

		cellStati.clear();
		tempCellStati.clear();
		floorCount = 0;
		String northCellStatus;
		String eastCellStatus;
		String southCellStatus;
		String westCellStatus;
		currentCellStatus = input.nextLine();

		// Status aktualisieren
		// Existiert er oder hat sich Position geändert?
		// Norden
		northCellStatus = input.nextLine();
		if (karte[jetztX][((jetztY - 1) + sizeY) % sizeY].getFeldStatus().equals("INIT")) {
			karte[jetztX][((jetztY - 1) + sizeY) % sizeY].setFeldStatus(northCellStatus, playerId);
			zieleAufgedeckt++;

		} else if (!karte[jetztX][((jetztY - 1) + sizeY) % sizeY].getFeldStatus().equals(northCellStatus)
				&& !karte[jetztX][((jetztY - 1) + sizeY) % sizeY].getFeldStatus().equals("EIGENESSHEET")) {
			karte[jetztX][((jetztY - 1) + sizeY) % sizeY].setFeldStatus(northCellStatus, playerId);
		}

		// Osten
		eastCellStatus = input.nextLine();
		if (karte[((jetztX + 1) + sizeX) % sizeX][jetztY].getFeldStatus().equals("INIT")) {
			karte[((jetztX + 1) + sizeX) % sizeX][jetztY].setFeldStatus(eastCellStatus, playerId);
			zieleAufgedeckt++;

		} else if (!karte[((jetztX + 1) + sizeX) % sizeX][jetztY].getFeldStatus().equals(eastCellStatus)
				&& !karte[((jetztX + 1) + sizeX) % sizeX][jetztY].getFeldStatus().equals("EIGENESSHEET")) {
			karte[((jetztX + 1) + sizeX) % sizeX][jetztY].setFeldStatus(eastCellStatus, playerId);
		}

		southCellStatus = input.nextLine();
		if (karte[jetztX][((jetztY + 1) + sizeY) % sizeY].getFeldStatus().equals("INIT")) {
			karte[jetztX][((jetztY + 1) + sizeY) % sizeY].setFeldStatus(southCellStatus, playerId);
			zieleAufgedeckt++;
		} else if (!karte[jetztX][((jetztY + 1) + sizeY) % sizeY].getFeldStatus().equals(southCellStatus)
				&& !karte[jetztX][((jetztY + 1) + sizeY) % sizeY].getFeldStatus().equals("EIGENESSHEET")) {
			karte[jetztX][((jetztY + 1) + sizeY) % sizeY].setFeldStatus(southCellStatus, playerId);
		}

		westCellStatus = input.nextLine();
		if (karte[((jetztX - 1) + sizeX) % sizeX][jetztY].getFeldStatus().equals("INIT")) {
			karte[((jetztX - 1) + sizeX) % sizeX][jetztY].setFeldStatus(westCellStatus, playerId);
			zieleAufgedeckt++;

		} else if (!karte[((jetztX - 1) + sizeX) % sizeX][jetztY].getFeldStatus().equals(westCellStatus)
				&& !karte[((jetztX - 1) + sizeX) % sizeX][jetztY].getFeldStatus().equals("EIGENESSHEET")) {
			karte[((jetztX - 1) + sizeX) % sizeX][jetztY].setFeldStatus(westCellStatus, playerId);
		}

		cellStati.add(northCellStatus + " norden");
		cellStati.add(eastCellStatus + " osten");
		cellStati.add(southCellStatus + " sueden");
		cellStati.add(westCellStatus + " westen");

		// Bewegungsfelder zaehlen
		for (String currentCellStatus : cellStati) {
			if (!currentCellStatus.contains("WALL")) {

				tempCellStati.add(currentCellStatus);
				floorCount++;
				// Der Sachbearbeiter weiß wie viele Formulare ich suchen muss
				if (currentCellStatus.contains("FINISH")) {
					String[] partsF = currentCellStatus.split(" ");

					String lastDigitF = partsF[2];
					int lastF = Integer.parseInt(lastDigitF);

					formCount = lastF;
					kombinator.setMeinFinish(meinFinish + formCount);
				}
			}
		}

		// Explorationszahl aktualisieren
		karte[jetztX][jetztY].setExplorationsZahl(floorCount);

		// Es fehlt nur noch ein Formular --> Ich merke mir den Weg zum Sachbearbeiter
		if (sachbearbeiterGefunden && habeMichBewegt && formCount == countForms + 1) {
			schrittZaehler++;
			karte[jetztX][jetztY].setExploitationsZahl(schrittZaehler);
		}

	}

	/**
	 * Exploration des unbekannten Spielfelds.
	 *
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 */
	private static void exploration() {

		switch (floorCount) {
		case 1:
			// Ich befinde mich in einer Sackgasse und drehe um
			ausgabe = kombinator.moeglichkeitenBerechnen(tempCellStati.get(0));
			break;
		case 2:
			// Es gibt zwei mögliche Wege die ich gehen kann
			ausgabe = kombinator.moeglichkeitenBerechnen(tempCellStati.get(0), tempCellStati.get(1), karte, jetztX,
					jetztY);
			break;
		case 3:
			// Ich befinde mich auf einer Kreuzung mit drei Optionen
			ausgabe = kombinator.moeglichkeitenBerechnen(tempCellStati.get(0), tempCellStati.get(1),
					tempCellStati.get(2), karte, jetztX, jetztY);
			break;
		default:
			// Ich befinde mich auf einer Kreuzung mit vier Optionen
			ausgabe = kombinator.moeglichkeitenBerechnen(tempCellStati.get(0), tempCellStati.get(1),
					tempCellStati.get(2), tempCellStati.get(3), karte, jetztX, jetztY);
		}

		habeMichBewegt = true;

	}

	/**
	 * Exploitation des bereits etwas besser bekannten Spielfelds.
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @param zielX X-Variable des Zielfelds
	 * @param zielY Y-Variable des Zielfelds
	 * @return List<Vertex> es wird eine Liste mit den Objekten des kürzesten Weg
	 *         zurückgegeben
	 */
	private static List<Vertex> exploitation(List<Vertex> liste, int zielX, int zielY) {

		// Existiert bereits eine Weg-Liste der ich folgen muss?
		if (!liste.isEmpty() && liste != null) {
			if (liste.get(listZaehler).getName().equals((jetztX + 1) + "" + jetztY)) {
				ausgabe = "go east";
			} else if (liste.get(listZaehler).getName().equals((jetztX - 1) + "" + jetztY)) {
				ausgabe = "go west";
			} else if (liste.get(listZaehler).getName().equals(jetztX + "" + (jetztY + 1))) {
				ausgabe = "go south";
			} else if (liste.get(listZaehler).getName().equals(jetztX + "" + (jetztY - 1))) {
				ausgabe = "go north";
			}
			listZaehler++;
			// liste.remove(0);
			if (liste.isEmpty()) {
				exploitation = false;
				listZaehler = 0;
				return liste;
			}

		} else {

			// Andernfalls wird eine Liste erstellt. Der kürzeste Weg wird über den Dikstra
			// Algorithmus berechnet
			String startString = jetztX + "" + jetztY;
			String zielString = zielX + "" + zielY;

			Vertex[] alleKnoten = new Vertex[sizeX * sizeY];
			Vertex startknoten = null;
			Vertex zielknoten = null;
			Vertex gesuchterKnoten = null;
			Vertex gesuchterKnoten2 = null;

			int anzahlknoten = 0;
			// Knoten bestimmen
			for (int i = 0; i < sizeX; i++) {
				for (int j = 0; j < sizeY; j++) {

					if (jetztX == i && jetztY == j) {

						alleKnoten[anzahlknoten] = new Vertex(i + "" + j);
						startknoten = alleKnoten[anzahlknoten];

					} else if (zielX == i && zielY == j) {
						alleKnoten[anzahlknoten] = new Vertex(i + "" + j);
						zielknoten = alleKnoten[anzahlknoten];

					} else {
						alleKnoten[anzahlknoten] = new Vertex(i + "" + j);

					}
					anzahlknoten++;

				}
			}

			// Nachbarn bestimmen
			anzahlknoten = 0;
			for (int i = 0; i < sizeX; i++) {
				for (int j = 0; j < sizeY; j++) {

					for (Vertex knoten : alleKnoten) {

						if (i < sizeX && knoten.getName().equals((i + 1) + "" + j)) {
							gesuchterKnoten = knoten;
						}
						if (j < sizeY && knoten.getName().equals(i + "" + (j + 1))) {
							gesuchterKnoten2 = knoten;
						}

					}

					// Nachbarn hinzufügen
					if (gesuchterKnoten != null) {
						alleKnoten[anzahlknoten].addNeighbour(new Edge(1, alleKnoten[anzahlknoten], gesuchterKnoten));

					}
					if (gesuchterKnoten2 != null) {
						alleKnoten[anzahlknoten].addNeighbour(new Edge(1, alleKnoten[anzahlknoten], gesuchterKnoten2));

					}

					gesuchterKnoten = null;
					gesuchterKnoten2 = null;
					anzahlknoten++;

				}
			}

			// Kürzesten Weg berechnen
			DijkstraShortestPath shortestPath = new DijkstraShortestPath();
			for (Vertex knoten : alleKnoten) {
				if (knoten.getName().equals(startString)) {
					startknoten = knoten;
				}
				if (knoten.getName().equals(zielString)) {
					zielknoten = knoten;
				}
			}
			shortestPath.computeShortestPaths(startknoten);

			List<Vertex> tmp = shortestPath.getShortestPathTo(zielknoten);

			for (Vertex vertex : tmp) {
				liste.add(vertex);
			}

			// TODO
			// In selbstgeschriebener Testklasse (Testumgebung) funktioniert die Umsetzung,
			// leider hier nicht
			if (!liste.isEmpty() && liste != null) {
				// liste.remove(0);

				if (liste.get(listZaehler).getName().equals((jetztX + 1) + "" + jetztY)) {
					ausgabe = "go east";
				} else if (liste.get(listZaehler).getName().equals((jetztX - 1) + "" + jetztY)) {
					ausgabe = "go west";
				} else if (liste.get(listZaehler).getName().equals(jetztX + "" + (jetztY + 1))) {
					ausgabe = "go south";
				} else if (liste.get(listZaehler).getName().equals(jetztX + "" + (jetztY - 1))) {
					ausgabe = "go north";
				}
				listZaehler++;
				// liste.remove(0);

				if (liste.isEmpty()) {
					exploitation = false;
					listZaehler = 0;
					return liste;
				}

			} else {
				// TODO Fehler abfangen
				// exploitation = false;
				// exploration();
				// return liste;
			}

		}

		exploitation = true;
		return liste;
	}

}
