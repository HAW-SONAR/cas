package cas.frontend;

import javax.swing.JPanel;
import javax.swing.JButton;

public class ButtonPane extends JPanel {

	/**
	 * Create the panel.
	 */
	public ButtonPane() {
		setLayout(null);

		final JButton btnLoadOrganisationFrom = new JButton("Load Organisation from file");
		btnLoadOrganisationFrom.setBounds(12, 12, 283, 25);
		add(btnLoadOrganisationFrom);

	}

}
