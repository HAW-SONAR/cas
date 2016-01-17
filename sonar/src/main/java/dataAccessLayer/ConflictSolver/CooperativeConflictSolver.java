package dataAccessLayer.ConflictSolver;

import businessLogicLayer.sonar.IOpaBlackBoard;
import dataAccessLayer.ChoiceEckiges;
import dataAccessLayer.agents.IOpa;
import dataAccessLayer.tasks.IEckiges;
import dataAccessLayer.tasks.Operation;
import dataAccessLayer.tasks.Round;

import java.awt.*;
import java.util.List;

/**
 * Created by Daniel Hofmeister on 16.01.2016.
 */
public class CooperativeConflictSolver implements IConflictSolver {

  private IOpaBlackBoard opaBlackBoard;
  private IOpa ownerOpa;

  public CooperativeConflictSolver(IOpaBlackBoard opaBlackBoard, IOpa opa) {
    this.opaBlackBoard = opaBlackBoard;
    this.ownerOpa = opa;
  }

  @Override public ChoiceEckiges solveConfict(List<IEckiges> eckige) {
    System.out.println("Conflict: " + eckige);
    int lowestCost = Integer.MAX_VALUE;
    IEckiges cheapestOption = null;
    int actualCost;
    for (IEckiges e : eckige) {
      actualCost = getCostForEckiges(e);
      if (actualCost<lowestCost) {
        //System.out.println("Choosing new Path " + e + " with cost " + actualCost + "\n over "
        //    + cheapestOption + " with cost " + lowestCost);
        cheapestOption = e;
        lowestCost = actualCost;
      }
    }
    ChoiceEckiges result = new ChoiceEckiges(cheapestOption, lowestCost);
    return result;
  }

  private int getCostForEckiges(IEckiges eckiges) {
    int result = ownerOpa.getEckigesOffer(eckiges);
    if (eckiges.getType().equals(Operation.DELEG)) {
      IOpa targetOpa = opaBlackBoard.getOpaById(eckiges.getOutput().get(0).getOpaName());
      result += targetOpa.getRoundOffer(eckiges.getOutput().get(0));
    } else if (eckiges.getType().equals(Operation.EXEC)) {

    } else {
      for (Round r : eckiges.getOutput()) {
        for (IEckiges e : ownerOpa.getTasks()) {
          if (e.getInput().equals(r)) {
            result += getCostForEckiges(e);
          }
        }
      }
    }
    return result;
  }
}
