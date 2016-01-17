package dataAccessLayer;

import dataAccessLayer.tasks.IEckiges;
import dataAccessLayer.tasks.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Hofmeister on 17.01.2016.
 */
public class Plan {
  List<IEckiges> execs;

  public Plan() {
    execs = new ArrayList<>();
  }

  public void addExec(IEckiges eckiges) {
    if (!eckiges.getType().equals(Operation.EXEC)) {
      throw new IllegalStateException("cant add a non exec task to the exec plan");
    }
    execs.add(eckiges);
  }

  @Override public String toString() {
    return "Plan{" +
        "execs=" + execs +
        '}';
  }
}
