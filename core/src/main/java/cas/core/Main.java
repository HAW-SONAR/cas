package cas.core;

import java.lang.reflect.InvocationTargetException;

import cas.common.agentrepository.IAgentRepositoryFassade;
import cas.common.frontend.IMainWindow;
import cas.common.logger.ILogger;
import cas.common.logger.Logger;
import cas.core.agentrepository.AgentRepositoryFassade;
import cas.frontend.IFrontendInteractions;
import cas.frontend.WindowBuilder;

public class Main {
	private static IMainWindow mainWindow;
	private static ILogger logger;
	private static IAgentRepositoryFassade agentRepository;

	public static void main(final String[] args) throws InvocationTargetException, InterruptedException {
		logger = new Logger();
		mainWindow = new WindowBuilder().getNewMainWindow(new IFrontendInteractions() {

			@Override
			public void createOrganisatinFromXml(final String xmlValue) {
				agentRepository.createOrganizationFromXML(xmlValue);
			}
		},logger);
		logger.addMessageReceiver(mainWindow);
		mainWindow.setVisible(true);
		mainWindow.setEnabled(false);
		agentRepository = new AgentRepositoryFassade(logger);

		mainWindow.setEnabled(true);
		logger.log("System Started");
	}
}
