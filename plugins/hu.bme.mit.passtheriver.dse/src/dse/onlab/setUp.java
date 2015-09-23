package dse.onlab;

import java.util.Collection;

import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.viatra.dse.api.DSETransformationRule;
import org.eclipse.viatra.dse.api.DesignSpaceExplorer;
import org.eclipse.viatra.dse.api.Solution;
import org.eclipse.viatra.dse.api.Strategies;
import org.eclipse.viatra.dse.api.strategy.impl.FixedPriorityStrategy;
import org.eclipse.viatra.dse.beestrategy.StrategyCombiner;
import org.eclipse.viatra.dse.beestrategy.createbeestrategy.CreateBeeWithDFS;
import org.eclipse.viatra.dse.beestrategy.createbeestrategy.CreateBeeWithHillClimbing;
import org.eclipse.viatra.dse.beestrategy.createbeestrategy.DFSWithHillClimbingMiniStrategy;
import org.eclipse.viatra.dse.objectives.IObjective;
import org.eclipse.viatra.dse.objectives.impl.ModelQueriesGlobalConstraint;
import org.eclipse.viatra.dse.objectives.impl.ModelQueriesHardObjective;
import org.eclipse.viatra.dse.objectives.impl.TrajectoryCostSoftObjective;
import org.eclipse.viatra.dse.objectives.impl.WeightedQueriesSoftObjective;
import org.eclipse.viatra.dse.solutionstore.SimpleSolutionStore;

import constraints.util.DangerousPassangersAtOnePlaceQuerySpecification;
import constraints.util.NullPassangerAtWrongPlaceQuerySpecification;
import constraints.util.PassangerOnLandQuerySpecification;
import constraints.util.PassangerOnVechichleToTargetQuerySpecification;
import dse.problems.StartProblem;
import dse.transformation.GetIntoBoat;
import dse.transformation.GetOutOfBoat;
import dse.transformation.GoToTheOtherPart;
import simulators.BeeStrategySimulator;

public class setUp {
	 protected DesignSpaceExplorer dse;
	 protected onlab.PassTheRiver model;
	 //dseDSETransformationRule
	 protected DSETransformationRule<?, ?> getInTheVehichle;
	 protected DSETransformationRule<?, ?> getOutOfVehichle;
	 protected DSETransformationRule<?, ?> switchLands;
	 FixedPriorityStrategy fps;
	 StrategyCombiner bs;

	 
	public void setUpProject() throws IncQueryException{
		
		 dse = new DesignSpaceExplorer();
		 model = new StartProblem().getpt().getPassTheRiver();
		 System.out.println("model"+model.toString());
		 dse.setInitialModel(model);
		 
		 this.getInTheVehichle = new GetIntoBoat().getinto();
		 this.getOutOfVehichle = new GetOutOfBoat().getinto();
		 this.switchLands = new GoToTheOtherPart().getinto();
		
		 
		 //set constraints
		 System.out.println("hali");
		 dse.addObjective(new ModelQueriesHardObjective("MyHardObjective")
		    .withConstraint(NullPassangerAtWrongPlaceQuerySpecification.instance()));

		 System.out.println("hali2");
		 dse.setStateCoderFactory(new OwnSerializerFactory());
		 System.out.println("hali3");
		 dse.addTransformationRule(this.getInTheVehichle);
		 dse.addTransformationRule(this.getOutOfVehichle);
		 dse.addTransformationRule(this.switchLands);
		 System.out.println("hali4");
		 dse.addGlobalConstraint(new ModelQueriesGlobalConstraint()
		  .withConstraint(DangerousPassangersAtOnePlaceQuerySpecification.instance()));
		 System.out.println("hali5");
		 IObjective standingAtTarget = new WeightedQueriesSoftObjective()
				 .withConstraint(PassangerOnLandQuerySpecification.instance(), 2)
				 .withConstraint(PassangerOnVechichleToTargetQuerySpecification.instance(), 1);
		 dse.addObjective(standingAtTarget);
		 
		 dse.setMaxNumberOfThreads(2);
		 dse.setSolutionStore(new SimpleSolutionStore(1));
		 System.out.println("hali");
		// bs = new BeeStrategy2();
//		 bs = new BeeStrategy(3);
//		 bs.setEliteBeesNum(1);
//		 bs.setEliteSitesNum(1);		
//		 bs.setSitesnum(1);
//		 bs.setPatchSize(2);
//		 CreateBeeWithDFS df = new CreateBeeWithDFS();
//		 CreateBeeWithHillClimbing cbwhc = new CreateBeeWithHillClimbing();
//		 bs.setNeighbourBeeCreator(df);
//		 bs.setRandomBeeCreator(cbwhc);
	
		  fps = new FixedPriorityStrategy()
         .withRulePriority(this.getInTheVehichle, 4)
         .withRulePriority(this.getOutOfVehichle, 4)
         .withRulePriority(this.switchLands, 4);
		  bs = new StrategyCombiner();
			BeeStrategySimulator bss = new BeeStrategySimulator(bs);
		//	SimulatedAnnealingMiniStrategy ministrategy2 = new SimulatedAnnealingMiniStrategy(bs);
			bss.init(new DFSWithHillClimbingMiniStrategy(bs), new  CreateBeeWithDFS(bs));
	
		 

	}
	public void startProject(){
		//bs.explore();
		dse.startExploration(bs);
	//	dse.startExploration(Strategies.createDFSStrategy(6));
		//dse.startExploration(fps);
		//dse.startExploration(bs);
	}
	
	public void writeOutProject() throws IncQueryException{
		 Collection<Solution> solutions = dse.getSolutions();
		 System.out.println(solutions.size());
	}
}
