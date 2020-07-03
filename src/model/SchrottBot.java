package model;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
	private static Zug aktuellerZug;
	private static Zug naechsterZug;

	private static KartenElement kartenElement;
	static Boolean einbahnstrasse = false;

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

			naechstenZugAktualisieren(northCellStatus, eastCellStatus, southCellStatus, westCellStatus);

			// Rundenaktion ausgeben
			rundenaktion(lastActionsResult, currentCellStatus);

			// Debug Information ausgeben (optional möglich)
			System.err.println("Ergebnis Vorrunde: aktuell X: " + aktuellerZug.getKoordinaten().getX() + "Y: "
					+ aktuellerZug.getKoordinaten().getY() + "\nnaechster Zug X: "
					+ naechsterZug.getKoordinaten().getX() + "Y: " + naechsterZug.getKoordinaten().getY()
					+ "\nSchrittzahl: " + aktuellerZug.getSchrittzahl() + " Vorzug: " + aktuellerZug.getVorzug()
					+ "\n Ziele: " + meineZiele.values().size());

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
	private static void rundenaktion(String lastActionsResult, String currentCellStatus) {
		int floorCount = 0;

		for (

		String floor : statusMeinesUmfelds) {
			if (floor.equals("FLOOR") || floor.contains("FINISH")) {
				floorCount++;
			}
		}

		if (floorCount == 2) {
			einbahnstrasse = true;
		}

		if (floorCount > 1 || aktuellerZug.getSchrittzahl() == 0) {
//			if (lastActionsResult.equals("NOK BLOCKED")) {
//				// move();
//			}

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
							&& !bewegungsRichtung[i].equals(aktuellerZug.getVorzug())) {

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
				ausgabe = aktuellerZug.getVorzug();
			}
		}
		aktuellerZug.aktualisieren(ausgabe);
		// kartenElement.setKoordinaten(aktuellerZug.getKoordinaten());
		// if (!meineZiele.containsValue(aktuellerZug.getKoordinaten())) {
		meineZiele.put(kartenElement, aktuellerZug.getKoordinaten());
		if (kartenElement.getClass() == Sackgasse.class || meineZiele.containsValue(aktuellerZug.getKoordinaten())) {
			sackgassen.put(new Sackgasse(), aktuellerZug.getKoordinaten());
		}
		// }
		System.out.println(ausgabe);

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

		aktuellerZug = new Zug(startX, startY);
		naechsterZug = aktuellerZug;

	}

	public static void naechstenZugAktualisieren(String northCellStatus, String eastCellStatus, String southCellStatus,
			String westCellStatus) {
		List<Integer> list = Arrays.asList(1, 2, 3, 4);
		SecureRandom random = new SecureRandom();
		// Collections.shuffle(list, random);
		naechsterZug = aktuellerZug;

		for (int i = 0; i < 4; i++) {

			switch (list.get(i)) {
			// nördlich
			case 1:
				statusMeinesUmfelds[i] = northCellStatus;
				bewegungsRichtung[i] = "go north";

				break;

			// östlich
			case 2:
				statusMeinesUmfelds[i] = eastCellStatus;
				bewegungsRichtung[i] = "go east";

				break;

			// südlich
			case 3:
				statusMeinesUmfelds[i] = southCellStatus;
				bewegungsRichtung[i] = "go south";

				break;

			// westlich
			default:
				statusMeinesUmfelds[i] = westCellStatus;
				bewegungsRichtung[i] = "go west";

				break;
			}
			// TODO
			koordinatenMeinesUmfelds[i] = naechsterZug.aktualisieren2(bewegungsRichtung[i]);

		}

	}

//	// Take First
//	if (currentCellStatus.equals("FORM " + playerId + " " + firstFormId)) {
//		System.out.println("take");
//		meineZiele.put(new Formular(firstFormId), koordinaten);
//	}
//
//	// Take any
//	if (currentCellStatus.equals("FORM " + playerId + " " + formId)) {
//		System.out.println("take");
//		meineZiele.put(new Formular(formId), koordinaten);
//		nextFormId++;
//	}
//	// Sachbearbeiter gefunden -> Position und formCount auslesen
//	// TODO
//	if (currentCellStatus.equals("FINISH " + playerId + " " + 4)) {
//		// System.out.println("finish");
//		meineZiele.put(new Sachbearbeiter(), koordinaten);
//		// hier soll ausgelesen werden wie viele Formulare es gibt!
//		formCount = 4;
//	}
//
//	// Go
//	else if (westCellStatus.equals("FLOOR") || westCellStatus.equals("FINISH " + playerId + " 0")) {
//
//		System.out.println("go west");
//		koordinaten.setX(koordinaten.getX() - 1);
//
//	}
//
//	// Position
//	else
//		System.out.println("position");

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

	public static int getFirstFormId() {
		return firstFormId;
	}

	public static void setFirstFormId(int firstFormId) {
		SchrottBot.firstFormId = firstFormId;
	}

	public static int getFormId() {
		return formId;
	}

	public static void setFormId(int formId) {
		SchrottBot.formId = formId;
	}

	public static int getNextFormId() {
		return nextFormId;
	}

	public static void setNextFormId(int nextFormId) {
		SchrottBot.nextFormId = nextFormId;
	}

	public static int getCountForms() {
		return countForms;
	}

	public static void setCountForms(int countForms) {
		SchrottBot.countForms = countForms;
	}

	public static Map<KartenElement, Koordinaten> getMeineZiele() {
		return meineZiele;
	}

	public static void setMeineZiele(Map<KartenElement, Koordinaten> meineZiele) {
		SchrottBot.meineZiele = meineZiele;
	}

	public static Map<Sackgasse, Koordinaten> getSackgassen() {
		return sackgassen;
	}

	public static void setSackgassen(Map<Sackgasse, Koordinaten> sackgassen) {
		SchrottBot.sackgassen = sackgassen;
	}

	public static String[] getStatusMeinesUmfelds() {
		return statusMeinesUmfelds;
	}

	public static void setStatusMeinesUmfelds(String[] statusMeinesUmfelds) {
		SchrottBot.statusMeinesUmfelds = statusMeinesUmfelds;
	}

	public static Koordinaten[] getKoordinatenMeinesUmfelds() {
		return koordinatenMeinesUmfelds;
	}

	public static void setKoordinatenMeinesUmfelds(Koordinaten[] koordinatenMeinesUmfelds) {
		SchrottBot.koordinatenMeinesUmfelds = koordinatenMeinesUmfelds;
	}

	public static String[] getBewegungsRichtung() {
		return bewegungsRichtung;
	}

	public static void setBewegungsRichtung(String[] bewegungsRichtung) {
		SchrottBot.bewegungsRichtung = bewegungsRichtung;
	}

	public static String getAusgabe() {
		return ausgabe;
	}

	public static void setAusgabe(String ausgabe) {
		SchrottBot.ausgabe = ausgabe;
	}

	public static Zug getAktuellerZug() {
		return aktuellerZug;
	}

	public static void setAktuellerZug(Zug aktuellerZug) {
		SchrottBot.aktuellerZug = aktuellerZug;
	}

	public static Zug getNaechsterZug() {
		return naechsterZug;
	}

	public static void setNaechsterZug(Zug naechsterZug) {
		SchrottBot.naechsterZug = naechsterZug;
	}

	public static KartenElement getKartenElement() {
		return kartenElement;
	}

	public static void setKartenElement(KartenElement kartenElement) {
		SchrottBot.kartenElement = kartenElement;
	}

}
