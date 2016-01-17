package businessLogicLayer.sonar;

import dataAccessLayer.Plan;
import dataAccessLayer.SonarOrganisation;
import dataAccessLayer.agents.IOpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

public class Sonar implements ISonar {

  private SonarOrganisation sonarOrganisation;
  private List<IOpa> opasWithStartableTasks;
  private static final Logger logger = Logger.getLogger(Sonar.class.getName());

  public Sonar(SonarOrganisation sonarOrganisation) {
    this.sonarOrganisation = sonarOrganisation;
    opasWithStartableTasks = new ArrayList<>();
  }

  private void init() {
    for (IOpa o : sonarOrganisation.getAllOpas()) {
      o.init();
      if (!o.getStartableTasks().isEmpty()) {
        opasWithStartableTasks.add(o);
      }
    }
  }

  @Override public void run() {
    logger.info("initializing");
    init();
    Random random = new Random();
    while (true) {
      logger.info("About to start a task!");
      IOpa opa = opasWithStartableTasks.get(random.nextInt(opasWithStartableTasks.size()));
      Plan plan = new Plan();
      opa.startTeamformation(opa.getStartableTasks().get(random.nextInt(opa
          .getStartableTasks().size())), plan);
      System.out.println("FOUND a plan: " + plan);
      try {
        sleep(30000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }
}
