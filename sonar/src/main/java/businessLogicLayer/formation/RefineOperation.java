package businessLogicLayer.formation;

import dataAccessLayer.agents.IOpa;
import dataAccessLayer.IProtocol;

/**
 */
public class RefineOperation extends TeamOperation {
    private final IProtocol refinement;

    protected RefineOperation(IOpa operator, IProtocol protocol, IProtocol refinement) {
        super(operator, protocol);
        this.refinement = refinement;
    }

    public IProtocol getRefinement() {
        return refinement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RefineOperation that = (RefineOperation) o;

        return refinement != null ? refinement.equals(that.refinement) : that.refinement == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (refinement != null ? refinement.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[refine " + super.toString() + " -> " + operator + ":" + refinement + "]";
    }
}
