package cas.frontend;

import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;

class ButtonPane extends JPanel {

	private final IButtonActions buttonActions;

	/**
	 * Create the panel.
	 */
	public ButtonPane(final IButtonActions buttonActions) {
		this.buttonActions = buttonActions;
		setLayout(null);

		final JButton btnLoadOrganisationFrom = new JButton("Load Organisation from file");
		btnLoadOrganisationFrom.setBounds(12, 12, 283, 25);
		btnLoadOrganisationFrom.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				buttonActions.loadOrganisationFromFile();

			}
		});
		add(btnLoadOrganisationFrom);

	}
}
