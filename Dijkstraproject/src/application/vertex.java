package application;

import java.util.LinkedList;

// Represents a vertex in a graph
public class vertex {
	Capitals capital; // The capital city associated with this vertex
	vertex previous; // Reference to the previous vertex in a path
	int num; // Unique identifier for the vertex
	double distance = Integer.MAX_VALUE; // Distance from the source vertex in shortest path calculations
	boolean visited; // Indicates whether the vertex has been visited during traversal
	LinkedList<edges> e = new LinkedList<edges>(); // List of edges connected to this vertex

	// Constructor
	public vertex(Capitals capital, int number) {
		super();
		this.capital = capital;
		this.num = number;
	}

	// Getters and setters
	public Capitals getCapital() {
		return capital;
	}

	public void setCapital(Capitals capital) {
		this.capital = capital;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public LinkedList<edges> getE() {
		return e;
	}

	public void setE(LinkedList<edges> e) {
		this.e = e;
	}

	// Method to check if an edge exists between this vertex and a destination
	public boolean FindEdge(String ss) {
		for (int i = 0; i < e.size(); i++) {
			if (e.get(i).getD().getCapital().getName().compareToIgnoreCase(ss) == 0)
				return true;
		}
		return false;
	}

	// Method to generate a string representation of the vertex
	public String ttoString() {
		String r = capital.getName() + ":";
		for (int i = 0; i < e.size(); i++) {
			r = r + e.get(i).desination.capital.getName() + ",";
		}
		return r;
	}
}
