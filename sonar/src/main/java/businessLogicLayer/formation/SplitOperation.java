package businessLogicLayer.formation;

import dataAccessLayer.agents.IOpa;
import dataAccessLayer.IProtocol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 */
public class SplitOperation extends TeamOperation {
    //NOTE the list should be sorted
    private ArrayList<IProtocol> components;

    public SplitOperation(IOpa operator, IProtocol protocol, List<IProtocol> components) {
        super(operator, protocol);
        this.components = new ArrayList<IProtocol>(components);
    }

    public List<IProtocol> getComponents() {
        return Collections.unmodifiableList(components);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SplitOperation that = (SplitOperation) o;

        return components != null ? components.equals(that.components) : that.components == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (components != null ? components.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String result = "[split " + super.toString() + " ->";
        boolean first = true;
        for (IProtocol component : components) {
            if (first) first = false;
            else result += " +";
            result += " " + component;
        }
        return result + "]";
    }
}
