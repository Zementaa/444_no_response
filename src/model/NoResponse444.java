<<<<<<< HEAD
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Zementa
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

		kombinator = new Kombinatorik(playerId, sizeX, sizeY);
		zieleAufgedeckt++;
		zielX = jetztX;
		zielY = jetztY;

	}

	/**
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
					liste = exploitation(liste, zielX, zielY);
				} else if (randomNumber > epsilon) {

					liste = exploitation(liste, zielX, zielY);

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
	 * 
	 * @return
	 */
	private static boolean steheAufEinemInteressantenFeld() {

		// Befinde ich mich mit einem anderen Bot auf einem Feld?
		char ausrufezeichen = currentCellStatus.charAt(currentCellStatus.length() - 1);
		if (ausrufezeichen == '!' && !lastActionsResult.equals("NOK TALKING")) {
			ausgabe = "position";
			return true;
		} else
		// Sheet kicken
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
	 * 
	 * @param zielX
	 * @param zielY
	 * @return
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
=======
package model;

	import java.util.ArrayList;
	import java.util.LinkedList;
	import java.util.List;
	import java.util.Queue;
	import java.util.Scanner;

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * Klasse des Bots namens NO_Response
	 * und Deklarierung sowie Initialisierung der Attribute
	 *
	 */
public class NO_Response {

	private static Queue<Explorer> queue = new LinkedList<>();

	private static Kombinatorik kombinator;
	private static double epsilon = 1;
	private static double randomNumber;
	private static int playerId;
	private static int startX;
	private static int startY;
	private static int vorherX;
	private static int vorherY;
	private static int zielX;
	private static int zielY;
	private static int kartenGroesse;
	private static String meinFinish;
	private static String meinForm;
	private static String lastActionsResult;
	private static String currentCellStatus;
	private static String northCellStatus;
	private static String eastCellStatus;
	private static String southCellStatus;
	private static String westCellStatus;
	private static String ausgabe = "";
	private static Explorer[][] karte;
	private static int zieleAufgedeckt = 0;
	private static int sizeX;
	private static int sizeY;
	private static List<String> cellStati = new ArrayList<>();
	private static List<String> tempCellStati = new ArrayList<>();
	private static int floorCount;
	private static int formCount = -1;
	private static int countForms = 0;
	private static boolean habeMichBewegt = false;
	private static boolean exploitation = false;
	private static int anzahlSheets = 0;

	private static boolean sachbearbeiterGefunden = false;
	private static int schrittZaehler = 0;

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * Initialisierung der Eingabe-Attribute
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
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * Eingabe der Informationen zum Labyrinth und dem eigenen Spieler (Bot)
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
		startX = input.nextInt(); // X-Koordinate der Startposition dieses Player
		startY = input.nextInt(); // Y-Koordinate der Startposition dieses Players
		if (level == 5) {
			anzahlSheets = input.nextInt();
		}

		input.nextLine(); // Beenden der zweiten Zeile

		kartenGroesse = sizeX * sizeY;

		meinFinish = "FINISH " + playerId + " ";
		meinForm = "FORM " + playerId + " ";
		karte = new Explorer[sizeX][sizeY];
		karte[startX][startY] = new Explorer("FLOOR", playerId, countForms + 1);
		kombinator = new Kombinatorik(playerId, sizeX, sizeY);
		zieleAufgedeckt++;

		queue.offer(karte[startX][startY]);

	}

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * Art und Weise, wie der Bot lÃ¤uft
	 * Bedingungen zur Entscheidung, wie er lÃ¤uft mittels switch
	 * 
	 * @param input
	 */
	private static void turn(Scanner input) {

		while (input.hasNext()) {

			// Rundeninformationen auslesen
			lastActionsResult = input.nextLine();
			// TODO lastactionresult abprÃ¼fen
			// TODO Bot bleibt hÃ¤ngen wenn er auf anderen trifft! Koordinaten nicht
			// aktualisieren
			// TODO wenn er festhÃ¤ngt ab da wo Explorationszahl kleiner als davor ist eine
			// Gasse
			// abfragen des Status

			epsilon = 1 - zieleAufgedeckt / (double) kartenGroesse;

			randomNumber = Math.random();

			aktualsiereStatusMeldungen(input);

			StringBuilder sb = new StringBuilder();
			sb.append("LastActionResult: " + lastActionsResult + "\nZiele aufgedeckt: " + zieleAufgedeckt + " von "
					+ kartenGroesse + " Epsilon:" + epsilon + "\nPosition X: " + startX + " Y: " + startY
					+ " Feldstatus " + karte[startX][startY].getFeldStatus() + " Explorationszahl: "
					+ karte[startX][startY].getExplorationsZahl() + " Exploitationszahl: "
					+ karte[startX][startY].getExploitationsZahl() + "\nFloorCount: " + floorCount
					+ "\nFormulare gefunden: " + countForms + " Von: " + formCount + " Kombinator.meinForm: "
					+ kombinator.getMeinForm() + " Kombinator.meinFinish: " + kombinator.getMeinFinish()
					+ "\nSchrittzaehler: " + schrittZaehler + " Hab mich bewegt: " + habeMichBewegt
					+ "\nExplorationszahl Norden: "
					+ karte[startX][((startY - 1) + sizeY) % sizeY].getExplorationsZahl() + "\nExplorationszahl Osten: "
					+ karte[((startX + 1) + sizeX) % sizeX][startY].getExplorationsZahl() + "\nExplorationszahl SÃ¼den: "
					+ karte[startX][((startY + 1) + sizeY) % sizeY].getExplorationsZahl()
					+ "\nExplorationszahl Westen: "
					+ karte[((startX - 1) + sizeX) % sizeX][startY].getExplorationsZahl() + "\nExplorationszahl HIER: "
					+ karte[startX][startY].getExplorationsZahl() + "\nExploitationszahl HIER: "
					+ karte[startX][startY].getExploitationsZahl() + "\nExploitationszahl Norden: "
					+ karte[startX][((startY - 1) + sizeY) % sizeY].getExploitationsZahl()
					+ "\nExploitationszahl Osten: "
					+ karte[((startX + 1) + sizeX) % sizeX][startY].getExploitationsZahl()
					+ "\nExploitationszahl SÃ¼den: "
					+ karte[startX][((startY + 1) + sizeY) % sizeY].getExploitationsZahl()
					+ "\nExploitationszahl Westen: "
					+ karte[((startX - 1) + sizeX) % sizeX][startY].getExploitationsZahl() + "\nFeldstatus Norden: "
					+ karte[startX][((startY - 1) + sizeY) % sizeY].getFeldStatus() + "\nFeldstatus Osten: "
					+ karte[((startX + 1) + sizeX) % sizeX][startY].getFeldStatus() + "\nFeldstatus SÃ¼den: "
					+ karte[startX][((startY + 1) + sizeY) % sizeY].getFeldStatus() + "\nFeldstatus Westen: "
					+ karte[((startX - 1) + sizeX) % sizeX][startY].getFeldStatus());

			// Debug Information ausgeben (optional mÃ¶glich)
			System.err.println(sb.toString());

			switch (lastActionsResult) {
//				case "NOK BLOCKED":
//					continue; // hier muss er in andere Richtung weiterlaufen, damit er nicht wiederholt was
//								// er getan hat
//				case "NOK NOTYOURS":
//					continue; // hier muss er eigentlich weiterlaufen, damit er nicht wiederholt was er getan
//								// hat
//				case "NOK EMPTY":
//					continue; // hier muss er eigentlich weiterlaufen, damit er nicht wiederholt was er getan
//								// hat
//				case "NOK WRONGORDER":
//					continue; // hier muss er eigentlich weiterlaufen, damit er nicht wiederholt was er getan
//								// hat
//				case "NOK TAKING":
//					System.out.println("position");
//					continue;
			case "NOK TALKING":
				startX = vorherX;
				startY = vorherY;
				System.out.println("position");
				continue;
//				case "NOK NOTSUPPORTED":
//					System.out.println("position");
//					continue; // hier muss er eigentlich weiter laufen, damit er nicht wiederholt was er getan
//								// hat
			default:

				// Wo stehe ich?
				if (steheAufEinemInteressantenFeld()) {
					habeMichBewegt = false;
				} else
				// Habe ich alle Formulare und kenne ich den Weg zum Sachbearbeiter?

				if (countForms == formCount && sachbearbeiterGefunden) {
					direktenWegGehen();
				}

				else if (randomNumber > epsilon) {
					// exploitation();

					// if nÃ¤chstes Ziel bekannt

					// else

					exploration();

				} else {
					int zaehlen = 0;
					for (Explorer explorer : queue) {
						if (explorer.equals(queue.peek())) {
							zaehlen++;
						}
					}
					if (zaehlen > 1) {
						// exploitation();
					} else {
					}

					exploration();
				}

				ausgabeValidieren();
				System.out.println(ausgabe);
			}
		}
	}

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * Ausgabe der ausgefÃ¼hrten Aktion
	 */
	private static void ausgabeValidieren() {
		vorherX = startX;
		vorherY = startY;
		switch (ausgabe) {
		case "go north":
			startY = ((startY - 1) + sizeY) % sizeY;
			break;
		case "go east":
			startX = ((startX + 1) + sizeX) % sizeX;
			break;
		case "go south":
			startY = ((startY + 1) + sizeY) % sizeY;
			break;
		case "go west":
			startX = ((startX - 1) + sizeX) % sizeX;
			break;
		default:
		}
		queue.offer(karte[startX][startY]);

		if (queue.size() > 5) {
			queue.poll();
		}

	}

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * 
	 */
	private static void direktenWegGehen() {

		int exploitationszahlHier = karte[startX][startY].getExploitationsZahl();
		// Norden
		if (karte[startX][startY - 1].getExploitationsZahl() == (exploitationszahlHier - 1)) {
			ausgabe = "go north";
		} else

		// Osten
		if (karte[startX + 1][startY].getExploitationsZahl() == (exploitationszahlHier - 1)) {
			ausgabe = "go east";
		} else

		// Sueden
		if (karte[startX][startY + 1].getExploitationsZahl() == (exploitationszahlHier - 1)) {
			ausgabe = "go south";
		}

		// Westen
		else
			ausgabe = "go west";

	}

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * Auswertung bzw. herausfinden, auf welche Art von Feld der Bot sich befindet
	 * z.B. ob dort sein eigenes Formular liegt
	 * @return
	 */
	private static boolean steheAufEinemInteressantenFeld() {

		// TODO sheets kicken Richtung dass Welt unendlich ist %
		if (karte[startX][startY].getFeldStatus().contains(meinForm) && currentCellStatus.contains("SHEET")) {
			if (karte[startX][startY - 1].getFeldStatus().contains("FLOOR")) {
				ausgabe = "kick north";
			}
			if (karte[startX + 1][startY].getFeldStatus().contains("FLOOR")) {
				ausgabe = "kick east";
			}
			if (karte[startX][startY + 1].getFeldStatus().contains("FLOOR")) {
				ausgabe = "kick south";
			}
			if (karte[startX - 1][startY].getFeldStatus().contains("FLOOR")) {
				ausgabe = "kick west";
			}
			return true;
		} else

		// Stehe ich auf einem Finish? (IMPLIZIERT, dass ich alle Formulare habe)
		if (currentCellStatus.contains(meinFinish + formCount)) {

			sachbearbeiterGefunden = true;

			if (countForms == formCount) {
				ausgabe = "finish";

				return true;
			}

		}
		// Stehe ich auf einem Formular? (IMPLIZIERT, dass ich dieses Formular als
		// nÃ¤chstes brauche)
		else if (currentCellStatus.contains(meinForm + (countForms + 1)))

		{
			ausgabe = "take";
			karte[startX][startY].setFeldStatus("FLOOR", playerId);
			countForms++;
			kombinator.setMeinForm(meinForm + (countForms + 1));

			return true;

		}
		// Ich stehe auf einem leeren Feld mit Gegner und muss eine Runde aussetzen
//			else if (currentCellStatus.equals("FLOOR !")) {
//				ausgabe = "position";
//				return true;
//			} 
		else if (currentCellStatus.contains("FORM") && anzahlSheets > 0)

		{

			ausgabe = "put";
			anzahlSheets--;

			return true;

		}
		return false;
	}

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * 
	 * @param input
	 */
	private static void aktualsiereStatusMeldungen(Scanner input) {

		// TODO prÃ¼fe Warteschlange auf doppelte Werte
		// Mitte auf Explorationszahl 1 setzen (der letzten beiden Elemente in der
		// Warteschlange)

		cellStati.clear();
		tempCellStati.clear();
		floorCount = 0;

		currentCellStatus = input.nextLine();

		// Status aktualisieren
		// Existiert er oder hat sich Position geÃ¤ndert?

		// Norden
		northCellStatus = input.nextLine();
		if (karte[startX][((startY - 1) + sizeY) % sizeY] == null) {
			karte[startX][((startY - 1) + sizeY) % sizeY] = new Explorer(northCellStatus, playerId, countForms + 1);
			zieleAufgedeckt++;

		} else if (!karte[startX][((startY - 1) + sizeY) % sizeY].getFeldStatus().equals(northCellStatus)) {
			karte[startX][((startY - 1) + sizeY) % sizeY].setFeldStatus(northCellStatus, playerId);
		}

		// Osten
		eastCellStatus = input.nextLine();
		if (karte[((startX + 1) + sizeX) % sizeX][startY] == null) {
			karte[((startX + 1) + sizeX) % sizeX][startY] = new Explorer(eastCellStatus, playerId, countForms + 1);
			zieleAufgedeckt++;

		} else if (!karte[((startX + 1) + sizeX) % sizeX][startY].getFeldStatus().equals(eastCellStatus)) {
			karte[((startX + 1) + sizeX) % sizeX][startY].setFeldStatus(eastCellStatus, playerId);
		}

		southCellStatus = input.nextLine();
		if (karte[startX][((startY + 1) + sizeY) % sizeY] == null) {
			karte[startX][((startY + 1) + sizeY) % sizeY] = new Explorer(southCellStatus, playerId, countForms + 1);
			zieleAufgedeckt++;
		} else if (!karte[startX][((startY + 1) + sizeY) % sizeY].getFeldStatus().equals(southCellStatus)) {
			karte[startX][((startY + 1) + sizeY) % sizeY].setFeldStatus(southCellStatus, playerId);
		}

		westCellStatus = input.nextLine();
		if (karte[((startX - 1) + sizeX) % sizeX][startY] == null) {
			karte[((startX - 1) + sizeX) % sizeX][startY] = new Explorer(westCellStatus, playerId, countForms + 1);
			zieleAufgedeckt++;

		} else if (!karte[((startX - 1) + sizeX) % sizeX][startY].getFeldStatus().equals(westCellStatus)) {
			karte[((startX - 1) + sizeX) % sizeX][startY].setFeldStatus(westCellStatus, playerId);
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
				if (currentCellStatus.contains("FINISH")) {
					String[] partsF = currentCellStatus.split(" ");

					String lastDigitF = partsF[2];
					int lastF = Integer.parseInt(lastDigitF);

					formCount = lastF;
					kombinator.setMeinFinish(meinFinish + formCount);
				}
			}
		}

		karte[startX][startY].setExplorationsZahl(floorCount);

		// Abgleich wie viele Formulare hab ich also zum Beispiel es fehlt nur noch
		// ein Formular
		if (sachbearbeiterGefunden && habeMichBewegt && formCount == countForms + 1) {
			schrittZaehler++;
			karte[startX][startY].setExploitationsZahl(schrittZaehler);
		}

	}

	/**
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
			ausgabe = kombinator.moeglichkeitenBerechnen(tempCellStati.get(0));
			break;
		case 2:

			ausgabe = kombinator.moeglichkeitenBerechnen(tempCellStati.get(0), tempCellStati.get(1), karte, startX,
					startY);
			break;
		case 3:
			// TODO
			ausgabe = kombinator.moeglichkeitenBerechnen(tempCellStati.get(0), tempCellStati.get(1),
					tempCellStati.get(2), karte, startX, startY);
			break;
		default:
			// TODO
			ausgabe = kombinator.moeglichkeitenBerechnen(tempCellStati.get(0), tempCellStati.get(1),
					tempCellStati.get(2), tempCellStati.get(3), karte, startX, startY);
		}

		habeMichBewegt = true;

	}

	/**
	 * 
	 * @author C.Camier
	 * @author D.Kleemann
	 * @author C.Peters
	 * @author L.Wascher
	 * 
	 * @param binAufDemWeg
	 * @param zielX
	 * @param zielY
	 * @return
	 */
	private static boolean exploitation(boolean binAufDemWeg, int zielX, int zielY) {

		// TODO Ich bin bisher nur ein dummer Bot der keine Strategie hat
		Knoten[] alleKnoten = new Knoten[sizeX * sizeY];
		Knoten startknoten = null;
		Knoten zielknoten = null;
		int anzahlknoten = 0;

		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				if (startX == i && startY == j) {
					alleKnoten[anzahlknoten] = new Knoten(karte[i][j].getFeldStatus(), i, j, true, false);
					startknoten = alleKnoten[anzahlknoten];
				} else if (zielX == i && zielY == j) {
					alleKnoten[anzahlknoten] = new Knoten(karte[i][j].getFeldStatus(), i, j, false, true);
					zielknoten = alleKnoten[anzahlknoten];
				} else
					alleKnoten[anzahlknoten] = new Knoten(karte[i][j].getFeldStatus(), i, j, false, false);

			}
		}

		Algorithmus d = new Algorithmus(startknoten, zielknoten);
		int anzahlZuege = d.getErgebnisReihenfolge().size();

		// Ziel bekannt

		// Ziel unbekannt
		if (!binAufDemWeg) {
			for (int i = 0; i < sizeX; i++) {
				for (int j = 0; j < sizeY; j++) {
					if (karte[i][j] != null && !karte[i][j].getFeldStatus().contains("WALL")) {
						zielX = i;
						zielY = j;
						break;
					}
				}
			}
			binAufDemWeg = true;
		} else {
			// nach Osten gehen

			if (zielX > startX) {
				if (!eastCellStatus.equals("WALL")) {
					ausgabe = "go east";
				}
			} else if (!westCellStatus.equals("WALL")) {
				ausgabe = "go west";
			}

			if (zielY > startY) {
				if (!southCellStatus.equals("WALL")) {
					ausgabe = "go south";
				}

			} else {
				if (!northCellStatus.equals("WALL")) {
					ausgabe = "go north";
				}
			}
		}

		return binAufDemWeg;

	}

}
>>>>>>> 7088e2e16077e99a07c621e325be2ccf5105d3d1
