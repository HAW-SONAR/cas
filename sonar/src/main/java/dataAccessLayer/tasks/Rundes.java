package dataAccessLayer.tasks;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 12.01.2016.
 */
//TODO rename to Round thing
public class Rundes implements IRundes {

  private Operation type;
  //TODO delete list make single
  private List<InputTask> input;
  private List<OutputTask> output;

  public Rundes(Operation type, List<InputTask> input, List<OutputTask> output) {
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

  public List<InputTask> getInput() {
    return input;
  }

  public void setInput(List<InputTask> input) {
    this.input = input;
  }

  public List<OutputTask> getOutput() {
    return output;
  }

  public void setOutput(List<OutputTask> output) {
    this.output = output;
  }

  @Override public String toString() {
    return "Rundes{" +
        "type=" + type +
        ", input=" + input +
        ", output=" + output +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Rundes))
      return false;

    Rundes rundes = (Rundes) o;

    if (type != rundes.type)
      return false;
    if (input != null ? !input.equals(rundes.input) : rundes.input != null)
      return false;
    return !(output != null ? !output.equals(rundes.output) : rundes.output != null);

  }

  @Override public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (input != null ? input.hashCode() : 0);
    result = 31 * result + (output != null ? output.hashCode() : 0);
    return result;
  }
}
