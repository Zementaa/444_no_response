package model;

import java.util.Random;

public class Kombinatorik {

	// TODO Eingang zu "Höhle" merken
	private static final String NORDEN = "norden";
	private static final String sueden = "sueden";
	private static final String westen = "westen";
	private static final String osten = "osten";
	private static final String GEH_NACH_NORDEN = "go north";
	private final String gehNachSueden = "go south";
	private final String gehNachWesten = "go west";
	private final String gehNachOsten = "go east";
	private String ausgabe = "";
	private int playerId;
	private String meinFinish;
	private String meinForm;
	private Random zufall = new Random();

	public Kombinatorik(int playerId) {
		this.playerId = playerId;
		meinFinish = "FINISH " + playerId;

		meinForm = "FORM " + playerId + " 1";
	}

	public String moeglichkeitenBerechnen(String eins, String zwei, String drei, String vier, Explorer[][] karte,
			int startX, int startY) {
		// Nur eine Reihenfolge wie die Daten hereinkommen, deshalb keine Unterscheidung
		// in verschiedene Fälle

		int nordenZahl = karte[startX][startY - 1].getExplorationsZahl();
		int ostenZahl = karte[startX + 1][startY].getExplorationsZahl();
		int suedenZahl = karte[startX][startY + 1].getExplorationsZahl();
		int westenZahl = karte[startX - 1][startY].getExplorationsZahl();

		String[] einsZweiDreiVier = { GEH_NACH_NORDEN, gehNachOsten, gehNachSueden, gehNachWesten };

		int zufallsZahl4 = zufall.nextInt(3);
		int zufallsZahl3 = zufall.nextInt(2);

		// 1 Möglichkeit: Norden, Osten, Süden, Westen

		// Jede Zahl gleich
		if (nordenZahl == ostenZahl && nordenZahl == suedenZahl && nordenZahl == westenZahl) {

			ausgabe = einsZweiDreiVier[zufallsZahl4];
		} else
		// Eine größte Zahl

		// Norden am größten
		if (nordenZahl > ostenZahl && nordenZahl > suedenZahl && nordenZahl > westenZahl) {
			ausgabe = GEH_NACH_NORDEN;
		} else

		// Osten am größten
		if (ostenZahl > nordenZahl && ostenZahl > suedenZahl && ostenZahl > westenZahl) {
			ausgabe = gehNachOsten;
		} else

		// Süden am größten
		if (suedenZahl > nordenZahl && suedenZahl > ostenZahl && suedenZahl > westenZahl) {
			ausgabe = gehNachSueden;
		} else

		// Westen am größten
		if (westenZahl > nordenZahl && westenZahl > suedenZahl && westenZahl > ostenZahl) {
			ausgabe = gehNachWesten;
		} else

		// Zwei größte Zahlen
		// TODO überprüfen: Was wenn er in einem "Block" gefangen ist weil dieser nur
		// Umfelder mit der selben zahl hat
		// Letzte zehn IDs mitschreiben --> 3 Wiederholungen
		// Überlegung: Warteschlange mit 5 Feldern, nach zwei Wdh. verlassen
		// Norden und Osten die zwei größten
		if (nordenZahl == ostenZahl && nordenZahl > suedenZahl && nordenZahl > westenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachOsten;
			} else
				ausgabe = GEH_NACH_NORDEN;
		} else

