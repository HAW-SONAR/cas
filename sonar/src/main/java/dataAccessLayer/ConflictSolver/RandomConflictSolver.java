package dataAccessLayer.ConflictSolver;

import dataAccessLayer.ChoiceEckiges;
import dataAccessLayer.tasks.IEckiges;

import java.util.List;
import java.util.Random;

/**
 * Created by Daniel Hofmeister on 16.01.2016.
 */
public class RandomConflictSolver implements IConflictSolver {

  @Override public ChoiceEckiges solveConfict(List<IEckiges> eckige) {
    ChoiceEckiges result = new ChoiceEckiges(eckige.get(new Random().nextInt(eckige.size())),0);
    return result;
  }

}
