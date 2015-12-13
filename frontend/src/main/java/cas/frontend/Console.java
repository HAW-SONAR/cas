package cas.frontend;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextField;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JTextArea;

class Console extends JPanel {
	private final JTextField input;
	private final JTextArea output;

	/**
	 * Create the panel.
	 */
	public Console() {
		setLayout(null);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 589, 567);
		add(scrollPane);

		output = new JTextArea();
		output.setEditable(false);
		scrollPane.setViewportView(output);

		input = new JTextField();
		input.setBounds(12, 591, 460, 19);
		add(input);
		input.setColumns(10);

		final JButton btnSend = new JButton("Send");
		btnSend.setBounds(484, 588, 117, 25);
		add(btnSend);

	}

	public void addMessage(final String message){
		if(message != null) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					output.append(message + "\n");
				}
			});
		}
	}
}


