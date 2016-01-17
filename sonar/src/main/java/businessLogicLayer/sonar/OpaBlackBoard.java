package businessLogicLayer.sonar;

import dataAccessLayer.agents.IOpa;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel Hofmeister on 16.01.2016.
 */
public class OpaBlackBoard implements IOpaBlackBoard {

  Map<String,IOpa> opas;

  /**
   * Collective resource for Opa communication.
   */
  public OpaBlackBoard() {
    opas = new HashMap<>();
  }

  @Override public void registerOpa(IOpa opa) {
    opas.put(opa.getId(),opa);
  }

  @Override public IOpa getOpaById(String id) {
    return opas.get(id);
  }
}
