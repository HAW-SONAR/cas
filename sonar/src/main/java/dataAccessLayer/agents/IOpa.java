package dataAccessLayer.agents;

import dataAccessLayer.Plan;
import dataAccessLayer.tasks.IEckiges;
import dataAccessLayer.tasks.Round;

import java.util.List;

public interface IOpa extends IAgent {

  public List<IEckiges> getTasks();

  public List<String> getResources();

  public void addStartTask(Round protocol);

  public List<Round> getStartableTasks();

  public int getRoundOffer(Round round);

  public void startTeamformation(Round round, Plan plan);

  public void init();
}
