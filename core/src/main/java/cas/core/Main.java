package cas.core;

import java.lang.reflect.InvocationTargetException;

import cas.common.frontend.IMainWindow;
import cas.common.logger.ILogger;
import cas.common.logger.Logger;
import cas.frontend.IFrontendInteractions;
import cas.frontend.WindowBuilder;

public class Main {
	private static IMainWindow mainWindow;
	private static ILogger logger;

	public static void main(final String[] args) throws InvocationTargetException, InterruptedException {
		logger = new Logger();
		mainWindow = new WindowBuilder().getNewMainWindow(new IFrontendInteractions() {

			@Override
			public String createOrganisatinFromXml(final String xmlValue) {
				throw new RuntimeException("Not yet Implemented");
			}
		},logger);

		logger.addMessageReceiver(mainWindow);
		mainWindow.setVisible(true);
		logger.log("System Started");
	}
}
