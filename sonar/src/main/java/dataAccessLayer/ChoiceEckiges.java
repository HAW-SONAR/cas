package dataAccessLayer;

import dataAccessLayer.tasks.IEckiges;

/**
 * Created by Daniel Hofmeister on 16.01.2016.
 */
public class ChoiceEckiges {
  private IEckiges eckiges;
  private int cost;

  public ChoiceEckiges(IEckiges eckiges, int cost) {
    this.eckiges = eckiges;
    this.cost = cost;
  }

  public IEckiges getEckiges() {
    return eckiges;
  }

  public int getCost() {
    return cost;
  }

  @Override public String toString() {
    return "ChoiceEckiges{" +
        "eckiges=" + eckiges +
        ", cost=" + cost +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof ChoiceEckiges))
      return false;

    ChoiceEckiges that = (ChoiceEckiges) o;

    if (cost != that.cost)
      return false;
    return !(eckiges != null ? !eckiges.equals(that.eckiges) : that.eckiges != null);

  }

  @Override public int hashCode() {
    int result = eckiges != null ? eckiges.hashCode() : 0;
    result = 31 * result + cost;
    return result;
  }
}
