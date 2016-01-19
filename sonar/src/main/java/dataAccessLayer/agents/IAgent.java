package dataAccessLayer.agents;

/**
 * Agent that can be part of an organisation.
 */
public interface IAgent {
    /**
     * @return The agent's name
     */
    String getName();

    /**
     * Starts the agent
     */
    void start();
}