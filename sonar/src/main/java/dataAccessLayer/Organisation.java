package dataAccessLayer;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class Organisation {

  private List<Opa> allOpas;

  public Organisation() {}

  public List<Opa> getAllOpas() {
    return allOpas;
  }

  public void addOpa(Opa opa) {
    allOpas.add(opa);
  }

  public void deleteOpa(Opa opa) {
    if (allOpas.contains(opa)) {
      allOpas.remove(opa);
    } else {
      throw new IllegalStateException("Opa was not part of this Organisation");
    }
  }
}
