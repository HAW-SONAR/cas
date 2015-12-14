package cas.core.agentrepository;

import cas.common.agentrepository.IAgentRepositoryFassade;
import cas.common.logger.ILogger;

public class AgentRepositoryFassade implements IAgentRepositoryFassade{

	private final ILogger logger;

	public AgentRepositoryFassade(final ILogger logger) {
		this.logger = logger;

	}

	@Override
	public void createOrganizationFromXML(final String xmlValue) {
		throw new RuntimeException("Not yet Implemented");
	}

}
