package dataAccessLayer.tasks;

import java.util.List;

/**
 * Created by Daniel Hofmeister on 12.01.2016.
 */
public class Task implements ITask {

  private Operation type;
  private List<TeamformationInput> input;
  private List<TeamformationOutput> output;

  public Task(Operation type, List<TeamformationInput> input, List<TeamformationOutput> output) {
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

  public List<TeamformationInput> getInput() {
    return input;
  }

  public void setInput(List<TeamformationInput> input) {
    this.input = input;
  }

  public List<TeamformationOutput> getOutput() {
    return output;
  }

  public void setOutput(List<TeamformationOutput> output) {
    this.output = output;
  }

  @Override public String toString() {
    return "Task{" +
        "type=" + type +
        ", input=" + input +
        ", output=" + output +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof Task))
      return false;

    Task task = (Task) o;

    if (type != task.type)
      return false;
    if (input != null ? !input.equals(task.input) : task.input != null)
      return false;
    return !(output != null ? !output.equals(task.output) : task.output != null);

  }

  @Override public int hashCode() {
    int result = type != null ? type.hashCode() : 0;
    result = 31 * result + (input != null ? input.hashCode() : 0);
    result = 31 * result + (output != null ? output.hashCode() : 0);
    return result;
  }
}
