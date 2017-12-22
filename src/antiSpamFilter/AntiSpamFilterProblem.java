package antiSpamFilter;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import funcionalities.ButtonAction;

public class AntiSpamFilterProblem extends AbstractDoubleProblem {

	private ButtonAction ba;
	
	
	  public AntiSpamFilterProblem(ButtonAction ba) {
		  // 10 variables (anti-spam filter rules) by default 
		  this(335);
		  this.ba = ba;
	   
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

	    double[] x = new double[getNumberOfVariables()];
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      x[i] = solution.getVariableValue(i) ;
	    }
	    
	    ba.calculateFP(x);
	    ba.calculateFN(x);
	    int fp = ba.getCounterFP();
	    int fn = ba.getCounterFN();
	    
	    solution.setObjective(0, fp);
	    solution.setObjective(1, fn);
	  }
}