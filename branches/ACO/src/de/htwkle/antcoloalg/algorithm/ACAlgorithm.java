package de.htwkle.antcoloalg.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import de.htwkle.antcoloalg.algorithm.ants.AntComperator;
import de.htwkle.antcoloalg.tsp.Graph;
import de.htwkle.antcoloalg.tsp.Vertex;

public class ACAlgorithm {

	/**
	 * The end condition for the Alg
	 */
	private static final int END = 40;

	/**
	 * The Ants which are running
	 */
	private static final int ANTS = 10;

	/**
	 * defines the propability the ant choose the best way
	 */
	private static final double EXPLORATIONS_CONTROLLER = 0.5;

	/**
	 * the weight the distance is giving
	 */
	private static final int DISTANCE_WEIGHT = 3;

	/**
	 * the grade the pheromon evaporates over time
	 */
	private static final double EVAPORATION_RATE = 0.8;

	private static final double PHEROMONE_STRENGTH = 3.0;

	private Random rndm = new Random();

	private Graph g;

	private List<Ant> ants = new ArrayList<Ant>();

	public ACAlgorithm() {

	}

	public ACAlgorithm(Graph g) {
		this.g = g;
	}

	public void startAlg(Graph g) {
		this.g = g;
		startAlg();
	}

	public void startAlg() {
		long start = System.currentTimeMillis();

		int round = 0;

		firstRandomTour();

		// startTour();

		System.out.println(System.currentTimeMillis() - start + " ms");
		// printSelectionProbabilityMatrix();
		// printPheromoneMatrix();
		Collections.sort(ants, new AntComperator());
		int i = 0;
		for (Ant a : ants) {
			System.out.println(i + ": " + a.toString());
			i++;
			if (i > 20) {
				break;
			}
		}

	}

	/**
	 * @return
	 */
	private void startTour() {
		int round;
		for (round = 0; round < END; round++) {
			System.out.println(round);

			// long substart = System.currentTimeMillis();
			// calculateSelectionProbabilityForEachEdge();
			this.ants.addAll(runAnts());
			// updatePheromonMatrix(ants);

			// printPheromoneMatrix();

		}
	}

	/**
	 * @param ants
	 */
	private List<Ant> runAnts() {
		List<Ant> ants = new ArrayList<Ant>();
		for (int i = 0; i < ANTS; i++) {
			final Ant ant = oneAntTour();

			ants.add(ant);
		}
		return ants;
	}

	/**
	 * @return
	 */
	private Ant oneAntTour() {
		Vertex choose = null;
		final Ant ant = new Ant(); // creates a new ant
		final Graph g = (Graph) this.g.clone();
		double distance;

		ant.add(g.get(0));
		ant.setCurrent(g.get(0));
		g.remove(ant.getCurrent());
		// let the ant choose and walk
		for (int j = 1; j < g.getVerticesSize(); j++) {
			choose = g.get(rndm.nextInt(g.size()));
			// checks the ant was on the vertex
			if (!ant.contains(choose)) {
				distance = ant.getCurrent().getDistance(choose);

				ant.addDistance(distance);
				ant.setCurrent(choose);

				// at the and we need to add the way
				ant.add(choose);
				g.remove(choose);
			} else {
				try {
					throw new Exception(
							"there is a vertex in the Graph, but the vertex hasnt to be there");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		ant.add(this.g.get(0)); // adds the first vertex to the way\
		distance = ant.getCurrent().getDistance(this.g.get(0));

		ant.addDistance(distance);

		return ant;
	}

	private void firstRandomTour() {
		System.out.println("Start first Random Tour");
		List<Ant> ants = new ArrayList<Ant>();

		for (int i = 0; i < ANTS; i++) {
			System.out.println("Ant " + i);
			ants.add(oneAntTour());
		}

		for (Ant a : ants) {
			System.out.println(a.toString());
		}
		 updatePheromonMatrix(ants);

		// printPheromoneMatrix();
	}

	//
	// /**
	// * choose the vertex with the alg in Evo Alg [Weicker] page 173 formula
	// 4.1
	// *
	// * @return the choosen vertex
	// */
	// private Vertex vertexChooser(Ant ant) {
	// double selectionProbability = -1.0;
	// Vertex target = null;
	// // System.out.print("current: " + ant.getCurrent().name);
	// for (int i = 0; i < ant.getCurrent().getAdjanciesSize(); i++) {
	// Edge e = ant.getCurrent().adjacency.get(rndm.nextInt(ant
	// .getCurrent().getAdjanciesSize()));
	// if (!ant.contains(e.getTarget())) {
	// // System.out.print(" | possible: " + e.getTarget().name);
	// if (e.getSelectionProbability() >= selectionProbability) {
	// selectionProbability = e.getSelectionProbability();
	// target = e.getTarget();
	// }
	//
	// } else {
	// i--;
	// }
	// }
	// // System.out.println(" Choosed: " + target.name );
	// return target;
	// }

	/**
	 * sets the pheromon on the edges
	 */
	private void updatePheromonMatrix(List<Ant> ants) {

		for (Ant ant : ants) {
			Vertex from = null;
			Vertex to = null;
			for (Vertex v : ant) {
				if (from != null && to == null) {
					to = v;
					try {
						g.setPheromone(PHEROMONE_STRENGTH, from, to);
					} catch (Exception e) {
						e.printStackTrace();
					}
					from = to;
					to = null;
				}
				if (from == null) {
					from = v;

				}
			}
		}
		// for(int i=0;i<this.g.size();i++){
		// for (int j = 0; j < this.g.size(); j++) {
		// System.out.print(i +"->"+ j + g.getPheromoneMatrix()[i][j] + " ");
		// }
		// System.out.println();
		// }
	}

	private Vertex maxVertexChooser(Vertex current) {
		return null;
	}

	// /**
	// * this calculates the selection probability for each edge of one Vertex
	// by <br>
	// * the formula 4.1 in Weicker 193
	// */
	// public void calculateSelectionProbabilityForEachEdge() {
	// for (Vertex from : g) {
	// for (Vertex to : g) {
	// if (!to.equals(from))
	// from.setSelectionProbability(to, DISTANCE_WEIGHT);
	// }
	// }
	// }

	// public void printPheromoneMatrix() {
	// DecimalFormat format = new DecimalFormat("#.###");
	// for (Vertex x : g) {
	//
	// for (Vertex in : g) {
	// if (!x.equals(in)) {
	// System.out
	// .print("|" + x.name + "->" + in.name + " phPower: "
	// + x.getPheromoneByTarget(in) + "\t|");
	// }
	// }
	// System.out.println();
	// }
	// }

	// public void printSelectionProbabilityMatrix() {
	//
	// for (Vertex x : g) {
	//
	// for (Vertex in : g) {
	// if (!x.equals(in)) {
	// System.out.print("|" + x.name + "->" + in.name
	// + " pheromonePower: "
	// + x.getSelectionProbability(in) + "\t|");
	// }
	// }
	// System.out.println("| ");
	// }
	// }
}
