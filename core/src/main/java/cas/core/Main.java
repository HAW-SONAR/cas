package cas.core;

import java.lang.reflect.InvocationTargetException;

import cas.common.frontend.IMainWindow;
import cas.frontend.WindowBuilder;

public class Main {
	private static IMainWindow mainWindow;

	public static void main(final String[] args) throws InvocationTargetException, InterruptedException {
		mainWindow = new WindowBuilder().getNewMainWindow();
		mainWindow.setVisible(true);
	}
}
