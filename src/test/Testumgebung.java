package test;

import java.util.List;

import model.DijkstraShortestPath;
import model.Edge;
import model.Explorer;
import model.Vertex;

class Testumgebung {

	@org.junit.jupiter.api.Test
	void test() {

		int jetztX = 0;
		int jetztY = 0;
		int sizeX = 2;
		int sizeY = 3;
		int zielX = 1;
		int zielY = 2;
		String startString = "00";
		String zielString = "12";

		Explorer[][] karte = new Explorer[sizeX][sizeY];
		karte[0][0] = new Explorer("00", 1, 1, 0, 0);
		karte[0][1] = new Explorer("01", 1, 1, 0, 1);
		karte[0][2] = new Explorer("02", 1, 1, 0, 2);
		karte[1][0] = new Explorer("10", 1, 1, 1, 0);
		karte[1][1] = new Explorer("11", 1, 1, 1, 1);
		karte[1][2] = new Explorer("12", 1, 1, 1, 2);
		Vertex[] alleKnoten = new Vertex[sizeX * sizeY];
		Vertex startknoten = null;
		Vertex zielknoten = null;
		Vertex gesuchterKnoten = null;
		Vertex gesuchterKnoten2 = null;

		int anzahlknoten = 0;
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {

				System.out.println(anzahlknoten);
				if (jetztX == i && jetztY == j) {

					alleKnoten[anzahlknoten] = new Vertex(i + "" + j);
					startknoten = alleKnoten[anzahlknoten];
					System.out.println("Start " + startknoten.toString());
				} else if (zielX == i && zielY == j) {
					alleKnoten[anzahlknoten] = new Vertex(i + "" + j);
					zielknoten = alleKnoten[anzahlknoten];
					System.out.println("Ziel " + zielknoten.toString());
				} else {
					alleKnoten[anzahlknoten] = new Vertex(i + "" + j);
					System.out.println("Normales Feld ");
				}
				anzahlknoten++;

			}
		}

		anzahlknoten = 0;
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {

				for (Vertex knoten : alleKnoten) {

					// normalerweise
					if (i < sizeX && knoten.getName().equals((i + 1) + "" + j)) {
						gesuchterKnoten = knoten;
					}
					if (j < sizeY && knoten.getName().equals(i + "" + (j + 1))) {
						gesuchterKnoten2 = knoten;
					}

				}

				if (gesuchterKnoten != null) {

					alleKnoten[anzahlknoten].addNeighbour(new Edge(1, alleKnoten[anzahlknoten], gesuchterKnoten));
					System.out.println(
							alleKnoten[anzahlknoten].toString() + " ist Nachbar von " + gesuchterKnoten.toString());
				}
				if (gesuchterKnoten2 != null) {
					alleKnoten[anzahlknoten].addNeighbour(new Edge(1, alleKnoten[anzahlknoten], gesuchterKnoten2));
					System.out.println(
							alleKnoten[anzahlknoten].toString() + " ist Nachbar von " + gesuchterKnoten2.toString());
				}

				// System.out.println(anzahlknoten);
				gesuchterKnoten = null;
				gesuchterKnoten2 = null;
				anzahlknoten++;

			}
		}

		DijkstraShortestPath shortestPath = new DijkstraShortestPath();
		for (Vertex knoten : alleKnoten) {
			if (knoten.getName().equals(startString)) {
				startknoten = knoten;
			}
			if (knoten.getName().equals(zielString)) {
				zielknoten = knoten;
			}
		}
		shortestPath.computeShortestPaths(startknoten);

		System.out.println("======================================");
		System.out.println("Berechnung kleinste Distanz");
		System.out.println("======================================");

		System.out.println("Kleinste Distanz von A zu B: " + zielknoten.getDistance());

		System.out.println("=====================	=================");
		System.out.println("Berechnung Wegdistanz");
		System.out.println("======================================");

		System.out.println(shortestPath.getShortestPathTo(zielknoten));
		List<Vertex> liste = shortestPath.getShortestPathTo(zielknoten);
		System.out.println("Kuerzester Weg von A zu B: " + liste.get(0));
		System.out.println("Kuerzester Weg von A zu B: " + liste.get(1));
		System.out.println(
				"Kuerzester Weg von A zu B: " + liste.get(0).getAdjacenciesList().get(0).getTargetVertex().getName());
		System.out.println(
				"Kuerzester Weg von A zu B: " + liste.get(0).getAdjacenciesList().get(1).getTargetVertex().getName());

	}

}
