package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TransferScreen extends JFrame {
	private JLabel lblberweisen;

	public TransferScreen(JFrame parentFrame) {
		setResizable(false);
		getContentPane().setLayout(null);
		{
			this.lblberweisen = new JLabel("Überweisen");
			this.lblberweisen.setBounds(12, 12, 104, 15);
			getContentPane().add(this.lblberweisen);
		}
		initTransferScreen(parentFrame);
	}

	private void initTransferScreen(JFrame parentFrame) {
		setTitle("Überweisen");
		setPreferredSize(new Dimension(300, 200));
	}
}
