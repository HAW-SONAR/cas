package businessLogicLayer.formation;

import dataAccessLayer.agents.IOpa;
import dataAccessLayer.IProtocol;

/**
 */
public class DelegateOperation extends TeamOperation {
    private IOpa delegate;

    public DelegateOperation(IOpa operator, IProtocol protocol, IOpa delegate) {
        super(operator, protocol);
        this.delegate = delegate;
    }

    public IOpa getDelegate() {
        return delegate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        DelegateOperation that = (DelegateOperation) o;

        return delegate != null ? delegate.equals(that.delegate) : that.delegate == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (delegate != null ? delegate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "[deleg " + super.toString() + " -> " + delegate + ":" + protocol + "]";
    }
}
