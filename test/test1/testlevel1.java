package test1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import model.Zug;

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
		Zug zug = new Zug(20, 20);

		zug.aktualisieren("go north");
		zug.aktualisieren("go north");
		zug.aktualisieren("go north");
		zug.aktualisieren("go north");
		zug.aktualisieren("go north");

		System.out.println(zug.getVorzug());

		System.out.println(zug.getKoordinaten().getX());
		System.out.println(zug.getKoordinaten().getY());

		String test = "FINISH 1 0";

		String[] parts = test.split(" ");
		String lastDigit = parts[parts.length - 1].trim();
		int last = Integer.parseInt(lastDigit);

		System.out.println(last);

	}

}
