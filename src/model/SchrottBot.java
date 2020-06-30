package model;

import java.util.Scanner;

/**
 * Klasse eines Bots für das VITMaze
 * 
 * @author Catherine.Camier
 * @version 1.1
 *
 */
public class SchrottBot {

	static int sizeX, sizeY, level, playerId, startX, startY;

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
		input = init(input);

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

	private static void rundenaktion(String lastActionsResult, String currentCellStatus, String northCellStatus,
			String eastCellStatus, String southCellStatus, String westCellStatus) {

		// Finish
		if (currentCellStatus.equals("FINISH " + playerId + " 0"))
			System.out.println("finish");

		// Go
		else if (westCellStatus.equals("FLOOR") || westCellStatus.equals("FINISH " + playerId + " 0")) {

			System.out.println("go west");
		}

		// Position
		else
			System.out.println("position");

	}

	public static Scanner init(Scanner input) {

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
		return input;
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

}
