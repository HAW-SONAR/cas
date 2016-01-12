package businessLogicLayer.formation;

import dataAccessLayer.IOpa;
import dataAccessLayer.IProtocol;

/**
 */
public class ExecuteOperation extends TeamOperation {
    public ExecuteOperation(IOpa operator, IProtocol protocol) {
        super(operator, protocol);
    }

    @Override
    public String toString() {
        return "[exec " + super.toString() + "]";
    }
}
