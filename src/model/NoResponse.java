package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class NoResponse {

	private static int playerId;
	private static String ausgabe = "";
	private static String vorzug = "";

	private static int schrittzahl = 0;

	private static String lastActionsResult;
	private static String currentCellStatus;
	private static String northCellStatus;
	private static String eastCellStatus;
	private static String southCellStatus;
	private static String westCellStatus;

	private static int gehNachNorden;
	private static int gehNachOsten;
	private static int gehNachSüden;
	private static int gehNachWesten;

	private static List<String> cellStati = new ArrayList<>();

	private static Map<String, String> karte = new HashMap<>();

	private static int startX;
	private static int startY;
	private static int kartenGroesse;

	private static final String SACKGASSE = "SACKGASSE";

	private static String meinFinish;
	private static String meinForm;
	private static int countForms = 1;
	private static int formCount = 0;
	private static int floorCount = 0;
	private static int groesseNachher = 1;
	private static int groesseVorher = 0;
	private static int sackgassenAnzahl = 0;

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

	private static void turn(Scanner input) {

		while (input.hasNext()) {
			floorCount = 0;

			// Rundeninformationen auslesen
			lastActionsResult = input.nextLine();
			currentCellStatus = input.nextLine();

			// Was passiert wenn ich alle Felder schon kenne? --> Sackgasse
			groesseVorher = karte.size();

			cellStati.clear();

			northCellStatus = input.nextLine();
			eastCellStatus = input.nextLine();
			southCellStatus = input.nextLine();
			westCellStatus = input.nextLine();

			cellStati.add(northCellStatus);
			cellStati.add(eastCellStatus);
			cellStati.add(southCellStatus);
			cellStati.add(westCellStatus);

			// Bewegungsfelder zaehlen
			for (String currentCellStatus : cellStati) {
				if (currentCellStatus.contains(meinFinish) || currentCellStatus.contains(meinForm)
						|| currentCellStatus.contains("FLOOR")) {
					floorCount++;
				}
			}

			if (!karte.containsKey(startX + "" + (startY - 1))) {
				karte.put(startX + "" + (startY - 1), northCellStatus);
			}

			if (!karte.containsKey((startX + 1) + "" + startY)) {
				karte.put((startX + 1) + "" + startY, eastCellStatus);
			}

			if (!karte.containsKey(startX + "" + (startY + 1))) {
				karte.put(startX + "" + (startY + 1), southCellStatus);
			}

			if (!karte.containsKey((startX - 1) + "" + startY)) {
				karte.put((startX - 1) + "" + startY, westCellStatus);
			}

			groesseNachher = karte.size();

			if ((floorCount == 1 || (floorCount == 2 && groesseVorher == groesseNachher))) {
				karte.replace(startX + "" + startY, SACKGASSE);
				sackgassenAnzahl++;
			}

			// Rundenaktion ausgeben
			rundenAktion();

			// Koordinaten anpassen
			System.out.println(ausgabe);
			if (ausgabe.equals("go north")) {
				setGehNachNorden();
			}

			if (ausgabe.equals("go east")) {
				setGehNachOsten();
			}

			if (ausgabe.equals("go south")) {
				setGehNachSueden();
			}

			if (ausgabe.equals("go west")) {
				setGehNachWesten();
			}

			// TODO Koordinaten stimmen nicht

			// Debug Information ausgeben (optional möglich)
			System.err.println("Schrittzahl: " + schrittzahl + "\nElemente aufgedeckt: " + karte.size() + " Von: "
					+ kartenGroesse + "\nFloorCount: " + floorCount + "\nSackgassenanzahl " + sackgassenAnzahl
					+ "\nStandpunkt X " + startX + " Y " + startY + "\nIch stehe auf "
					+ karte.get(startX + "" + startY));

		}
	}

	private static void rundenAktion() {

		// Wo stehe ich?
		if (positionsCheck()) {
			return;
		}

		// Verschiedene Optionen je nach Anzahl der Bewegungsmöglichkeiten
		switch (floorCount) {
		case 1:
			if (schrittzahl != 0) {
				ausgabe = vorzug;
				break;
			}
		case 2:
			if (groesseVorher == groesseNachher) {

				break;
			}
		case 3:

		default:

			// Wo will ich alternativ hin?
			if (finishOrForm(northCellStatus, "go north", "go south", startX + "" + (startY - 1))) {
				break;
			}
			if (finishOrForm(eastCellStatus, "go east", "go west", (startX + 1) + "" + (startY - 1))) {
				break;
			}
			if (finishOrForm(southCellStatus, "go south", "go north", startX + "" + (startY + 1))) {
				break;
			}
			if (finishOrForm(westCellStatus, "go west", "go east", (startX - 1) + "" + startY)) {
				break;
			}

//			if (floor(northCellStatus, "go north", "go south", startX + "" + (startY - 1))) {
//				break;
//			}
//			if (floor(eastCellStatus, "go east", "go west", (startX + 1) + "" + (startY - 1))) {
//				break;
//			}
			if (floor(southCellStatus, "go south", "go north", startX + "" + (startY + 1))) {
				break;
			}
			floor(westCellStatus, "go west", "go east", (startX - 1) + "" + startY);
		}

		schrittzahl++;

	}

	private static boolean floor(String cellStatus, String richtung, String vorherigerZug, String koordinaten) {
		if (cellStatus.contains("FLOOR")) {
			if (karte.get(koordinaten) != null && karte.get(koordinaten).equals(SACKGASSE)) {

				return false;

			}
			ausgabe = richtung;
			vorzug = vorherigerZug;
			return true;
		}
		return false;

	}

	private static boolean positionsCheck() {

		// Stehe ich auf einem Finish? (IMPLIZIERT, dass ich alle Formulare habe)
		if (currentCellStatus.contains(meinFinish + formCount)) {
			ausgabe = "finish";
			return true;

		}
		// Stehe ich auf einem Formular? (IMPLIZIERT, dass ich dieses Formular als
		// nächstes brauche)
		else if (currentCellStatus.contains(meinForm + countForms)) {
			ausgabe = "take";
			countForms++;
			return true;

		}
		// Ich stehe auf einem leeren Feld mit Gegner und muss eine Runde aussetzen
		else if (currentCellStatus.equals("FLOOR !")) {
			ausgabe = "position";
			return true;
		}
		return false;

	}

	private static void init(Scanner input) {
		// 1. Zeile: Maze Infos
		int sizeX = input.nextInt(); // X-Groesse des Spielfeldes (Breite)
		int sizeY = input.nextInt(); // Y-Groesse des Spielfeldes (Hoehe)
		int level = input.nextInt(); // Level des Matches
		input.nextLine(); // Beenden der ersten Zeile
		// 2. Zeile: Player Infos
		playerId = input.nextInt(); // id dieses Players / Bots
		startX = input.nextInt(); // X-Koordinate der Startposition dieses Player
		startY = input.nextInt(); // Y-Koordinate der Startposition dieses Players
		input.nextLine(); // Beenden der zweiten Zeile

		karte.put(startX + "" + startY, currentCellStatus);
		kartenGroesse = sizeX * sizeY;

		meinFinish = "FINISH " + playerId + " ";
		meinForm = "FORM " + playerId + " ";

	}

	public static boolean finishOrForm(String cellStatus, String richtung, String vorherigerZug, String koordinaten) {
		// TODO
		// Finishline

		if (cellStatus.contains(meinFinish)) {
			String[] partsF = cellStatus.split(" ");
			List<String> list = Arrays.asList(partsF);
			if (list.contains("!")) {
				list.remove(list.size() - 1);
				partsF = list.toArray(partsF);
			}
			String lastDigitF = partsF[partsF.length - 1].trim();
			int lastF = Integer.parseInt(lastDigitF);

			formCount = lastF;
			ausgabe = richtung;
			vorzug = vorherigerZug;
			return true;

		} else if (cellStatus.contains(meinForm + countForms)) {

			ausgabe = richtung;
			vorzug = vorherigerZug;
			return true;

		}
		return false;
	}

	public static int getPlayerId() {
		return playerId;
	}

	public static void setPlayerId(int playerId) {
		NoResponse.playerId = playerId;
	}

	public static String getAusgabe() {
		return ausgabe;
	}

	public static void setAusgabe(String ausgabe) {
		NoResponse.ausgabe = ausgabe;
	}

	public static String getVorzug() {
		return vorzug;
	}

	public static void setVorzug(String vorzug) {
		NoResponse.vorzug = vorzug;
	}

	public static int getSchrittzahl() {
		return schrittzahl;
	}

	public static void setSchrittzahl(int schrittzahl) {
		NoResponse.schrittzahl = schrittzahl;
	}

	public static String getLastActionsResult() {
		return lastActionsResult;
	}

	public static void setLastActionsResult(String lastActionsResult) {
		NoResponse.lastActionsResult = lastActionsResult;
	}

	public static String getCurrentCellStatus() {
		return currentCellStatus;
	}

	public static void setCurrentCellStatus(String currentCellStatus) {
		NoResponse.currentCellStatus = currentCellStatus;
	}

	public static String getNorthCellStatus() {
		return northCellStatus;
	}

	public static void setNorthCellStatus(String northCellStatus) {
		NoResponse.northCellStatus = northCellStatus;
	}

	public static String getEastCellStatus() {
		return eastCellStatus;
	}

	public static void setEastCellStatus(String eastCellStatus) {
		NoResponse.eastCellStatus = eastCellStatus;
	}

	public static String getSouthCellStatus() {
		return southCellStatus;
	}

	public static void setSouthCellStatus(String southCellStatus) {
		NoResponse.southCellStatus = southCellStatus;
	}

	public static String getWestCellStatus() {
		return westCellStatus;
	}

	public static void setWestCellStatus(String westCellStatus) {
		NoResponse.westCellStatus = westCellStatus;
	}

	public static int getGehNachNorden() {
		return gehNachNorden;
	}

	public static void setGehNachNorden() {
		NoResponse.gehNachNorden = startY - 1;
	}

	public static int getGehNachOsten() {
		return gehNachOsten;
	}

	public static void setGehNachOsten() {
		NoResponse.gehNachOsten = startX + 1;
	}

	public static int getGehNachSueden() {
		return gehNachSüden;
	}

	public static void setGehNachSueden() {
		NoResponse.gehNachSüden = startY + 1;
	}

	public static int getGehNachWesten() {
		return gehNachWesten;
	}

	public static void setGehNachWesten() {
		NoResponse.gehNachWesten = startX - 1;
	}

	public static List<String> getCellStati() {
		return cellStati;
	}

	public static void setCellStati(List<String> cellStati) {
		NoResponse.cellStati = cellStati;
	}

	public static Map<String, String> getKarte() {
		return karte;
	}

	public static void setKarte(Map<String, String> karte) {
		NoResponse.karte = karte;
	}

	public static int getStartX() {
		return startX;
	}

	public static void setStartX(int startX) {
		NoResponse.startX = startX;
	}

	public static int getStartY() {
		return startY;
	}

	public static void setStartY(int startY) {
		NoResponse.startY = startY;
	}

	public static int getKartenGroesse() {
		return kartenGroesse;
	}

	public static void setKartenGroesse(int kartenGroesse) {
		NoResponse.kartenGroesse = kartenGroesse;
	}

	public static String getMeinFinish() {
		return meinFinish;
	}

	public static void setMeinFinish(String meinFinish) {
		NoResponse.meinFinish = meinFinish;
	}

	public static String getMeinForm() {
		return meinForm;
	}

	public static void setMeinForm(String meinForm) {
		NoResponse.meinForm = meinForm;
	}

	public static int getCountForms() {
		return countForms;
	}

	public static void setCountForms(int countForms) {
		NoResponse.countForms = countForms;
	}

	public static int getFormCount() {
		return formCount;
	}

	public static void setFormCount(int formCount) {
		NoResponse.formCount = formCount;
	}

	public static int getFloorCount() {
		return floorCount;
	}

	public static void setFloorCount(int floorCount) {
		NoResponse.floorCount = floorCount;
	}

	public static int getGroesseNachher() {
		return groesseNachher;
	}

	public static void setGroesseNachher(int groesseNachher) {
		NoResponse.groesseNachher = groesseNachher;
	}

	public static int getGroesseVorher() {
		return groesseVorher;
	}

	public static void setGroesseVorher(int groesseVorher) {
		NoResponse.groesseVorher = groesseVorher;
	}

	public static int getSackgassenAnzahl() {
		return sackgassenAnzahl;
	}

	public static void setSackgassenAnzahl(int sackgassenAnzahl) {
		NoResponse.sackgassenAnzahl = sackgassenAnzahl;
	}

	public static String getSackgasse() {
		return SACKGASSE;
	}

}
