/*
 * Created by JFormDesigner on Mon May 13 22:48:06 PDT 2013
 */

package spatialdb.MidnightRun.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import spatialdb.MidnightRun.controller.SDOManager;
import spatialdb.MidnightRun.model.SDOShape;

public class DeleteDialog extends JDialog {
	
	private SDOManager manager;
	private List<SDOShape> shapes;
	private int selectedIndex = 0;
	public DeleteDialog(Frame owner, SDOManager manager) {
		super(owner, true);
		this.shapes = manager.getSDOShapes();
		this.manager = manager;
		initComponents();
	}

	public DeleteDialog(Dialog owner, SDOManager manager) {
		super(owner, true);
		this.shapes = manager.getSDOShapes();
		this.manager = manager;
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		deleteLabel = new JLabel();
		shapeNameCombo = new JComboBox(getShapeNames());
		buttonBar = new JPanel();
		deleteOkButton = new JButton();
		deleteCancelButton = new JButton();

		//======== this ========
		setTitle("Delete Shape");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new BorderLayout());

				//---- deleteLabel ----
				deleteLabel.setText("Delete: ");
				contentPanel.add(deleteLabel, BorderLayout.WEST);

				//---- shapeNameCombo ----
				shapeNameCombo.addItemListener(new ItemListener(){
											public void itemStateChanged(ItemEvent ie){
												int index = shapeNameCombo.getSelectedIndex();
												setSelectedIndex(index);
											}
										});
				contentPanel.add(shapeNameCombo, BorderLayout.CENTER);
			}
			dialogPane.add(contentPanel, BorderLayout.NORTH);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- deleteOkButton ----
				deleteOkButton.setText("OK");
				deleteOkButton.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
											deleteShape(e);
									}
								});
				buttonBar.add(deleteOkButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- deleteCancelButton ----
				deleteCancelButton.setText("Cancel");
				deleteCancelButton.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
										cancel(e);
									}
								});
				buttonBar.add(deleteCancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	protected void setSelectedIndex(int index) 
	{
		this.selectedIndex = index;
	}

	protected void deleteShape(MouseEvent e) 
	{
		String shapeName = shapes.get(selectedIndex).getName();
		ConfirmDialog confirm = new ConfirmDialog(this, shapeName);
		confirm.setSize(250, 125);
		confirm.setVisible(true);
		
		if (confirm.okPressed)
		{
			manager.deleteShape(shapeName);
			shapes.remove(selectedIndex);
			((MidnightRunWindow)getOwner()).setShapes(manager.toJShape(manager.getSDOShapes()));
			this.dispose();
		}
		
	}

	protected void cancel(MouseEvent e) 
	{
		this.dispose();
	}
	
	private String[] getShapeNames()
	{
		String[] names = new String[shapes.size()];
		
		for (int i = 0; i < shapes.size(); i ++)
		{
			names[i] = shapes.get(i).getName();
		}
		
		return names;
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel deleteLabel;
	private JComboBox shapeNameCombo;
	private JPanel buttonBar;
	private JButton deleteOkButton;
	private JButton deleteCancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
