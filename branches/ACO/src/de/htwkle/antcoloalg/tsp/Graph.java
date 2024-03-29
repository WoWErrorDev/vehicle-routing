package de.htwkle.antcoloalg.tsp;

import java.util.ArrayList;

public class Graph extends ArrayList<Vertex> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String egdeWeightType;
	private String name;
	private int verticesSize;

	/**
	 * @return the egdeWeightType
	 */
	public String getEgdeWeightType() {
		return egdeWeightType;
	}

	/**
	 * @param pheromoneMatrix
	 *            the pheromoneMatrix to set
	 * @throws Exception
	 */
	public void setPheromone(double pheromone, Vertex from, Vertex to)
			throws Exception {
		System.out.println("init phem matrix");
		if (null == from.getEdgeByTarget(to)) {
			from.addAdjancy(to);
			to.addAdjancy(from);
		}

		from.getEdgeByTarget(to).addPheromone(pheromone);
		to.getEdgeByTarget(from).addPheromone(pheromone);

		System.out.println("init phem matrix done");
		if (from.id == to.id) {
			throw new Exception("Same Vertices " + from.getId() + " to "
					+ to.getId());

		}

	}

	/**
	 * @param egdeWeightType
	 *            the egdeWeightType to set
	 */
	public void setEgdeWeightType(String egdeWeightType) {
		this.egdeWeightType = egdeWeightType;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the verticesSize
	 */
	public int getVerticesSize() {
		return verticesSize;
	}

	/**
	 * @param verticesSize
	 *            the verticesSize to set
	 */
	public void setVerticesSize(int verticesSize) {
		this.verticesSize = verticesSize;
	}

}
