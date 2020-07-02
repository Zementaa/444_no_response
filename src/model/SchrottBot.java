package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Klasse eines Bots für das VITMaze
 * 
 * @author Catherine.Camier
 * @version 1.1
 *
 */
public class SchrottBot {

	private static int sizeX;
	private static int sizeY;
	private static int level;
	private static int playerId;
	private static int firstFormId = 0;
	private static int formId;
	private static int nextFormId = 0;
	private static int formCount = 0;
	private static int countForms = 0;
	private static int startX;
	private static int startY;
	private static Map<KartenElement, Koordinaten> meineZiele = new HashMap<>();
	private static Map<Sackgasse, Koordinaten> sackgassen = new HashMap<>();
	private static String[] statusMeinesUmfelds = new String[4];
	private static Koordinaten[] koordinatenMeinesUmfelds = new Koordinaten[4];
	private static String[] bewegungsRichtung = { "go north", "go east", "go south", "go west" };
	private static String ausgabe = "";
	private static Zug zug;

	private static KartenElement kartenElement;

	/**
	 * Hauptmethode zum Ausführen des Bots
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
		while (input.hasNext()) {
			// Rundeninformationen auslesen
			String lastActionsResult = input.nextLine();
			String currentCellStatus = input.nextLine();
			String northCellStatus = input.nextLine();
			String eastCellStatus = input.nextLine();
			String southCellStatus = input.nextLine();
			String westCellStatus = input.nextLine();

			// Debug Information ausgeben (optional möglich)
			System.err.println("Ergebnis Vorrunde: " + lastActionsResult);

			// Rundenaktion ausgeben
			rundenaktion(lastActionsResult, currentCellStatus, northCellStatus, eastCellStatus, southCellStatus,
					westCellStatus);

		}

		// Eingabe schliessen (letzte Aktion)
		input.close();
	}

	/**
	 * 
	 * @param lastActionsResult
	 * @param currentCellStatus
	 * @param northCellStatus
	 * @param eastCellStatus
	 * @param southCellStatus
	 * @param westCellStatus
	 */
	private static void rundenaktion(String lastActionsResult, String currentCellStatus, String northCellStatus,
			String eastCellStatus, String southCellStatus, String westCellStatus) {

		statusMeinesUmfelds[0] = northCellStatus;
		statusMeinesUmfelds[1] = eastCellStatus;
		statusMeinesUmfelds[2] = southCellStatus;
		statusMeinesUmfelds[3] = westCellStatus;

		koordinatenAktualisieren();

		int floorCount = 0;

		for (String floor : statusMeinesUmfelds) {
			if (floor.equals("FLOOR") || floor.contains("FINISH")) {
				floorCount++;
			}
		}

		if (floorCount > 1 || zug.getSchrittzahl() == 0) {
			if (lastActionsResult.equals("NOK BLOCKED")) {
				// move();
			}

			if (currentCellStatus.contains("FINISH " + getPlayerId() + " " + getFormCount())) {
				ausgabe = "finish";
			} else {
				for (int i = 0; i < statusMeinesUmfelds.length; i++) {
					if (statusMeinesUmfelds[i].contains("FINISH " + getPlayerId())) {

						String[] parts = statusMeinesUmfelds[i].split(" ");
						String lastDigit = parts[parts.length - 1].trim();
						int last = Integer.parseInt(lastDigit);
						setFormCount(last);
						ausgabe = bewegungsRichtung[i];
						kartenElement = new Sachbearbeiter();
						break;

					} else if (statusMeinesUmfelds[i].equals("FLOOR")
							&& !bewegungsRichtung[i].equals(zug.getVorzug())) {
						if (sackgassen.isEmpty()) {
							ausgabe = bewegungsRichtung[i];
							kartenElement = new Boden();
							break;
						} else {
							for (Sackgasse sackgasse : sackgassen.keySet()) {
								if (sackgasse.getKoordinaten() != koordinatenMeinesUmfelds[i]) {
									ausgabe = bewegungsRichtung[i];
									kartenElement = new Boden();
									break;
								}

							}
						}

					}
				}

			}

		} else {
			if (currentCellStatus.contains("FINISH " + getPlayerId() + " " + getFormCount())) {
				ausgabe = "finish";
			} else {
				kartenElement = new Sackgasse();
				ausgabe = zug.getVorzug();
			}
		}
		zug.aktualisieren(ausgabe);
		meineZiele.put(kartenElement, zug.getKoordinaten());
		if (kartenElement.getClass() == Sackgasse.class) {
			sackgassen.put((Sackgasse) kartenElement, zug.getKoordinaten());
		}
		System.out.println(ausgabe);
//		// Take First
//		if (currentCellStatus.equals("FORM " + playerId + " " + firstFormId)) {
//			System.out.println("take");
//			meineZiele.put(new Formular(firstFormId), koordinaten);
//		}
//
//		// Take any
//		if (currentCellStatus.equals("FORM " + playerId + " " + formId)) {
//			System.out.println("take");
//			meineZiele.put(new Formular(formId), koordinaten);
//			nextFormId++;
//		}
//		// Sachbearbeiter gefunden -> Position und formCount auslesen
//		// TODO
//		if (currentCellStatus.equals("FINISH " + playerId + " " + 4)) {
//			// System.out.println("finish");
//			meineZiele.put(new Sachbearbeiter(), koordinaten);
//			// hier soll ausgelesen werden wie viele Formulare es gibt!
//			formCount = 4;
//		}
//
//		// Go
//		else if (westCellStatus.equals("FLOOR") || westCellStatus.equals("FINISH " + playerId + " 0")) {
//
//			System.out.println("go west");
//			koordinaten.setX(koordinaten.getX() - 1);
//
//		}
//
//		// Position
//		else
//			System.out.println("position");

	}