		// Norden und Westen die zwei größten
		if (nordenZahl == westenZahl && nordenZahl > suedenZahl && nordenZahl > ostenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachWesten;
			} else
				ausgabe = GEH_NACH_NORDEN;
		} else

		// Norden und Süden die zwei größten
		if (nordenZahl == suedenZahl && nordenZahl > ostenZahl && nordenZahl > westenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachSueden;
			} else
				ausgabe = GEH_NACH_NORDEN;
		} else

		// Osten und Süden die zwei größten
		if (suedenZahl == ostenZahl && suedenZahl > nordenZahl && suedenZahl > westenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachOsten;
			} else
				ausgabe = gehNachSueden;
		} else
		// Osten und Westen die zwei größten
		if (westenZahl == ostenZahl && westenZahl > suedenZahl && westenZahl > nordenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachOsten;
			} else
				ausgabe = gehNachWesten;
		} else

		// Süden und Westen die zwei größten
		if (suedenZahl == westenZahl && suedenZahl > nordenZahl && suedenZahl > ostenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachSueden;
			} else
				ausgabe = gehNachWesten;
		} else

		// Drei größte Zahlen - eine kleiner und alle anderen gleich
		// Norden am kleinsten
		// TODO überprüfen

		if (nordenZahl < ostenZahl && ostenZahl == suedenZahl && ostenZahl == westenZahl) {
			if (zufallsZahl3 == 0) {
				ausgabe = gehNachSueden;
			} else if (zufallsZahl3 == 1) {
				ausgabe = gehNachOsten;
			} else
				ausgabe = gehNachWesten;
		} else

		// Osten am kleinsten
		if (ostenZahl < nordenZahl && nordenZahl == suedenZahl && nordenZahl == westenZahl) {
			if (zufallsZahl3 == 0) {
				ausgabe = gehNachSueden;
			} else if (zufallsZahl3 == 1) {
				ausgabe = GEH_NACH_NORDEN;
			} else
				ausgabe = gehNachWesten;
		} else

		// Süden am kleinsten
		if (suedenZahl < nordenZahl && nordenZahl == ostenZahl && nordenZahl == westenZahl) {
			if (zufallsZahl3 == 0) {
				ausgabe = GEH_NACH_NORDEN;
			} else if (zufallsZahl3 == 1) {
				ausgabe = gehNachOsten;
			} else
				ausgabe = gehNachWesten;
		} else

		// Westen am kleinsten
		if (westenZahl < nordenZahl && nordenZahl == suedenZahl && nordenZahl == ostenZahl) {
			if (zufallsZahl3 == 0) {
				ausgabe = gehNachSueden;
			} else if (zufallsZahl3 == 1) {
				ausgabe = gehNachOsten;
			} else
				ausgabe = GEH_NACH_NORDEN;
		}
		return this.ausgabe;
	}

	public String moeglichkeitenBerechnen(String eins, String zwei, String drei, Explorer[][] karte, int startX,
			int startY) {

		String[] partsE = eins.split(" ");
		String lastWordEins = partsE[partsE.length - 1].trim();

		String[] partsZ = zwei.split(" ");
		String lastWordZwei = partsZ[partsZ.length - 1].trim();

		String[] partsD = drei.split(" ");
		String lastWordDrei = partsD[partsD.length - 1].trim();

		int nordenZahl = karte[startX][startY - 1].getExplorationsZahl();
		int ostenZahl = karte[startX + 1][startY].getExplorationsZahl();
		int suedenZahl = karte[startX][startY + 1].getExplorationsZahl();
		int westenZahl = karte[startX - 1][startY].getExplorationsZahl();

		int zufallsZahl3 = zufall.nextInt(2);
		int zufallsZahl2 = zufall.nextInt(1);
//
//		// Formline
//
//		if (eins.contains(meinForm)) {
//			ausgabe = gehNachNorden;
//		} else if (zwei.contains(meinForm)) {
//			ausgabe = gehNachOsten;
//		} else if (drei.contains(meinForm)) {
//			ausgabe = gehNachSueden;
//		} else
//		// Finishline
//		if (meinFinish.equals(meinForm)) {
//
//			if (eins.contains(meinFinish)) {
//				ausgabe = gehNachNorden;
//			} else if (zwei.contains(meinFinish)) {
//				ausgabe = gehNachOsten;
//			} else if (drei.contains(meinFinish)) {
//				ausgabe = gehNachSueden;
//			}
//		} else

		// 4 Möglichkeiten

		// Norden Osten Süden
		if (lastWordEins.equals(NORDEN) && lastWordZwei.equals(osten) && lastWordDrei.equals(sueden)) {
			// Gleiche Explorationszahl
			if (nordenZahl == ostenZahl && nordenZahl == suedenZahl) {
				if (zufallsZahl3 == 0) {
					ausgabe = gehNachSueden;
				} else if (zufallsZahl3 == 1) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = GEH_NACH_NORDEN;
			} else
			// Norden am größten
			if (nordenZahl > ostenZahl && nordenZahl > suedenZahl) {

				ausgabe = GEH_NACH_NORDEN;
			} else
			// Osten am größten
			if (ostenZahl > nordenZahl && ostenZahl > suedenZahl) {
				ausgabe = gehNachOsten;
			} else
			// Süden am größten
			if (suedenZahl > ostenZahl && suedenZahl > nordenZahl) {
				ausgabe = gehNachSueden;
			} else
			// Norden und Osten gleich aber größer als Süden
			if (nordenZahl == ostenZahl && nordenZahl > suedenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = GEH_NACH_NORDEN;
			} else
			// Norden und Süden gleich aber größer als Osten
			if (nordenZahl == suedenZahl && nordenZahl > ostenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachSueden;
				} else
					ausgabe = GEH_NACH_NORDEN;
			} else
			// Süden und Osten gleich aber größer als Norden
			if (suedenZahl > ostenZahl && ostenZahl > nordenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachSueden;
			}
			return ausgabe;
		}

		// TODO Methode mit sechs Übergabeparameter
		// Norden Osten Westen
		if (lastWordEins.equals(NORDEN) && lastWordZwei.equals(osten) && lastWordDrei.equals(westen)) {
			// Gleiche Explorationszahl
			if (nordenZahl == ostenZahl && nordenZahl == westenZahl) {
				if (zufallsZahl3 == 0) {
					ausgabe = gehNachWesten;
				} else if (zufallsZahl3 == 1) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = GEH_NACH_NORDEN;
			} else
			// Norden am größten
			if (nordenZahl > ostenZahl && nordenZahl > westenZahl) {
				ausgabe = GEH_NACH_NORDEN;
			} else
			// Osten am größten
			if (ostenZahl > nordenZahl && ostenZahl > westenZahl) {
				ausgabe = gehNachOsten;
			} else
			// Westen am größten
			if (westenZahl > ostenZahl && westenZahl > nordenZahl) {
				ausgabe = gehNachWesten;
			} else
			// Norden und Osten gleich aber größer als Westen
			if (nordenZahl == ostenZahl && nordenZahl > westenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = GEH_NACH_NORDEN;
			} else
			// Norden und Westen gleich aber größer als Osten
			if (nordenZahl == westenZahl && nordenZahl > ostenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachWesten;
				} else
					ausgabe = GEH_NACH_NORDEN;
			} else {
				// Westen und Osten gleich aber größer als Norden

				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachWesten;
			}
			return ausgabe;
		}

		// Norden Süden Westen
		if (lastWordEins.equals(NORDEN) && lastWordZwei.equals(sueden) && lastWordDrei.equals(westen)) {
			// Gleiche Explorationszahl
			if (nordenZahl == suedenZahl && nordenZahl == westenZahl) {
				if (zufallsZahl3 == 0) {
					ausgabe = gehNachWesten;
				} else if (zufallsZahl3 == 1) {
					ausgabe = GEH_NACH_NORDEN;
				} else
					ausgabe = gehNachSueden;
			} else
			// Norden am größten
			if (nordenZahl > suedenZahl && nordenZahl > westenZahl) {
				ausgabe = GEH_NACH_NORDEN;
			} else
			// Süden am größten
			if (suedenZahl > nordenZahl && suedenZahl > westenZahl) {
				ausgabe = gehNachSueden;
			} else
			// Westen am größten
			if (westenZahl > suedenZahl && westenZahl > nordenZahl) {
				ausgabe = gehNachWesten;
			} else
			// Norden und Süden gleich aber größer als Westen
			if (nordenZahl == suedenZahl && nordenZahl > westenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachSueden;
				} else
					ausgabe = GEH_NACH_NORDEN;
			} else
			// Norden und Westen gleich aber größer als Süden
			if (nordenZahl == westenZahl && nordenZahl > suedenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachWesten;
				} else
					ausgabe = GEH_NACH_NORDEN;
			} else
			// Westen und Süden gleich aber größer als Norden
			if (westenZahl == suedenZahl && suedenZahl > nordenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachSueden;
				} else
					ausgabe = gehNachWesten;
			}
			return ausgabe;
		}

		// Osten Süden Westen
		if (lastWordEins.equals(osten) && lastWordZwei.equals(sueden) && lastWordDrei.equals(westen)) {
			// Gleiche Explorationszahl
			if (ostenZahl == suedenZahl && ostenZahl == westenZahl) {
				if (zufallsZahl3 == 0) {
					ausgabe = gehNachWesten;
				} else if (zufallsZahl3 == 1) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachSueden;
			} else
			// Osten am größten
			if (ostenZahl > suedenZahl && ostenZahl > westenZahl) {
				ausgabe = gehNachOsten;
			} else
			// Süden am größten
			if (suedenZahl > ostenZahl && suedenZahl > westenZahl) {
				ausgabe = gehNachSueden;
			} else
			// Westen am größten
			if (westenZahl > suedenZahl && westenZahl > ostenZahl) {
				ausgabe = gehNachWesten;
			} else
			// Osten und Süden gleich aber größer als Westen
			if (ostenZahl == suedenZahl && ostenZahl > westenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachSueden;
				} else
					ausgabe = gehNachOsten;
			} else
			// Osten und Westen gleich aber größer als Süden
			if (ostenZahl == westenZahl && ostenZahl > suedenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachWesten;
				} else
					ausgabe = gehNachOsten;
			} else
			// Westen und Süden gleich aber größer als Osten
			if (westenZahl == suedenZahl && suedenZahl > ostenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachSueden;
				} else
					ausgabe = gehNachWesten;
			}
		}

		return ausgabe;
	}

	public String moeglichkeitenBerechnen(String eins, String zwei, Explorer[][] karte, int startX, int startY) {

		String[] partsE = eins.split(" ");
		String lastWordEins = partsE[partsE.length - 1].trim();

		String[] partsZ = zwei.split(" ");
		String lastWordZwei = partsZ[partsZ.length - 1].trim();

		int nordenZahl = karte[startX][startY - 1].getExplorationsZahl();
		int ostenZahl = karte[startX + 1][startY].getExplorationsZahl();
		int suedenZahl = karte[startX][startY + 1].getExplorationsZahl();
		int westenZahl = karte[startX - 1][startY].getExplorationsZahl();

		int zufallsZahl2 = zufall.nextInt(1);

		// 6 Möglichkeiten
		// Norden und Osten
		if (lastWordEins.equals(NORDEN) && lastWordZwei.equals(osten)) {
			if (nordenZahl == ostenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = GEH_NACH_NORDEN;
			}
			if (nordenZahl > ostenZahl) {
				ausgabe = GEH_NACH_NORDEN;
			}
			if (ostenZahl > nordenZahl) {
				ausgabe = gehNachOsten;
			}
		}

		// Norden und Süden
		if (lastWordEins.equals(NORDEN) && lastWordZwei.equals(sueden)) {
			if (nordenZahl == suedenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachSueden;
				} else
					ausgabe = GEH_NACH_NORDEN;
			}
			if (nordenZahl > suedenZahl) {
				ausgabe = GEH_NACH_NORDEN;
			}
			if (suedenZahl > nordenZahl) {
				ausgabe = gehNachSueden;
			}
		}

		// Norden und Westen
		if (lastWordEins.equals(NORDEN) && lastWordZwei.equals(westen)) {
			if (nordenZahl == westenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachWesten;
				} else
					ausgabe = GEH_NACH_NORDEN;
			}
			if (nordenZahl > westenZahl) {
				ausgabe = GEH_NACH_NORDEN;
			}
			if (westenZahl > nordenZahl) {
				ausgabe = gehNachWesten;
			}
		}

		// Osten und Süden
		if (lastWordEins.equals(osten) && lastWordZwei.equals(sueden)) {
			if (ostenZahl == suedenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachSueden;
			}
			if (ostenZahl > suedenZahl) {
				ausgabe = gehNachOsten;
			}
			if (suedenZahl > ostenZahl) {
				ausgabe = gehNachSueden;
			}
		}

		// Osten und Westen
		if (lastWordEins.equals(osten) && lastWordZwei.equals(westen)) {
			if (ostenZahl == westenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachWesten;
			}
			if (ostenZahl > westenZahl) {
				ausgabe = gehNachOsten;
			}
			if (westenZahl > ostenZahl) {
				ausgabe = gehNachWesten;
			}
		}

		// Sueden und Westen
		if (lastWordEins.equals(sueden) && lastWordZwei.equals(westen)) {
			if (suedenZahl == westenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachWesten;
				} else
					ausgabe = gehNachSueden;
			}
			if (suedenZahl > westenZahl) {
				ausgabe = gehNachSueden;
			}
			if (westenZahl > suedenZahl) {
				ausgabe = gehNachWesten;
			}
		}

		return this.ausgabe;

	}

	public String moeglichkeitenBerechnen(String eins) {

		// Nur eine Möglichkeit
		String[] partsF = eins.split(" ");
		String lastWord = partsF[partsF.length - 1].trim();
		switch (lastWord) {
		case NORDEN:
			ausgabe = GEH_NACH_NORDEN;
			break;
		case osten:
			ausgabe = gehNachOsten;
			break;
		case sueden:
			ausgabe = gehNachSueden;
			break;
		default:
			ausgabe = gehNachWesten;

		}
		return ausgabe;
	}

	public String getMeinFinish() {
		return meinFinish;
	}

	public void setMeinFinish(String meinFinish) {
		this.meinFinish = meinFinish;
	}

	public String getMeinForm() {
		return meinForm;
	}

	public void setMeinForm(String meinForm) {
		this.meinForm = meinForm;
	}

}
