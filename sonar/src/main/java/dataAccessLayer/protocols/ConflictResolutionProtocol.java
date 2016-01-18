package dataAccessLayer.protocols;

import java.util.List;

/**
 * Invokable protocol that can resolve simple conflicts by choosing one of multiple independent options.
 * @param <O> The common supertype of all concrete options that can be included in a conflict that the protocol can solve.
 */
public interface ConflictResolutionProtocol<O> {
    /**
     * Solves a conflict by choosing a preferred option.
     * @param options The list of options that could be chosen to solve the conflict
     * @return The preferred option to solve the conflict according to the protocol
     */
	public ConflictOption<O> solveConflict(List<ConflictOption<O>> options);

    /**
     * @return The protocol's identifier (name)
     */
	public ConflictResolutionProtocolName getName();
}