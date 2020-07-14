package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class NoResponseBot {

	// Übergabeparameter
	private static int sizeX;
	private static int sizeY;
	private static int level;
	private static int playerId;
	private static String lastActionsResult;
	private static Status status = new Status();

	// Merker
	private static int formCount = 0;
	private static int countForms = 1;
	private static int floorCount = 0;

	// Karteninfos
	private static Map<String, KartenElement> meineZiele = new HashMap<>();
	private static KartenElement[] meineZiele2;
	private static int zielZaehler = 0;
	private static Map<String, Sackgasse> sackgassen = new HashMap<>();
	private static KartenElement kartenElement;
	private static KartenElement[][] map;

	// Rundeninfos
	private static String ausgabe = "";
	private static Zug aktuellerZug;
	private static Zug naechsterZug;

	// Sonstiges
	private static String meinFinish;
	private static String meinForm;

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

	public static void init(Scanner input) {

		// 1. Zeile: Maze Infos
		sizeX = input.nextInt(); // X-Groesse des Spielfeldes (Breite)
		sizeY = input.nextInt(); // Y-Groesse des Spielfeldes (Hoehe)
		level = input.nextInt(); // Level des Matches
		input.nextLine(); // Beenden der ersten Zeile
		// 2. Zeile: Player Infos
		playerId = input.nextInt(); // id dieses Players / Bots
		int startX = input.nextInt(); // X-Koordinate der Startposition dieses Player
		int startY = input.nextInt(); // Y-Koordinate der Startposition dieses Players
		input.nextLine(); // Beenden der zweiten Zeile

		aktuellerZug = new Zug(startX, startY);
		naechsterZug = new Zug(startX, startY);

		map = new KartenElement[sizeX][sizeY];
		map[startX][startY] = new Boden(aktuellerZug.getKoordinaten());

		meineZiele2 = new KartenElement[startX * startY];
		meineZiele2[zielZaehler] = new Boden(aktuellerZug.getKoordinaten());
		zielZaehler++;
		meineZiele.put(aktuellerZug.getKoordinaten().getName(), new Boden(aktuellerZug.getKoordinaten()));

		meinFinish = "FINISH " + playerId + " ";
		meinForm = "FORM " + playerId + " ";

	}

	public static void turn(Scanner input) {
		while (input.hasNext()) {
			// Rundeninformationen auslesen
			lastActionsResult = input.nextLine();
			status.setCurrentCellStatus(input.nextLine());

			status.aktualisiereStatus(input.nextLine(), input.nextLine(), input.nextLine(), input.nextLine());

			// Rundenaktion ausgeben
			rundenAktion();

			System.out.println(ausgabe);

			StringBuilder bld = new StringBuilder();
			for (int i = 0; i < meineZiele2.length; ++i) {

				bld.append(meineZiele2[i]);

			}
			String str = bld.toString();

			// Debug Information ausgeben (optional möglich)
			System.err.println("Koordinaten: Aktuell X: " + aktuellerZug.getKoordinaten().getX() + " Y: "
					+ aktuellerZug.getKoordinaten().getY() + "\nSchrittzahl: " + aktuellerZug.getSchrittzahl()
					+ " Vorzug: " + aktuellerZug.getVorzug() + "\n Ziele: " + meineZiele.values().size()
					+ " Aktuell gesuchte FormularID: " + countForms + " FloorCount: " + floorCount + "\nZellstatus"
					+ status.getValue()[0] + status.getValue()[1] + status.getValue()[2] + status.getValue()[3]
					+ "\nZielkoordinaten" + str);

		}

	}

	public static void rundenAktion() {

		// aktualisieren der Map
		mapAktualisieren();

		// Stehe ich auf einem Finish? (IMPLIZIERT, dass ich alle Formulare habe)
		if (status.getCurrentCellStatus().contains(meinFinish + getFormCount())) {
			ausgabe = "finish";

		}
		// Stehe ich auf einem Formular? (IMPLIZIERT, dass ich dieses Formular als
		// nächstes brauche)
		else if (status.getCurrentCellStatus().contains(meinForm + getCountForms())) {
			ausgabe = "take";
			countForms++;

		}
		// Ich stehe auf einem leeren Feld mit Gegner und muss eine Runde aussetzen
		else if (status.getCurrentCellStatus().equals("FLOOR !")) {
			ausgabe = "position";
		}
		// Ich stehe auf einem leeren Feld und in einiger Entfernung steht mein Gegner
//		else if (status.getCurrentCellStatus().contains("FLOOR !")) {
//			// ausgabe = "position";
//		}

		else {
			// Was ist neben mir?

			for (int i = 0; i < floorCount; i++) {
				// Finishline
				if (finish(i)) {
					break;
				}

				// Formline
				if (form(i)) {
					break;
				}

				// Floorline
				if (floor(i)) {
//					break;
				}

			}

			aktuellerZug.aktualisieren(ausgabe);
		}

//		if (kartenElement.getClass() == Sackgasse.class || meineZiele.containsValue(aktuellerZug.getKoordinaten())) {
//			sackgassen.put(new Sackgasse(), aktuellerZug.getKoordinaten());
//		}
	}

	private static void mapAktualisieren() {
		floorCount = 0;
		for (int i = 0; i < 4; i++) {
			naechsterZug = aktuellerZug;
			Koordinate temp = naechsterZug.aktualisieren2(status.getKey()[i]);
			if (status.getValue()[i].contains("FLOOR")) {

				// Falls noch nicht vorhanden
				if (!meineZiele.containsKey(temp.getName())) {
					map[temp.getX()][temp.getY()] = new Boden(temp);
					meineZiele.put(temp.getName(), new Boden(temp));
					meineZiele2[zielZaehler] = new Boden(temp);
					zielZaehler++;

				}
				floorCount++;
				continue;

			}
			if (status.getValue()[i].contains(meinForm)) {

				if (!meineZiele.containsKey(temp.getName())) {
					String[] partsF = status.getValue()[i].split(" ");
					List<String> list = Arrays.asList(partsF);
					if (list.contains("!")) {
						list.remove(list.size() - 1);
						partsF = list.toArray(partsF);
					}
					String lastDigitF = partsF[partsF.length - 1].trim();
					int lastF = Integer.parseInt(lastDigitF);

					map[temp.getX()][temp.getY()] = new Formular(temp, lastF);
					meineZiele.put(temp.getName(), new Formular(temp, lastF));
					meineZiele2[zielZaehler] = new Formular(temp, lastF);
					zielZaehler++;

				}
				floorCount++;
				continue;

			}

			if (status.getValue()[i].contains(meinFinish)) {
				if (!meineZiele.containsKey(temp.getName())) {
					String[] partsF = status.getValue()[i].split(" ");
					List<String> list = Arrays.asList(partsF);
					if (list.contains("!")) {
						list.remove(list.size() - 1);
						partsF = list.toArray(partsF);
					}
					String lastDigitF = partsF[partsF.length - 1].trim();
					int lastF = Integer.parseInt(lastDigitF);

					setFormCount(lastF);

					map[temp.getX()][temp.getY()] = new Sachbearbeiter(temp);
					meineZiele.put(temp.getName(), new Sachbearbeiter(temp));
					meineZiele2[zielZaehler] = new Sachbearbeiter(temp);
					zielZaehler++;

				}
				floorCount++;

			}

		}

	}

	private static boolean floor(int i) {
		// Stehe ich neben einem Floor?
		if (status.getValue()[i].contains("FLOOR")) {
			ausgabe = status.getKey()[i];
			return true;
		}
//		else {
//			ausgabe = aktuellerZug.getVorzug();
//			kartenElement = new Sackgasse();
//
//		}

// 4.1 4 --> beliebige Richtung wählen, aber niemals rückwärts

// 4.2 3 --> beliebige Richtung wählen, aber niemals rückwärts

// 4.3 2 --> beliebige Richtung wählen, aber niemals rückwärts

// 4.4 1 --> Sackgasse --> Umdrehen --> Sackgasse merken

// 4.4.1 Gibt es mehr als zwei FLOORs? --> Ende der Sackgasse markieren und
// zurück zu 4
		return false;

	}

	private static boolean form(int i) {
		// 3 Stehe ich neben einem Formular?
		if (status.getValue()[i].contains(meinForm + countForms)) {
			// 3.1 Das Formular der nächsten Reihenfolge --> drauf gehen
			ausgabe = status.getKey()[i];

			return true;
		}
		return false;

	}

	private static boolean finish(int i) {
		// 2 Stehe ich neben einem Finish?
		// Formulare vollständig?

		if (countForms == formCount && status.getValue()[i].contains(meinFinish)) {
			// 2.1 Formulare vollständig! --> drauf gehen
			ausgabe = status.getKey()[i];
			return true;
		}
		return false;
	}

	public static int getSizeX() {
		return sizeX;
	}

	public static void setSizeX(int sizeX) {
		NoResponseBot.sizeX = sizeX;
	}

	public static int getSizeY() {
		return sizeY;
	}

	public static void setSizeY(int sizeY) {
		NoResponseBot.sizeY = sizeY;
	}

	public static int getLevel() {
		return level;
	}

	public static void setLevel(int level) {
		NoResponseBot.level = level;
	}

	public static int getPlayerId() {
		return playerId;
	}

	public static void setPlayerId(int playerId) {
		NoResponseBot.playerId = playerId;
	}

	public static String getLastActionsResult() {
		return lastActionsResult;
	}

	public static void setLastActionsResult(String lastActionsResult) {
		NoResponseBot.lastActionsResult = lastActionsResult;
	}

	public static Status getStatus() {
		return status;
	}

	public static void setStatus(Status status) {
		NoResponseBot.status = status;
	}

	public static int getFormCount() {
		return formCount;
	}

	public static void setFormCount(int formCount) {
		NoResponseBot.formCount = formCount;
	}

	public static Map<String, KartenElement> getMeineZiele() {
		return meineZiele;
	}

	public static void setMeineZiele(Map<String, KartenElement> meineZiele) {
		NoResponseBot.meineZiele = meineZiele;
	}

	public static Map<String, Sackgasse> getSackgassen() {
		return sackgassen;
	}

	public static void setSackgassen(Map<String, Sackgasse> sackgassen) {
		NoResponseBot.sackgassen = sackgassen;
	}

	public static KartenElement getKartenElement() {
		return kartenElement;
	}

	public static void setKartenElement(KartenElement kartenElement) {
		NoResponseBot.kartenElement = kartenElement;
	}

	public static String getAusgabe() {
		return ausgabe;
	}

	public static void setAusgabe(String ausgabe) {
		NoResponseBot.ausgabe = ausgabe;
	}

	public static Zug getAktuellerZug() {
		return aktuellerZug;
	}

	public static void setAktuellerZug(Zug aktuellerZug) {
		NoResponseBot.aktuellerZug = aktuellerZug;
	}

	public static Zug getNaechsterZug() {
		return naechsterZug;
	}

	public static void setNaechsterZug(Zug naechsterZug) {
		NoResponseBot.naechsterZug = naechsterZug;
	}

	public static int getCountForms() {
		return countForms;
	}

	public static void setCountForms(int countForms) {
		NoResponseBot.countForms = countForms;
	}

}
