package funcionalities;

public class Rule {
	
	private String name;
	private double weight;
	
	public Rule(String name, double weight) {
		this.name = name;
		this.weight = weight;
	}
	
	public String getName() {
		
		return name;
	}
	
	public double getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		return name + " - " + weight;
	}

}
