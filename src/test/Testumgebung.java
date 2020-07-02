package test;

import model.SchrottBot;

public class Testumgebung {

	public static void main(String[] args) {

		SchrottBot bot = new SchrottBot();

		init(bot);

	}

	public static void init(SchrottBot bot) {

		// 1. Zeile: Maze Infos
		bot.setSizeX(20); // X-Groesse des Spielfeldes (Breite)
		bot.setSizeY(20); // Y-Groesse des Spielfeldes (Hoehe)
		bot.setLevel(1); // Level des Matches

		// 2. Zeile: Player Infos
		bot.setPlayerId(444); // id dieses Players / Bots
		bot.setStartX(0); // X-Koordinate der Startposition dieses Player
		bot.setStartY(0); // Y-Koordinate der Startposition dieses Players

	}

}
