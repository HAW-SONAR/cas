package dataAccessLayer;

/**
 * Created by Daniel Hofmeister on 04.01.2016.
 */
public class Opa {

  private Oma oma;

  public Opa() {
    oma = null;
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
}
