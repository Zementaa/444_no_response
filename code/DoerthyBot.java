package de.vitbund.vitmaze.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DoerthyBot {

	private static int sizeX;
	private static int sizeY;
	private static int level;
	private static int playerId;
	private static int startX;
	private static int startY;

	private static String lastActionsResult;
	private static String currentCellStatus;
	private static String northCellStatus;
	private static String eastCellStatus;
	private static String southCellStatus;
	private static String westCellStatus;

	/**
	 * Liste für die möglichen Richtungen des aktuellen Standortes
	 */
	private static List richtungen = new ArrayList();
	/**
	 * welche Richtung wurde in der letzter Runde gewählt
	 */
	private static String wayLastRound;
	private static String way2LastRound;
	private static int indexActualWay;
	/**
	 * Attribute zum für ausgelesene Werte der Formulare und des Ziels
	 */
	private static String playerIdForm;
	private static String playerIdSB;
	private static String formId;
	private static String formCount;

	// Zufällige Richtung
	private static Random wurfel = new Random();

	// Array zum speichern der letzten 3 Felder die besucht wurden, dient dazu das
	// der Bot nicht im Kreis läuft
	private static int[][] arrLetzteFelder = new int[3][2];
	/**
	 * Karteninformationen
	 */
	// private static Map<Runde, Array<Integer>> karte = new HashMap();
	private static Feld[] kartendaten;

	/**
	 * Hauptmethode zum Ausführen des Bots
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Scanner zum Auslesen der Standardeingabe, welche Initialisierungs- und
		// Rundendaten liefert
		Scanner input = new Scanner(System.in);

		init(input);

		// TURN (Wiederholung je Runde notwendig)
		while (input.hasNext()) {

			// Rundeninformationen auslesen
			lastActionsResult = input.nextLine();
			currentCellStatus = input.nextLine();
			northCellStatus = input.nextLine();
			eastCellStatus = input.nextLine();
			southCellStatus = input.nextLine();
			westCellStatus = input.nextLine();

			// Debug Information ausgeben (optional möglich)
			System.err.println("Ergebnis Vorrunde: " + lastActionsResult + "\nRichtung letzte Runde: " + wayLastRound
					+ "\nRichtung vorletzte Runde: " + way2LastRound);

			// extrahiert aus Formularfeld PlayerID und FormID
			if (currentCellStatus.contains("FORM")) {
				String playerIdformId = currentCellStatus.substring(5, currentCellStatus.length());
				playerIdForm = playerIdformId.substring(0, (playerIdformId.indexOf(" ") - 1));
				formId = playerIdformId.substring((playerIdformId.indexOf(" ") + 1), playerIdformId.length());
			}

			// extrahiert aus Sachbearbeiterfeld PlayerID und Anzahl der benötigen Formular
			if (currentCellStatus.contains("FINISH")) {
				String playerIdformCount = currentCellStatus.substring(6, currentCellStatus.length());
				playerIdSB = playerIdformCount.substring(0, (playerIdformCount.indexOf(" ") - 1));
				formCount = playerIdformCount.substring((playerIdformCount.indexOf(" ") + 1),
						playerIdformCount.length());
			}

			// abfragen des Status
			switch (lastActionsResult) {
			case "NOK BLOCKED":
				// FIXME: position abfragen um Strings und damit das richtungen array neu zu
				// befüllen
				System.out.println("position");
				break;
			case "NOK NOTYOURS":
				// FIXME: position abfragen um Strings und damit das richtungen array neu zu
				// befüllen
				System.out.println("position");
				break;
			case "NOK EMPTY":
				// FIXME: position abfragen um Strings und damit das richtungen array neu zu
				// befüllen
				System.out.println("position");
				break;
			case "NOK WRONGORDER":
				// FIXME: position abfragen um Strings und damit das richtungen array neu zu
				// befüllen
				System.out.println("position");
				break;
			case "NOK NOTSUPPORTED":
				// FIXME: position abfragen um Strings und damit das richtungen array neu zu
				// befüllen
				System.out.println("position");
				break;
			default:
				if (currentCellStatus.contains("FINISH " + playerId)) {
					finish();
					break;
				} else {
					// Rundenaktion ausgeben
					anfrageSB();
					anfrageForm();
					weiterGehen();
				}

				richtungen.clear();

			} // schließt switch

		} // schließt while

		// Eingabe schliessen (letzte Aktion)
		input.close();
	}

	// Methoden
	/**
	 * Methode zur Übernahme von Kartendaten, Startpunkt und SpielerID
	 * 
	 * @param input
	 */
	public static void init(Scanner input) {
		String name = "";

		// INIT - Auslesen der Initialdaten
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

	}

	public static void anfrageSB() {
		way2LastRound = wayLastRound;

		// sollte eins der umliegenden Felder dein SB sein geh hin und schrei finish
		if (northCellStatus.contains("FINISH " + playerId)) {
			wayLastRound = "go north";
			direction("go north");
			finish();
		}
		if (eastCellStatus.contains("FINISH " + playerId)) {
			wayLastRound = "go east";
			direction("go east");
			finish();
		}
		if (southCellStatus.contains("FINISH " + playerId)) {
			wayLastRound = "go south";
			direction("go south");
			finish();
		}
		if (westCellStatus.contains("FINISH " + playerId)) {
			direction("go west");
			finish();
		}
	}

	public static void anfrageForm() {
		// TODO: muss es hier nochmal gesetzt werden?
		way2LastRound = wayLastRound;

		// FIXME: läUft auf fehler und nimmt dann trotzdem
		// sollte eins der umliegenden Felder dein Formular sein, geh hin und nimm es
		if (northCellStatus.contains("FORM " + playerId)) {
			wayLastRound = "go north";
			direction("go north");
			take();
		}
		if (eastCellStatus.contains("FORM " + playerId)) {
			wayLastRound = "go east";
			direction("go east");
			take();
		}
		if (southCellStatus.contains("FORM " + playerId)) {
			wayLastRound = "go south";
			direction("go south");
			take();
		}
		if (westCellStatus.contains("FORM " + playerId)) {
			wayLastRound = "go west";
			direction("go west");
			take();
		}
	}

	public static void weiterGehen() {
		// TODO: muss es hier nochmal gesetzt werden?
		way2LastRound = wayLastRound;

		// hast du nur FLOORs um dich, such dir deinen nächsten weg
		if (northCellStatus.equals("FLOOR") && wayLastRound != "go south")
			richtungen.add("go north");
		if (eastCellStatus.equals("FLOOR") && wayLastRound != "go west")
			richtungen.add("go east");
		if (southCellStatus.equals("FLOOR") && wayLastRound != "go north")
			richtungen.add("go south");
		if (westCellStatus.equals("FLOOR") && wayLastRound != "go east")
			richtungen.add("go west");

		if (richtungen == null) {
			if (wayLastRound.equals("go north"))
				direction("go south");
			if (wayLastRound.equals("go east"))
				direction("go west");
			if (wayLastRound.equals("go south"))
				direction("go north");
			if (wayLastRound.equals("go west"))
				direction("go east");
		} else {
			switch (richtungen.size()) {
			case 2:
				indexActualWay = wurfel.nextInt(2);
				wayLastRound = (String) richtungen.get(indexActualWay);
				direction(wayLastRound);
				break;
			case 3:
				indexActualWay = wurfel.nextInt(3);
				wayLastRound = (String) richtungen.get(indexActualWay);
				direction(wayLastRound);
				break;
			case 4:
				indexActualWay = wurfel.nextInt(4);
				wayLastRound = (String) richtungen.get(indexActualWay);
				direction(wayLastRound);
				break;
			default:
				wayLastRound = (String) richtungen.get(0);
				direction(wayLastRound);
			}
		}
	}

	/**
	 * Methode zur Übermittlung der Richtung an den Bot
	 * 
	 * @param indexActualWay
	 */
	public static void direction(String direction) {
		System.out.println(direction);
	}

	public static void finish() {
		System.out.println("finish");
	}

	public static void take() {
		System.out.println("take");
	}

	// G&S
	public static int getSizeX() {
		return sizeX;
	}

	public static void setSizeX(int sizeX) {
		DoerthyBot.sizeX = sizeX;
	}

	public static int getSizeY() {
		return sizeY;
	}

	public static void setSizeY(int sizeY) {
		DoerthyBot.sizeY = sizeY;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		DoerthyBot.level = level;
	}

	public static int getPlayerId() {
		return playerId;
	}

	public static void setPlayerId(int playerId) {
		DoerthyBot.playerId = playerId;
	}

	public static int getStartX() {
		return startX;
	}

	public static void setStartX(int startX) {
		DoerthyBot.startX = startX;
	}

	public static int getStartY() {
		return startY;
	}

	public static void setStartY(int startY) {
		DoerthyBot.startY = startY;
	}

}
