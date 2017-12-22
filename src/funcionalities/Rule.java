package funcionalities;

public class Rule {
	
	private String name;
	private double weight;
	
	/**
	 * Construtor da regra
	 *
	 * @return ....
	 */
	public Rule(String name, double weight) {
		this.name = name;
		this.weight = weight;
	}
	
	/**
	 * Retorna o nome da regra
	 *
	 * @return String
	 */
	public String getName() {
		
		return name;
	}
	
	/**
	 * Retorna o peso da regra
	 *
	 * @return double
	 */
	public double getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return name + " - " + weight;
	}

}
