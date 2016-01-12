package dataAccessLayer.tasks;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 12.01.2016.
 */
public class Eckiges implements IEckiges {

  private Operation type;
  //TODO delete list make single
  private List<InputRound> input;
  private List<OutputRound> output;

  public Eckiges(Operation type, List<InputRound> input, List<OutputRound> output) {
    this.type = type;
    this.input = input;
    this.output = output;
  }

  public Operation getType() {
    return type;
  }

  public void setType(Operation type) {
    this.type = type;
  }

  public List<InputRound> getInput() {
    return input;
  }

  public void setInput(List<InputRound> input) {
    this.input = input;
  }

  public List<OutputRound> getOutput() {
    return output;
  }

  public void setOutput(List<OutputRound> output) {
    this.output = output;
  }

  @Override public String toString() {
    return "Eckiges{" +
        "type=" + type +
        ", input=" + input +
        ", output=" + output +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Eckiges))
      return false;

    Eckiges eckiges = (Eckiges) o;

    if (type != eckiges.type)
      return false;
    if (input != null ? !input.equals(eckiges.input) : eckiges.input != null)
      return false;
    return !(output != null ? !output.equals(eckiges.output) : eckiges.output != null);

  }

  @Override public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (input != null ? input.hashCode() : 0);
    result = 31 * result + (output != null ? output.hashCode() : 0);
    return result;
  }
}
