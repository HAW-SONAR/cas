package dataAccessLayer.agents;

import businessLogicLayer.sonar.IOpaBlackBoard;
import dataAccessLayer.ChoiceEckiges;
import dataAccessLayer.ConflictSolver.CooperativeConflictSolver;
import dataAccessLayer.ConflictSolver.IConflictSolver;
import dataAccessLayer.Plan;
import dataAccessLayer.tasks.IEckiges;
import dataAccessLayer.tasks.Operation;
import dataAccessLayer.tasks.Round;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class Opa implements IOpa {

  private Oma oma;
  private String id;
  private List<IEckiges> tasks;
  private List<String> resources;
  private List<String> communicationProtocols;
  private IOpaBlackBoard opaBlackBoard;
  private IConflictSolver conflictSolver;
  private static final Logger logger = Logger.getLogger(Opa.class.getName());

  private List<Round> startableTasks;

  public Opa(Oma oma, String id, List<IEckiges> tasks, List<String> resources,
      List<String> communicationProtocols, IOpaBlackBoard opaBlackBoard) {
    this.oma = oma;
    this.id = id;
    this.tasks = tasks;
    this.resources = resources;
    this.communicationProtocols = communicationProtocols;
    this.opaBlackBoard = opaBlackBoard;
    this.startableTasks = new ArrayList<>();
    conflictSolver = new CooperativeConflictSolver(this.opaBlackBoard, this);
  }

  private int getExecOffer(String protocolName) {
    int result = new Random().nextInt(10);
    return new Random().nextInt(10);
  }

  @Override public int getEckigesOffer(IEckiges eckiges) {
    int result = 0;
    Operation op = eckiges.getType();
    if (op.equals(Operation.DELEG)) {
      result = 0;
    } else if (op.equals(Operation.SPLIT)) {
      result = 1;
    } else if (op.equals(Operation.REFINE)) {
      result = 1;
    } else if (op.equals(Operation.EXEC)) {
      result = getExecOffer(eckiges.getInput().getProtocol());
    } else {
      System.err.println("Operation: " + op + " not supported!");
    }
    result += oma.getEckigesOffer(eckiges);
    return result;
  }

  @Override public int getRoundOffer(Round round) {
    return new Random().nextInt(100);
  }

  @Override public void startTeamformation(Round round, Plan plan) {
    List<Round> openRound = new ArrayList<>();
    openRound.add(round);
    List<IEckiges> options;
    ChoiceEckiges nextStep = null;
    List<Round> tasksToBeDelegates = new ArrayList<>();
    int totalLocalCost = 0;
    while (!openRound.isEmpty()) {
      List<Round> openRound2 = new ArrayList<>();
      for (Round r : openRound) {
        options = getOptions(r);
        if (options.isEmpty()) {
          System.err.println("No Options available, error in organisation!");
        } else if (options.size() == 1) {
          nextStep = new ChoiceEckiges(options.get(0), getEckigesOffer(options.get(0)));
        } else {
          nextStep = conflictSolver.solveConfict(options);
        };
        totalLocalCost += nextStep.getCost();
        // if the Opa the Round Output of our Choice isnt the same as our local Opa then this Task
        // is to be completed by another Opa.
        if (nextStep.getEckiges().getType().equals(Operation.EXEC)) {
          plan.addExec(nextStep.getEckiges());
        } else if (!nextStep.getEckiges().getType().equals(Operation.DELEG)) {
          openRound2.addAll(nextStep.getEckiges().getOutput());
        } else {
          tasksToBeDelegates.addAll(nextStep.getEckiges().getOutput());
        }

      }
      openRound = openRound2;
    }
    // Start Teamwork for all others.
    for (Round r : tasksToBeDelegates) {
      opaBlackBoard.getOpaById(r.getOpaName()).startTeamformation(r, plan);
    }
  }

  private List<IEckiges> getOptions(Round round) {
    List<IEckiges> result = new ArrayList<>();
    for (IEckiges e : tasks) {
      if (e.getInput().equals(round)) {
        result.add(e);
      }
    }
    return result;
  }


  @Override public void init() {
    if (oma == null) {
      oma = new Oma("Oma for Opa: " + id, this, startableTasks);
    }
  }

  /* ___________________Setter Getter stuff_____________________ */


  public void setId(String id) {
    this.id = id;
  }

  @Override public String getId() {
    return id;
  }

  public List<IEckiges> getTasks() {
    return tasks;
  }

  public void setTasks(List<IEckiges> tasks) {
    this.tasks = tasks;
  }

  public List<String> getResources() {
    return resources;
  }

  @Override public void addStartTask(Round protocol) {
    startableTasks.add(protocol);
    logger.info("added a startable Task: " + protocol + " in Opa: " + id);
  }

  @Override public List<Round> getStartableTasks() {
    return startableTasks;
  }



  public void setStartableTasks(List<Round> startableTasks) {
    this.startableTasks = startableTasks;
  }

  public void setResources(List<String> resources) {
    this.resources = resources;
  }

  public List<String> getCommunicationProtocols() {
    return communicationProtocols;
  }

  public void setCommunicationProtocols(List<String> communicationProtocols) {
    this.communicationProtocols = communicationProtocols;
  }

  public Oma getOma() {
    return oma;
  }

  public void setOma(Oma oma) {
    this.oma = oma;
  }

  public void removeOma() {
    oma = null;
  }

  @Override public String toString() {
    return "Opa{" +
        "oma=" + oma +
        ", id='" + id + '\'' +
        ", tasks=" + tasks +
        ", resources=" + resources +
        ", communicationProtocols=" + communicationProtocols +
        ", startableTasks=" + startableTasks +
        "}\n";
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Opa))
      return false;

    Opa opa = (Opa) o;

    if (oma != null ? !oma.equals(opa.oma) : opa.oma != null)
      return false;
    if (id != null ? !id.equals(opa.id) : opa.id != null)
      return false;
    if (tasks != null ? !tasks.equals(opa.tasks) : opa.tasks != null)
      return false;
    if (resources != null ? !resources.equals(opa.resources) : opa.resources != null)
      return false;
    if (communicationProtocols != null ?
        !communicationProtocols.equals(opa.communicationProtocols) :
        opa.communicationProtocols != null)
      return false;
    return !(startableTasks != null ?
        !startableTasks.equals(opa.startableTasks) :
        opa.startableTasks != null);

  }

  @Override public int hashCode() {
    int result = oma != null ? oma.hashCode() : 0;
    result = 31 * result + (id != null ? id.hashCode() : 0);
    result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
    result = 31 * result + (resources != null ? resources.hashCode() : 0);
    result = 31 * result + (communicationProtocols != null ? communicationProtocols.hashCode() : 0);
    result = 31 * result + (startableTasks != null ? startableTasks.hashCode() : 0);
    return result;
  }
}
