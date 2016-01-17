package dataAccessLayer.ConflictSolver;

import dataAccessLayer.ChoiceEckiges;
import dataAccessLayer.tasks.IEckiges;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 16.01.2016.
 */
public interface IConflictSolver {

  /**
   * Solves a Conflict when an agent has multiple options.
   * @param eckige List of options.
   * @return the option to choose.
   */
  public ChoiceEckiges solveConfict(List<IEckiges> eckige);
}
