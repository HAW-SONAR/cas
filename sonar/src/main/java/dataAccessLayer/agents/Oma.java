package dataAccessLayer.agents;

import dataAccessLayer.tasks.IEckiges;
import dataAccessLayer.tasks.Operation;
import dataAccessLayer.tasks.Round;

import java.util.List;
import java.util.Random;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class Oma implements IOma {

  private String id;
  private IOpa opa;
  private List<Round> startableTasks;

  public Oma(String id, IOpa opa, List<Round> startableTasks) {
    this.id = id;
    this.opa = opa;
    this.startableTasks = startableTasks;
  }

  @Override public String getId() {
    return id;
  }

  private int getExecOffer(String protocolName) {
    return new Random().nextInt(50);
  }

  @Override public int getEckigesOffer(IEckiges eckiges) {
    int result = 0;
    Operation op = eckiges.getType();
    if (op.equals(Operation.DELEG)) {
      result = 1;
    } else if (op.equals(Operation.SPLIT)) {
      result = 2;
    } else if (op.equals(Operation.REFINE)) {
      result = 5;
    } else if (op.equals(Operation.EXEC)) {
      result = getExecOffer(eckiges.getInput().getProtocol());
    }
    return result;
  }
}
