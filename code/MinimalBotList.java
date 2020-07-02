package de.vitbund.vitmaze.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Klasse eines minimalen Bots für das VITMaze
 * 
 * @author Patrick.Stalljohann
 * @version 1.0
 *
 */
public class MinimalBotList {

	private static int sizeX;
	private static int sizeY;
	private static int level;
	private static int playerId;
	private static int startX;
	private static int startY;
	/**
	 * Liste für die möglichen Richtungen des aktuellen Standortes
	 */
	private static List richtungen = new ArrayList();
	/**
	 * welche Richtung wurde in der letzter Runde gewählt
	 */
	private static String wayLastRound = null;
	private static int indexActualWay = 0;

	/**
	 * Hauptmethode zum Ausführen des Bots
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Scanner zum Auslesen der Standardeingabe, welche Initialisierungs- und
		// Rundendaten liefert
		Scanner input = new Scanner(System.in);

		// Liste für die möglichen Richtungen
//		List richtungen = new ArrayList();
		// String[] arr = { "go west", "go north", "go east", "go south" };

		String wayLastRound = null;
		int indexActualWay = 0;

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

			if (currentCellStatus.equals("FINISH " + playerId + " 0")) {
				System.out.println("finish");
				break;
			} else {
				// Rundenaktion ausgeben
				// System.out.println(arr[(int) (Math.random() * 10 / 3.33)]);
				if (northCellStatus.equals("FLOOR") && wayLastRound != "go south")
					richtungen.add("go north");
				if (eastCellStatus.equals("FLOOR") && wayLastRound != "go west")
					richtungen.add("go east");
				if (southCellStatus.equals("FLOOR") && wayLastRound != "go north")
					richtungen.add("go south");
				if (westCellStatus.equals("FLOOR") && wayLastRound != "go east")
					richtungen.add("go west");

				if (richtungen == null) {
					System.out.println(wayLastRound);
				} else {
					switch (richtungen.size()) {
					case 2:
						indexActualWay = (int) (Math.random() * 10 / 5.5);
						call(indexActualWay);
						break;
					case 3:
						indexActualWay = (int) (Math.random() * 10 / 4);
						call(indexActualWay);
						break;
					case 4:
						indexActualWay = (int) (Math.random() * 10 / 3);
						call(indexActualWay);
						break;
					default:
						wayLastRound = (String) richtungen.get(0);
						System.out.println(richtungen.get(0));
					}
				}
			}
			richtungen.clear();

		}

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

	/**
	 * Methode zur Übermittlung der Richtung an den Bot
	 * 
	 * @param IndexLastCellStatus
	 */
	public static void call(int IndexLastCellStatus) {
		wayLastRound = (String) richtungen.get(IndexLastCellStatus);
		System.out.println(richtungen.get(IndexLastCellStatus));
	}

	// G&S
	public static int getSizeX() {
		return sizeX;
	}

	public static void setSizeX(int sizeX) {
		MinimalBotList.sizeX = sizeX;
	}

	public static int getSizeY() {
		return sizeY;
	}

	public static void setSizeY(int sizeY) {
		MinimalBotList.sizeY = sizeY;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		MinimalBotList.level = level;
	}

	public static int getPlayerId() {
		return playerId;
	}

	public static void setPlayerId(int playerId) {
		MinimalBotList.playerId = playerId;
	}

	public static int getStartX() {
		return startX;
	}

	public static void setStartX(int startX) {
		MinimalBotList.startX = startX;
	}

	public static int getStartY() {
		return startY;
	}

	public static void setStartY(int startY) {
		MinimalBotList.startY = startY;
	}

}
