package cas.frontend;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import cas.common.frontend.IMainWindow;
import cas.common.logger.ILogger;

public class WindowBuilder {

	private MainWindow mainWindow;

	synchronized public IMainWindow getNewMainWindow(final IFrontendInteractions interactions, final ILogger logger) throws InvocationTargetException, InterruptedException{
		EventQueue.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				mainWindow = new MainWindow(interactions, logger);
			}
		});
		return mainWindow;
	}
}
