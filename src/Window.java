import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Window extends JFrame {

	private static final int GAP = 10;

	public Window() {
		setTitle("Lab Buddy");

		JPanel content = new JPanel();
		// Changes the layout of the panel so the equations would stack vertically.
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		// add "rigid stuff" to force minimum spacing
		content.add(Box.createVerticalStrut(GAP));

		add(content);
		JTextField equationField = new JTextField(10);
		content.add(equationField, BorderLayout.SOUTH);
		content.add(new JLabel("Equation"));
		setResizable(false);

		pack();
	}
}
