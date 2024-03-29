package KuerzesterWeg;

public class DijkstraMain {
 
	public static void main(String[] args) {
		
		Vertex vertexA = new Vertex("A");
		Vertex vertexB = new Vertex("B");
		Vertex vertexC = new Vertex("C");
		Vertex vertexD = new Vertex("D");
		Vertex vertexE = new Vertex("E");
		
		vertexA.addNeighbour(new Edge(10,vertexA,vertexC));
		vertexA.addNeighbour(new Edge(17,vertexA,vertexB));
		vertexC.addNeighbour(new Edge(5,vertexC,vertexB));
		vertexC.addNeighbour(new Edge(9,vertexC,vertexD));
		vertexC.addNeighbour(new Edge(11,vertexC,vertexE));
		vertexB.addNeighbour(new Edge(1,vertexB,vertexD));
		vertexD.addNeighbour(new Edge(6,vertexD,vertexE));
	
		DijkstraShortestPath shortestPath = new DijkstraShortestPath();
		shortestPath.computeShortestPaths(vertexA);
		
		System.out.println("======================================");
		System.out.println("Berechnung kleinste Distanz");
		System.out.println("======================================");
		
		System.out.println("Kleinste Distanz von A zu B: "+vertexB.getDistance());
		System.out.println("Kleinste Distanz von A zu C: "+vertexC.getDistance());
		System.out.println("Kleinste Distanz von A zu D: "+vertexD.getDistance());
		System.out.println("Kleinste Distanz von A zu E: "+vertexE.getDistance());
		
		System.out.println("=====================	=================");
		System.out.println("Berechnung Wegdistanz");
		System.out.println("======================================");
		
		System.out.println("Kuerzester Weg von A zu B: "+shortestPath.getShortestPathTo(vertexB));
		System.out.println("Kuerzester Weg von A zu C: "+shortestPath.getShortestPathTo(vertexC));
		System.out.println("Kuerzester Weg von A zu D: "+shortestPath.getShortestPathTo(vertexD));
		System.out.println("Kuerzester Weg von A zu E: "+shortestPath.getShortestPathTo(vertexE));
		
		
	}
}