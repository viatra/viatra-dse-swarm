package dse.onlab;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.incquery.runtime.exception.IncQueryException;
import org.eclipse.viatra.dse.api.Solution;
import org.eclipse.viatra.dse.api.SolutionTrajectory;
import org.eclipse.viatra.dse.api.strategy.impl.DepthFirstStrategy;
import org.eclipse.viatra.dse.beestrategy.BeeStrategyWorkerThread;
import org.eclipse.viatra.dse.beestrategy.createbeestrategy.CreateBeeWithDFS;
import org.junit.Test;

import com.google.common.eventbus.DeadEvent;



public class runDse {
	
	@Test
	public void test(){
		// BasicConfigurator.configure();
	    // Logger.getRootLogger().setLevel(Level.ERROR);
	    // Logger.getLogger(CreateBeeWithDFS.class).setLevel(Level.ALL);
		// Logger.getLogger(CreateBeeWithDFS.class).setLevel(Level.DEBUG);
		// Logger.getLogger(DepthFirstStrategy.class).setLevel(Level.DEBUG);
		// Logger.getLogger(BeeStrategyWorkerThread.class).setLevel(Level.DEBUG);
	     //System.out.println("elkezdve");
		 setUp su = new setUp();
		
		try {
			System.out.println("set");			
			su.setUpProject();
			System.out.println("started");
			su.startProject();
			System.out.println("finished");
			su.writeOutProject();
			System.out.println(su.dse.toStringSolutions());
			//ArrayList<Solution> sols = (ArrayList<Solution>) su.dse.getGlobalContext().getSolutionStore().getSolutions();
			
			//System.out.println("length: "+su.dse.getGlobalContext().getSolutionStore().getSolutions().
		} catch (IncQueryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
