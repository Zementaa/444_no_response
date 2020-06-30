package test1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import model.MinimalBot;

class testlevel1 {

	@Test
	void test() {
		File file = new File(
				"C:\\Users\\Zementa\\Documents\\H2\\m08_objektorientierte_programmierung\\coding_challenge\\444_no_response\\code\\test.txt");
		Scanner input;

		try {
			input = new Scanner(file);
			// System.out.println(input.next());
			System.out.println(MinimalBot.init(input).nextLine());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
