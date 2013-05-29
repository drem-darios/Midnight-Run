/*
 * Created by JFormDesigner on Thu May 16 00:17:07 PDT 2013
 */

package spatialdb.MidnightRun.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author User #7
 */
public class ResultDialog extends JDialog {
	private String result;
	public ResultDialog(Frame owner, String result) {
		super(owner, true);
		this.result = result;
		initComponents();
	}

	public ResultDialog(Dialog owner, String result) {
		super(owner, true);
		this.result = result;
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label1 = new JLabel();
		resultLabel = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();

		//======== this ========
		setTitle("Result");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

				//---- label1 ----
				label1.setText("Result:");
				contentPanel.add(label1);

				//---- resultLabel ----
				resultLabel.setText("text");
				resultLabel.setText(result);
				contentPanel.add(resultLabel);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				okButton.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
										closeDialog(e);
									}
								});
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	protected void closeDialog(MouseEvent e) 
	{
		this.dispose();
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel label1;
	private JLabel resultLabel;
	private JPanel buttonBar;
	private JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
