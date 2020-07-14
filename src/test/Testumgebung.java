package test;

public class Testumgebung {

	public static void main(String[] args) {
		int formCount = 0;
		String string = "FINISH 1 2 !2";
		String[] partsF = string.split(" ");
//
//		if (partsF.length > 3) {
//			partsF = Arrays.copyOf(partsF, partsF.length - 1);
//		}

		String lastDigitF = partsF[2];
		int lastF = Integer.parseInt(lastDigitF);

		formCount = lastF;
		System.out.println(formCount);
//
	}

}
