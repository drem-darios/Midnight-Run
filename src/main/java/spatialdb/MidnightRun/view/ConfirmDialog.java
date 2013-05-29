/*
 * Created by JFormDesigner on Mon Mar 21 22:27:09 EDT 2011
 */

package spatialdb.MidnightRun.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * @author Drem Darios
 */
public class ConfirmDialog extends JDialog {
	
	String shapeName;
	boolean okPressed;
	//simple dialog box to ask the user if they want to overwrite the current event at this time
	public ConfirmDialog(Frame owner, String shapeName) {
		super(owner, true);
		this.shapeName = shapeName;
		this.okPressed = false;
		initComponents();
	}

	public ConfirmDialog(Dialog owner, String shapeName) {
		super(owner, true);
		this.shapeName = shapeName;
		this.okPressed = false;
		initComponents();
	}

	private void cancelButtonActionPerformed(ActionEvent e) {
		this.dispose(); //destroys the context
	}

	private void okButtonActionPerformed(ActionEvent e) {
		this.okPressed = true;
		this.dispose();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label1 = new JLabel();
		shapeLabel = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Delete?");
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(BorderFactory.createEmptyBorder());
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new FlowLayout());

				//---- label1 ----
				label1.setText("Are you sure you want to delete:");
				contentPanel.add(label1);

				//---- shapeLabel ----
				shapeLabel.setText("text");
				shapeLabel.setForeground(Color.red);
				shapeLabel.setText(shapeName);
				contentPanel.add(shapeLabel);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(BorderFactory.createEmptyBorder());
				buttonBar.setLayout(new FlowLayout());

				//---- okButton ----
				okButton.setText("Yes");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						okButtonActionPerformed(e);
					}
				});
				buttonBar.add(okButton);

				//---- cancelButton ----
				cancelButton.setText("No");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cancelButtonActionPerformed(e);
					}
				});
				buttonBar.add(cancelButton);
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel label1;
	private JLabel shapeLabel;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
