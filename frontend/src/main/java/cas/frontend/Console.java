package cas.frontend;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Console extends JPanel {
	private final JTextField textField;

	/**
	 * Create the panel.
	 */
	public Console() {
		setLayout(null);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 589, 567);
		add(scrollPane);

		final JTextPane textPane = new JTextPane();
		scrollPane.setViewportView(textPane);

		textField = new JTextField();
		textField.setBounds(12, 591, 460, 19);
		add(textField);
		textField.setColumns(10);

		final JButton btnSend = new JButton("Send");
		btnSend.setBounds(484, 588, 117, 25);
		add(btnSend);

	}

}
