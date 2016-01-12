package businessLogicLayer.formation;

import dataAccessLayer.IOpa;
import dataAccessLayer.IProtocol;

/**
 */
public abstract class TeamOperation {
    protected final IOpa operator;
    protected final IProtocol protocol;

    protected TeamOperation(IOpa operator, IProtocol protocol) {
        this.operator = operator;
        this.protocol = protocol;
    }

    public IOpa getOperator() {
        return operator;
    }
    public IProtocol getProtocol() {
        return protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamOperation that = (TeamOperation) o;

        if (operator != null ? !operator.equals(that.operator) : that.operator != null) return false;
        return protocol != null ? protocol.equals(that.protocol) : that.protocol == null;

    }

    @Override
    public int hashCode() {
        int result = operator != null ? operator.hashCode() : 0;
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return operator + ":" + protocol;
    }
}