	private static void koordinatenAktualisieren() {

		// nördliches Feld
		zug.getKoordinaten().setY(zug.getKoordinaten().getY() - 1);

		koordinatenMeinesUmfelds[0] = zug.getKoordinaten();

		// östliches Feld
		zug.getKoordinaten().setX(zug.getKoordinaten().getX() + 1);

		koordinatenMeinesUmfelds[1] = zug.getKoordinaten();

		// südliches Feld
		zug.getKoordinaten().setY(zug.getKoordinaten().getY() + 1);

		koordinatenMeinesUmfelds[2] = zug.getKoordinaten();

		// westliches Feld
		zug.getKoordinaten().setX(zug.getKoordinaten().getX() - 1);

		koordinatenMeinesUmfelds[3] = zug.getKoordinaten();

	}

	/**
	 * 
	 * @param input
	 */
	public static void init(Scanner input) {

		// 1. Zeile: Maze Infos
		sizeX = input.nextInt(); // X-Groesse des Spielfeldes (Breite)
		sizeY = input.nextInt(); // Y-Groesse des Spielfeldes (Hoehe)
		level = input.nextInt(); // Level des Matches
		input.nextLine(); // Beenden der ersten Zeile
		// 2. Zeile: Player Infos
		playerId = input.nextInt(); // id dieses Players / Bots
		startX = input.nextInt(); // X-Koordinate der Startposition dieses Player
		startY = input.nextInt(); // Y-Koordinate der Startposition dieses Players
		input.nextLine(); // Beenden der zweiten Zeile

		zug = new Zug(startX, startY);

	}

	public static int getSizeX() {
		return sizeX;
	}

	public static void setSizeX(int sizeX) {
		SchrottBot.sizeX = sizeX;
	}

	public static int getSizeY() {
		return sizeY;
	}

	public static void setSizeY(int sizeY) {
		SchrottBot.sizeY = sizeY;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		SchrottBot.level = level;
	}

	public static int getPlayerId() {
		return playerId;
	}

	public static void setPlayerId(int playerId) {
		SchrottBot.playerId = playerId;
	}

	public static int getStartX() {
		return startX;
	}

	public static void setStartX(int startX) {
		SchrottBot.startX = startX;
	}

	public static int getStartY() {
		return startY;
	}

	public static void setStartY(int startY) {
		SchrottBot.startY = startY;
	}

	public static int getFormCount() {
		return formCount;
	}

	public static void setFormCount(int formCount) {
		SchrottBot.formCount = formCount;
	}

}
