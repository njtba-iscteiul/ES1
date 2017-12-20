package antiSpamFilter;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import funcionalities.ButtonAction;

public class AntiSpamFilterProblem extends AbstractDoubleProblem {

	private ButtonAction ba;
	
	  public AntiSpamFilterProblem() {
	    // 10 variables (anti-spam filter rules) by default 
	    this(335);
	  }

	  public AntiSpamFilterProblem(Integer numberOfVariables) {
	    setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(2);
	    setName("AntiSpamFilterProblem");

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(-5.0);
	      upperLimit.add(5.0);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	  }

	  public void evaluate(DoubleSolution solution){
//	    double aux, xi, xj;
//	    double[] fx = new double[getNumberOfObjectives()];
	    double[] x = new double[getNumberOfVariables()];
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      x[i] = solution.getVariableValue(i) ;
	    }

//	    fx[0] = 0.0;
//	    for (int var = 0; var < solution.getNumberOfVariables() - 1; var++) {
//		  fx[0] += Math.abs(x[0]); // Example for testing
//	    }
//	    
//	    fx[1] = 0.0;
//	    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
//	    	fx[1] += Math.abs(x[1]); // Example for testing
//	    }
	    
	    System.out.println();
	    ba.calculateFP(x);
	    ba.calculateFN(x);
	    int fp = ba.getCounterFP();
	    int fn = ba.getCounterFN();
	     
	    System.out.println("FP: " + fp + " - FN: " + fn );

	    solution.setObjective(0, fp);
	    solution.setObjective(1, fn);
	  }
}