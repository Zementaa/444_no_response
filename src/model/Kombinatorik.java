package model;

import java.util.Random;

public class Kombinatorik {
	private final String norden = "norden";
	private final String sueden = "sueden";
	private final String westen = "westen";
	private final String osten = "osten";
	private final String gehNachNorden = "go north";
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
		// in verschiedene F�lle

		int nordenZahl = karte[startX][startY - 1].getExplorationsZahl();
		int ostenZahl = karte[startX + 1][startY].getExplorationsZahl();
		int suedenZahl = karte[startX][startY + 1].getExplorationsZahl();
		int westenZahl = karte[startX - 1][startY].getExplorationsZahl();

		String[] einsZweiDreiVier = { gehNachNorden, gehNachOsten, gehNachSueden, gehNachWesten };

		int zufallsZahl4 = zufall.nextInt(3);
		int zufallsZahl3 = zufall.nextInt(2);

		// 1 M�glichkeit: Norden, Osten, S�den, Westen

		// Jede Zahl gleich
		if (nordenZahl == ostenZahl && nordenZahl == suedenZahl && nordenZahl == westenZahl) {

			ausgabe = einsZweiDreiVier[zufallsZahl4];
		} else
		// Eine gr��te Zahl

		// Norden am gr��ten
		if (nordenZahl > ostenZahl && nordenZahl > suedenZahl && nordenZahl > westenZahl) {
			ausgabe = gehNachNorden;
		} else

		// Osten am gr��ten
		if (ostenZahl > nordenZahl && ostenZahl > suedenZahl && ostenZahl > westenZahl) {
			ausgabe = gehNachOsten;
		} else

		// S�den am gr��ten
		if (suedenZahl > nordenZahl && suedenZahl > ostenZahl && suedenZahl > westenZahl) {
			ausgabe = gehNachSueden;
		} else

		// Westen am gr��ten
		if (westenZahl > nordenZahl && westenZahl > suedenZahl && westenZahl > ostenZahl) {
			ausgabe = gehNachWesten;
		} else

