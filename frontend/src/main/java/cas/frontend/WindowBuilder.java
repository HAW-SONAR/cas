package cas.frontend;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import cas.common.frontend.IMainWindow;

public class WindowBuilder {

	private MainWindow mainWindow;

	synchronized public IMainWindow getNewMainWindow() throws InvocationTargetException, InterruptedException{
		EventQueue.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				mainWindow = new MainWindow();
			}
		});
		return mainWindow;
	}
}
