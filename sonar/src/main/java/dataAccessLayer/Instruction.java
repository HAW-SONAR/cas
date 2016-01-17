package dataAccessLayer;

import dataAccessLayer.tasks.Round;

/**
 * Created by Daniel Hofmeister on 16.01.2016.
 */
public class Instruction {
  private Round round;
  private int cost;

  public Instruction(Round round, int cost) {
    this.round = round;
    this.cost = cost;
  }

  public Round getRound() {
    return round;
  }

  public int getCost() {
    return cost;
  }

  @Override public String toString() {
    return "Instruction{" +
        "round=" + round +
        ", cost=" + cost +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Instruction))
      return false;

    Instruction that = (Instruction) o;

    if (cost != that.cost)
      return false;
    return !(round != null ? !round.equals(that.round) : that.round != null);

  }

  @Override public int hashCode() {
    int result = round != null ? round.hashCode() : 0;
    result = 31 * result + cost;
    return result;
  }
}
