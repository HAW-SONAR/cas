package cas.frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cas.common.frontend.IMainWindow;
import cas.common.logger.ILogger;

import javax.swing.JScrollPane;

class MainWindow extends JFrame implements IMainWindow {
	static MainWindow frame ;
	private final JPanel contentPane;
	private final Console console;
	private final IFrontendInteractions interactions;
	private final ILogger logger;

	/**
	 * Create the frame.
	 */
	public MainWindow(final IFrontendInteractions interactions, final ILogger logger) {
		this.interactions = interactions;
		this.logger = logger;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		console = new Console();
		console.setBounds(12, 12, 612, 644);
		contentPane.add(console);

		final ButtonPane buttonPane = new ButtonPane(new IButtonActions() {

			@Override
			public void loadOrganisationFromFile() {
				final JFileChooser fileChooser = new JFileChooser();
				final int retVal = fileChooser.showOpenDialog(MainWindow.this);
				if(retVal == JFileChooser.APPROVE_OPTION){
					new Thread(){
						@Override
						public void run() {
							FileReader fr;
							BufferedReader reader = null;
							try {
								fr = new FileReader(fileChooser.getSelectedFile());
								reader = new BufferedReader(fr);
								String xmlValue = "";
								String line = reader.readLine();
								while (line != null) {
									xmlValue +=line + "\n";
									line = reader.readLine();
								}
								interactions.createOrganisatinFromXml(xmlValue);
							} catch (final FileNotFoundException e) {
								logger.log(e.getMessage());
								e.printStackTrace();
							} catch (final IOException e) {
								logger.log(e.getMessage());
								e.printStackTrace();
							}catch(final RuntimeException e){
								logger.log(e.getMessage());
								e.printStackTrace();
							}finally {
								if(reader != null){
									try {
										reader.close();
									} catch (final IOException e) {
										logger.log(e.getMessage());
										e.printStackTrace();
									}
								}
							}
						};
					}.start();
				}else{
					logger.log("Unable to open File.");
				}
			}
		});
		buttonPane.setBounds(636, 12, 354, 644);
		contentPane.add(buttonPane);
	}

	@Override
	public void addMessage(final String message){
		console.addMessage(message);
	}

}
