package cas.frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cas.common.frontend.IMainWindow;

import javax.swing.JScrollPane;

class MainWindow extends JFrame implements IMainWindow {
	static MainWindow frame ;
	private final JPanel contentPane;
	private final Console console;

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		console = new Console();
		console.setBounds(12, 12, 612, 644);
		contentPane.add(console);

		final ButtonPane buttonPane = new ButtonPane();
		buttonPane.setBounds(636, 12, 354, 644);
		contentPane.add(buttonPane);
	}

	@Override
	public void addMessage(final String message){
		console.addMessage(message);
	}

}
