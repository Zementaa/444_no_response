package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class EpsilonGreedyBot {

	private static Queue<Explorer> queue = new LinkedList<>();

	private static Kombinatorik kombinator;
	private static double epsilon = 1;
	private static double randomNumber;
	private static int playerId;
	private static int startX;
	private static int startY;
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
		kombinator = new Kombinatorik(playerId);
		zieleAufgedeckt++;

		queue.offer(karte[startX][startY]);

	}

	private static void turn(Scanner input) {

		while (input.hasNext()) {

			// Rundeninformationen auslesen
			lastActionsResult = input.nextLine();
			// TODO lastactionresult abprüfen
			// TODO Bot bleibt hängen wenn er auf anderen trifft.
			// TODO wenn er festhängt ab da wo Explorationszahl kleiner als davor ist eine
			// Gasse
			// abfragen des Status
//			switch (lastActionsResult) {
//			case "NOK BLOCKED":
//				continue; // hier muss er in andere Richtung weiterlaufen, damit er nicht wiederholt was
//							// er getan hat
//			case "NOK NOTYOURS":
//				continue; // hier muss er eigentlich weiterlaufen, damit er nicht wiederholt was er getan
//							// hat
//			case "NOK EMPTY":
//				continue; // hier muss er eigentlich weiterlaufen, damit er nicht wiederholt was er getan
//							// hat
//			case "NOK WRONGORDER":
//				continue; // hier muss er eigentlich weiterlaufen, damit er nicht wiederholt was er getan
//							// hat
//			case "NOK TAKING":
//				continue;
//			case "NOK TALKING":
//				continue;
//			case "NOK NOTSUPPORTED":
//				continue; // hier muss er eigentlich weiter laufen, damit er nicht wiederholt was er getan
//							// hat
//			default:

			aktualsiereStatusMeldungen(input);

			epsilon = 1 - zieleAufgedeckt / (double) kartenGroesse;

			randomNumber = Math.random();

			// Debug Information ausgeben (optional möglich)
			System.err.println("LastActionResult: " + lastActionsResult + "\nZiele aufgedeckt: " + zieleAufgedeckt
					+ " von " + kartenGroesse + " Epsilon:" + epsilon + "\nPosition X: " + startX + " Y: " + startY
					+ " Feldstatus " + karte[startX][startY].getFeldStatus() + " Explorationszahl: "
					+ karte[startX][startY].getExplorationsZahl() + " Exploitationszahl: "
					+ karte[startX][startY].getExploitationsZahl() + "\nFloorCount: " + floorCount
					+ "\nFormulare gefunden: " + countForms + " Von: " + formCount + " Kombinator.meinForm: "
					+ kombinator.getMeinForm() + " Kombinator.meinFinish: " + kombinator.getMeinFinish()
					+ "\nSchrittzaehler: " + schrittZaehler + " Hab mich bewegt: " + habeMichBewegt
					+ "\nExplorationszahl Norden: " + karte[startX][startY - 1].getExplorationsZahl()
					+ "\nExplorationszahl Osten: " + karte[startX + 1][startY].getExplorationsZahl()
					+ "\nExplorationszahl Süden: " + karte[startX][startY + 1].getExplorationsZahl()
					+ "\nExplorationszahl Westen: " + karte[startX - 1][startY].getExplorationsZahl()
					+ "\nExplorationszahl HIER: " + karte[startX][startY].getExplorationsZahl()
					+ "\nExploitationszahl HIER: " + karte[startX][startY].getExploitationsZahl()
					+ "\nExploitationszahl Norden: " + karte[startX][startY - 1].getExploitationsZahl()
					+ "\nExploitationszahl Osten: " + karte[startX + 1][startY].getExploitationsZahl()
					+ "\nExploitationszahl Süden: " + karte[startX][startY + 1].getExploitationsZahl()
					+ "\nExploitationszahl Westen: " + karte[startX - 1][startY].getExploitationsZahl()
					+ "\nFeldstatus Norden: " + karte[startX][startY - 1].getFeldStatus() + "\nFeldstatus Osten: "
					+ karte[startX + 1][startY].getFeldStatus() + "\nFeldstatus Süden: "
					+ karte[startX][startY + 1].getFeldStatus() + "\nFeldstatus Westen: "
					+ karte[startX - 1][startY].getFeldStatus());

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

				// if nächstes Ziel bekannt

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

	private static void ausgabeValidieren() {

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
		// nächstes brauche)
		else if (currentCellStatus.contains(meinForm + (countForms + 1)))

		{
			ausgabe = "take";
			karte[startX][startY].setFeldStatus("FLOOR", playerId);
			countForms++;
			kombinator.setMeinForm(meinForm + (countForms + 1));

			return true;

		}
		// Ich stehe auf einem leeren Feld mit Gegner und muss eine Runde aussetzen
//		else if (currentCellStatus.equals("FLOOR !")) {
//			ausgabe = "position";
//			return true;
//		} 
		else if (currentCellStatus.contains("FORM") && anzahlSheets > 0)

		{

			ausgabe = "put";
			anzahlSheets--;

			return true;

		}
		return false;
	}

	private static void aktualsiereStatusMeldungen(Scanner input) {

		// TODO prüfe Warteschlange auf doppelte Werte
		// Mitte auf Explorationszahl 1 setzen (der letzten beiden Elemente in der
		// Warteschlange)

		cellStati.clear();
		tempCellStati.clear();
		floorCount = 0;

		currentCellStatus = input.nextLine();

		// Status aktualisieren
		// Existiert er oder hat sich Position geändert?

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