		// Zwei gr��te Zahlen
		// TODO �berpr�fen: Was wenn er in einem "Block" gefangen ist weil dieser nur
		// Umfelder mit der selben zahl hat
		// Letzte zehn IDs mitschreiben --> 3 Wiederholungen
		// �berlegung: Warteschlange mit 5 Feldern, nach zwei Wdh. verlassen
		// Norden und Osten die zwei gr��ten
		if (nordenZahl == ostenZahl && nordenZahl > suedenZahl && nordenZahl > westenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachOsten;
			} else
				ausgabe = gehNachNorden;
		} else

		// Norden und Westen die zwei gr��ten
		if (nordenZahl == westenZahl && nordenZahl > suedenZahl && nordenZahl > ostenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachWesten;
			} else
				ausgabe = gehNachNorden;
		} else

		// Norden und S�den die zwei gr��ten
		if (nordenZahl == suedenZahl && nordenZahl > ostenZahl && nordenZahl > westenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachSueden;
			} else
				ausgabe = gehNachNorden;
		} else

		// Osten und S�den die zwei gr��ten
		if (suedenZahl == ostenZahl && suedenZahl > nordenZahl && suedenZahl > westenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachOsten;
			} else
				ausgabe = gehNachSueden;
		} else
		// Osten und Westen die zwei gr��ten
		if (westenZahl == ostenZahl && westenZahl > suedenZahl && westenZahl > nordenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachOsten;
			} else
				ausgabe = gehNachWesten;
		} else

		// S�den und Westen die zwei gr��ten
		if (suedenZahl == westenZahl && suedenZahl > nordenZahl && suedenZahl > ostenZahl) {
			if (zufallsZahl4 % 2 == 0) {
				ausgabe = gehNachSueden;
			} else
				ausgabe = gehNachWesten;
		} else

		// Drei gr��te Zahlen - eine kleiner und alle anderen gleich
		// Norden am kleinsten
		// TODO �berpr�fen

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
				ausgabe = gehNachNorden;
			} else
				ausgabe = gehNachWesten;
		} else

		// S�den am kleinsten
		if (suedenZahl < nordenZahl && nordenZahl == ostenZahl && nordenZahl == westenZahl) {
			if (zufallsZahl3 == 0) {
				ausgabe = gehNachNorden;
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
				ausgabe = gehNachNorden;
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

		// 4 M�glichkeiten

		// Norden Osten S�den
		if (lastWordEins.equals(norden) && lastWordZwei.equals(osten) && lastWordDrei.equals(sueden)) {
			// Gleiche Explorationszahl
			if (nordenZahl == ostenZahl && nordenZahl == suedenZahl) {
				if (zufallsZahl3 == 0) {
					ausgabe = gehNachSueden;
				} else if (zufallsZahl3 == 1) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachNorden;
			} else
			// Norden am gr��ten
			if (nordenZahl > ostenZahl && nordenZahl > suedenZahl) {

				ausgabe = gehNachNorden;
			} else
			// Osten am gr��ten
			if (ostenZahl > nordenZahl && ostenZahl > suedenZahl) {
				ausgabe = gehNachOsten;
			} else
			// S�den am gr��ten
			if (suedenZahl > ostenZahl && suedenZahl > nordenZahl) {
				ausgabe = gehNachSueden;
			} else
			// Norden und Osten gleich aber gr��er als S�den
			if (nordenZahl == ostenZahl && nordenZahl > suedenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachNorden;
			} else
			// Norden und S�den gleich aber gr��er als Osten
			if (nordenZahl == suedenZahl && nordenZahl > ostenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachSueden;
				} else
					ausgabe = gehNachNorden;
			} else
			// S�den und Osten gleich aber gr��er als Norden
			if (suedenZahl > ostenZahl && ostenZahl > nordenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachSueden;
			}
			return ausgabe;
		}

		// TODO Methode mit sechs �bergabeparameter
		// Norden Osten Westen
		if (lastWordEins.equals(norden) && lastWordZwei.equals(osten) && lastWordDrei.equals(westen)) {
			// Gleiche Explorationszahl
			if (nordenZahl == ostenZahl && nordenZahl == westenZahl) {
				if (zufallsZahl3 == 0) {
					ausgabe = gehNachWesten;
				} else if (zufallsZahl3 == 1) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachNorden;
			} else
			// Norden am gr��ten
			if (nordenZahl > ostenZahl && nordenZahl > westenZahl) {
				ausgabe = gehNachNorden;
			} else
			// Osten am gr��ten
			if (ostenZahl > nordenZahl && ostenZahl > westenZahl) {
				ausgabe = gehNachOsten;
			} else
			// Westen am gr��ten
			if (westenZahl > ostenZahl && westenZahl > nordenZahl) {
				ausgabe = gehNachWesten;
			} else
			// Norden und Osten gleich aber gr��er als Westen
			if (nordenZahl == ostenZahl && nordenZahl > westenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachNorden;
			} else
			// Norden und Westen gleich aber gr��er als Osten
			if (nordenZahl == westenZahl && nordenZahl > ostenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachWesten;
				} else
					ausgabe = gehNachNorden;
			} else {
				// Westen und Osten gleich aber gr��er als Norden

				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachWesten;
			}
			return ausgabe;
		}

		// Norden S�den Westen
		if (lastWordEins.equals(norden) && lastWordZwei.equals(sueden) && lastWordDrei.equals(westen)) {
			// Gleiche Explorationszahl
			if (nordenZahl == suedenZahl && nordenZahl == westenZahl) {
				if (zufallsZahl3 == 0) {
					ausgabe = gehNachWesten;
				} else if (zufallsZahl3 == 1) {
					ausgabe = gehNachNorden;
				} else
					ausgabe = gehNachSueden;
			} else
			// Norden am gr��ten
			if (nordenZahl > suedenZahl && nordenZahl > westenZahl) {
				ausgabe = gehNachNorden;
			} else
			// S�den am gr��ten
			if (suedenZahl > nordenZahl && suedenZahl > westenZahl) {
				ausgabe = gehNachSueden;
			} else
			// Westen am gr��ten
			if (westenZahl > suedenZahl && westenZahl > nordenZahl) {
				ausgabe = gehNachWesten;
			} else
			// Norden und S�den gleich aber gr��er als Westen
			if (nordenZahl == suedenZahl && nordenZahl > westenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachSueden;
				} else
					ausgabe = gehNachNorden;
			} else
			// Norden und Westen gleich aber gr��er als S�den
			if (nordenZahl == westenZahl && nordenZahl > suedenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachWesten;
				} else
					ausgabe = gehNachNorden;
			} else
			// Westen und S�den gleich aber gr��er als Norden
			if (westenZahl == suedenZahl && suedenZahl > nordenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachSueden;
				} else
					ausgabe = gehNachWesten;
			}
			return ausgabe;
		}

		// Osten S�den Westen
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
			// Osten am gr��ten
			if (ostenZahl > suedenZahl && ostenZahl > westenZahl) {
				ausgabe = gehNachOsten;
			} else
			// S�den am gr��ten
			if (suedenZahl > ostenZahl && suedenZahl > westenZahl) {
				ausgabe = gehNachSueden;
			} else
			// Westen am gr��ten
			if (westenZahl > suedenZahl && westenZahl > ostenZahl) {
				ausgabe = gehNachWesten;
			} else
			// Osten und S�den gleich aber gr��er als Westen
			if (ostenZahl == suedenZahl && ostenZahl > westenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachSueden;
				} else
					ausgabe = gehNachOsten;
			} else
			// Osten und Westen gleich aber gr��er als S�den
			if (ostenZahl == westenZahl && ostenZahl > suedenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachWesten;
				} else
					ausgabe = gehNachOsten;
			} else
			// Westen und S�den gleich aber gr��er als Osten
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

		// 6 M�glichkeiten
		// Norden und Osten
		if (lastWordEins.equals(norden) && lastWordZwei.equals(osten)) {
			if (nordenZahl == ostenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachOsten;
				} else
					ausgabe = gehNachNorden;
			}
			if (nordenZahl > ostenZahl) {
				ausgabe = gehNachNorden;
			}
			if (ostenZahl > nordenZahl) {
				ausgabe = gehNachOsten;
			}
		}

		// Norden und S�den
		if (lastWordEins.equals(norden) && lastWordZwei.equals(sueden)) {
			if (nordenZahl == suedenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachSueden;
				} else
					ausgabe = gehNachNorden;
			}
			if (nordenZahl > suedenZahl) {
				ausgabe = gehNachNorden;
			}
			if (suedenZahl > nordenZahl) {
				ausgabe = gehNachSueden;
			}
		}

		// Norden und Westen
		if (lastWordEins.equals(norden) && lastWordZwei.equals(westen)) {
			if (nordenZahl == westenZahl) {
				if (zufallsZahl2 == 0) {
					ausgabe = gehNachWesten;
				} else
					ausgabe = gehNachNorden;
			}
			if (nordenZahl > westenZahl) {
				ausgabe = gehNachNorden;
			}
			if (westenZahl > nordenZahl) {
				ausgabe = gehNachWesten;
			}
		}

		// Osten und S�den
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

		// Nur eine M�glichkeit
		String[] partsF = eins.split(" ");
		String lastWord = partsF[partsF.length - 1].trim();
		switch (lastWord) {
		case norden:
			ausgabe = gehNachNorden;
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
