package panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import template.TemplateFrame;

public class LoadingPanel extends JPanel {

	private static final long serialVersionUID = -5635683829636543434L;

	JFrame mainFrame;

	JPanel progressLabelPanel = new JPanel();
	JPanel progressBarPanel = new JPanel();

	JProgressBar bar = new JProgressBar();
	JLabel progressLabel = new JLabel(
			"Loading data from the server. Please wait.");

	public LoadingPanel(JFrame mainFrame) {

		this.mainFrame = mainFrame;
		mainFrame.setTitle("TCG on the GO");

		setLayout(new BorderLayout());

		progressLabelPanel.add(progressLabel);
		progressBarPanel.add(bar);

		add(progressLabelPanel, BorderLayout.NORTH);
		add(progressBarPanel, BorderLayout.CENTER);

		mainFrame.add(this);

		mainFrame.setLocationRelativeTo(null);

		revalidate();
		repaint();
	}

	public static void main(String[] args) {
		new LoadingPanel(new TemplateFrame());
	}
}
