package test1;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import model.SchrottBot;

class testlevel1 {

	@Test
	void test() {
		File file = new File(
				"C:\\Users\\Zementa\\Documents\\H2\\m08_objektorientierte_programmierung\\coding_challenge\\444_no_response\\code\\test.txt");
		Scanner input;

		try {
			input = new Scanner(file);
			// System.out.println(input.next());
			System.out.println();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void testZug() {
		// Zug zug = new Zug(20, 20);
//
//		zug.aktualisieren("go north");
//		zug.aktualisieren("go north");
//		zug.aktualisieren("go north");
//		zug.aktualisieren("go north");
//		zug.aktualisieren("go north");
//
//		System.out.println(zug.getVorzug());
//
//		System.out.println(zug.getKoordinaten().getX());
//		System.out.println(zug.getKoordinaten().getY());

		List<Integer> list = Arrays.asList(1, 2, 3, 4);
		SecureRandom random = new SecureRandom();
		Collections.shuffle(list, random);

		System.out.println(list);

		Collections.shuffle(list, random);

		System.out.println(list);

		SchrottBot bot = new SchrottBot();
		bot.naechstenZugAktualisieren("FLOOR", "WALL", "WALL", "WALL");

	}

}
