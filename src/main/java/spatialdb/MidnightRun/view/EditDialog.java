/*
 * Created by JFormDesigner on Tue May 14 09:10:12 PDT 2013
 */

package spatialdb.MidnightRun.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import spatialdb.MidnightRun.controller.SDOManager;
import spatialdb.MidnightRun.model.SDOShape;

public class EditDialog extends JDialog {
	
	private SDOManager manager;
	private List<SDOShape> shapes;
	private int selectedIndex = 0;
	
	public EditDialog(Frame owner, SDOManager manager) {
		super(owner, true);
		this.shapes = manager.getSDOShapes();
		this.manager = manager;
		initComponents();
	}

	public EditDialog(Dialog owner, SDOManager manager) {
		super(owner);
		this.shapes = manager.getSDOShapes();
		this.manager = manager;
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		shapeNameLabel = new JLabel();
		shapeNameCombo = new JComboBox(getShapeNames());
		xValuesLabel = new JLabel();
		xValuesText = new JTextField();
		xValuesText.setText(getXValues(0));
		yValuesLabel = new JLabel();
		yValuesText = new JTextField();
		yValuesText.setText(getYValues(0));
		buttonBar = new JPanel();
		editOkButton = new JButton();
		editCancelButton = new JButton();

		//======== this ========
		setTitle("Edit Shape");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridLayout(3, 2));

				//---- shapeNameLabel ----
				shapeNameLabel.setText("Shape name:");
				contentPanel.add(shapeNameLabel);

				//---- shapeNameCombo ----
				shapeNameCombo.addItemListener(new ItemListener(){
											public void itemStateChanged(ItemEvent ie){
												int index = shapeNameCombo.getSelectedIndex();
												xValuesText.setText(getXValues(index));
												yValuesText.setText(getYValues(index));
												setSelectedIndex(index);
											}
										});

				contentPanel.add(shapeNameCombo);

				//---- xValuesLabel ----
				xValuesLabel.setText("X Values:");
				contentPanel.add(xValuesLabel);
				contentPanel.add(xValuesText);

				//---- yValuesLabel ----
				yValuesLabel.setText("Y Values:");
				contentPanel.add(yValuesLabel);
				contentPanel.add(yValuesText);
			}
			dialogPane.add(contentPanel, BorderLayout.NORTH);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- editOkButton ----
				editOkButton.setText("OK");
				editOkButton.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
											editShape(e);
									}
								});
				buttonBar.add(editOkButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- editCancelButton ----
				editCancelButton.setText("Cancel");
				editCancelButton.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
										cancel(e);
									}
								});
				buttonBar.add(editCancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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

	protected void cancel(MouseEvent e) 
	{
		this.dispose();
	}
	
	protected void setSelectedIndex(int index) 
	{
		this.selectedIndex = index;
	}

	protected void editShape(MouseEvent e) 
	{
		String shapeName = shapes.get(selectedIndex).getName();
		
		String[] xValString = xValuesText.getText().split(",");
		String[] yValString = yValuesText.getText().split(",");
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < xValString.length; i++)
		{
			if (i == xValString.length-1)
			{
				sb.append(xValString[i]+", "+ yValString[i]);	
			}
			else
			{
				sb.append(xValString[i] + ", " + yValString[i]+ ", ");
			}
		}
		String edit = sb.toString();
		edit = edit.replace("[", "");
		edit = edit.replace("]", "");
		manager.editShape(shapeName, edit);
		((MidnightRunWindow)getOwner()).setShapes(manager.toJShape(manager.getSDOShapes()));
		this.dispose();
	}
	
	private String getXValues(int shapeIndex)
	{
		SDOShape selected = shapes.get(shapeIndex);
		return Arrays.toString(selected.getX());
	}
	
	private String getYValues(int shapeIndex)
	{
		SDOShape selected = shapes.get(shapeIndex);
		return Arrays.toString(selected.getY());
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
	private JLabel shapeNameLabel;
	private JComboBox shapeNameCombo;
	private JLabel xValuesLabel;
	private JTextField xValuesText;
	private JLabel yValuesLabel;
	private JTextField yValuesText;
	private JPanel buttonBar;
	private JButton editOkButton;
	private JButton editCancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
