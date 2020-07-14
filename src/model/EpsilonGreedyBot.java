package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EpsilonGreedyBot {

	private static Kombinatorik kombinator;
	private static double epsilon = 1;
	private static double randomNumber;
	private static int playerId;
	private static int startX;
	private static int startY;
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
//	private static boolean sachbearbeiterGefunden = false;

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
		input.nextLine(); // Beenden der zweiten Zeile

		kartenGroesse = sizeX * sizeY;

		meinFinish = "FINISH " + playerId + " ";
		meinForm = "FORM " + playerId + " ";
		karte = new Explorer[sizeX][sizeY];
		karte[startX][startY] = new Explorer("START", playerId);
		kombinator = new Kombinatorik(playerId);
		zieleAufgedeckt++;

	}

	private static void turn(Scanner input) {

		while (input.hasNext()) {

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
					+ "\nExplorationszahl Norden: " + karte[startX][startY - 1].getExplorationsZahl()
					+ "\nExplorationszahl Osten: " + karte[startX + 1][startY].getExplorationsZahl()
					+ "\nExplorationszahl Süden: " + karte[startX][startY + 1].getExplorationsZahl()
					+ "\nExplorationszahl Westen: " + karte[startX - 1][startY].getExplorationsZahl()
					+ "\nExploitationszahl Norden: " + karte[startX][startY - 1].getExploitationsZahl()
					+ "\nExploitationszahl Osten: " + karte[startX + 1][startY].getExploitationsZahl()
					+ "\nExploitationszahl Süden: " + karte[startX][startY + 1].getExploitationsZahl()
					+ "\nExploitationszahl Westen: " + karte[startX - 1][startY].getExploitationsZahl()
					+ "\nFeldstatus Norden: " + karte[startX][startY - 1].getFeldStatus() + "\nFeldstatus Osten: "
					+ karte[startX + 1][startY].getFeldStatus() + "\nFeldstatus Süden: "
					+ karte[startX][startY + 1].getFeldStatus() + "\nFeldstatus Westen: "
					+ karte[startX - 1][startY].getFeldStatus());

			if (randomNumber > epsilon) {
				// exploitation();
				if (steheAufEinemInteressantenFeld()) {

				} else {
					exploration();
				}
			} else {

				// Wo befinde ich mich momentan eig?
				if (steheAufEinemInteressantenFeld()) {

				} else {
					exploration();
				}
			}

			System.out.println(ausgabe);

		}
	}

	private static boolean steheAufEinemInteressantenFeld() {
		// Stehe ich auf einem Finish? (IMPLIZIERT, dass ich alle Formulare habe)
		if (currentCellStatus.contains(meinFinish + formCount) && (countForms == formCount)) {
			ausgabe = "finish";
			return true;

		}
		// Stehe ich auf einem Formular? (IMPLIZIERT, dass ich dieses Formular als
		// nächstes brauche)
		else if (currentCellStatus.contains(meinForm + (countForms + 1))) {
			ausgabe = "take";
			karte[startX][startY].setFeldStatus("FLOOR");
			countForms++;
			kombinator.setMeinForm(meinForm + (countForms + 1));

			if (countForms == formCount) {
				for (int i = 0; i < karte.length; i++) {
					for (int j = 0; j < karte[i].length; j++) {

						if (karte[i][j] != null && karte[i][j].getFeldStatus().contains(meinFinish)) {
							karte[i][j].setExplorationsZahl(6);
						}

					}
				}
			}
			return true;

		}
		// Ich stehe auf einem leeren Feld mit Gegner und muss eine Runde aussetzen
//		else if (currentCellStatus.equals("FLOOR !")) {
//			ausgabe = "position";
//			return true;
//		}
		return false;
	}

	private static void aktualsiereStatusMeldungen(Scanner input) {

		// TODO prüfe Warteschlange auf doppelte Werte
		// Mitte auf Explorationszahl 1 setzen (der letzten beiden Elemente in der
		// Warteschlange)

		cellStati.clear();
		tempCellStati.clear();
		floorCount = 0;

		// Rundeninformationen auslesen
		lastActionsResult = input.nextLine();
		currentCellStatus = input.nextLine();

		// TODO was wenn Objekt auf anderen Seite des Spielfelds liegt?
		// TODO Feldaktualisierung falls jemand mein Formular kickt!!! --> Sich also die
		// position ändert?
		// ((x + richtung) + breite) % breite;
		northCellStatus = input.nextLine();
		if (startY == 0) {
			if (karte[startX][sizeY] == null) {
				karte[startX][sizeY] = new Explorer(northCellStatus, playerId);
				zieleAufgedeckt++;
			}
		} else if (karte[startX][startY - 1] == null) {
			karte[startX][startY - 1] = new Explorer(northCellStatus, playerId);
			zieleAufgedeckt++;

		}
		eastCellStatus = input.nextLine();
		if (startX == sizeX) {
			if (karte[0][startY] == null) {
				karte[0][startY] = new Explorer(eastCellStatus, playerId);
				zieleAufgedeckt++;
			}
		} else if (karte[startX + 1][startY] == null) {
			karte[startX + 1][startY] = new Explorer(eastCellStatus, playerId);
			zieleAufgedeckt++;

		}
		southCellStatus = input.nextLine();
		if (startY == sizeY) {
			if (karte[startX][0] == null) {
				karte[startX][0] = new Explorer(southCellStatus, playerId);
				zieleAufgedeckt++;
			}
		} else if (karte[startX][startY + 1] == null) {
			karte[startX][startY + 1] = new Explorer(southCellStatus, playerId);
			zieleAufgedeckt++;
		}
		westCellStatus = input.nextLine();
		if (startX == 0) {
			if (karte[sizeX][startY] == null) {
				karte[sizeX][startY] = new Explorer(westCellStatus, playerId);
				zieleAufgedeckt++;
			}
		} else if (karte[startX - 1][startY] == null) {
			karte[startX - 1][startY] = new Explorer(westCellStatus, playerId);
			zieleAufgedeckt++;
		}

		cellStati.add(northCellStatus + " norden");
		cellStati.add(eastCellStatus + " osten");
		cellStati.add(southCellStatus + " sueden");
		cellStati.add(westCellStatus + " westen");

		// Bewegungsfelder zaehlen
		for (String currentCellStatus : cellStati) {
			if (!currentCellStatus.contains("WALL")) {
				if (currentCellStatus.contains(meinFinish)) {

				}
				tempCellStati.add(currentCellStatus);
				floorCount++;
				if (currentCellStatus.contains(meinFinish)) {
					String[] partsF = currentCellStatus.split(" ");

					String lastDigitF = partsF[2];
					int lastF = Integer.parseInt(lastDigitF);

					formCount = lastF;
					kombinator.setMeinFinish(meinFinish + formCount);
				}
			}
		}

		karte[startX][startY].setExplorationsZahl(floorCount);

		// TODO Ich weiss nicht ob ich alle habe wenn ich den Bearbeiter noch nicht
		// gefunden habe
//		if (countForms == formCount && sachbearbeiterGefunden) {
//			for (int i = 0; i < karte.length; i++) {
//				for (int j = 0; j < karte[i].length; j++) {
//					if (karte[i][j] != null && karte[i][j].getFeldStatus().contains(meinFinish)) {
//						karte[i][j].setExplorationsZahl(6);
//					}
//				}
//			}
//		}

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

		if (ausgabe.equals("go south")) {
			if (startY == sizeY) {
				startY = 0;
			} else
				startY += 1;
		} else if (ausgabe.equals("go east")) {
			if (startX == sizeX) {
				startX = 0;
			} else
				startX += 1;
		} else if (ausgabe.equals("go west")) {
			if (startX == 0) {
				startX = sizeX;
			} else
				startX -= 1;

		} else {

			if (startY == 0) {
				startY = sizeY;
			} else
				startY -= 1;
		}

	}

	private static void exploitation() {
		// TODO Ich bin bisher nur ein dummer Bot der keine Strategie hat

	}

}
