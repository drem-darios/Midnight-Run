/*
 * Created by JFormDesigner on Mon May 13 23:03:36 PDT 2013
 */

package spatialdb.MidnightRun.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import spatialdb.MidnightRun.controller.SDOManager;
import spatialdb.MidnightRun.model.ShapeType;

public class CreateDialog extends JDialog {
	
	private SDOManager manager;
	
	public CreateDialog(Frame owner, SDOManager manager) {
		super(owner, true);
		this.manager = manager;
		initComponents();
	}

	public CreateDialog(Dialog owner, SDOManager manager) {
		super(owner, true);
		this.manager = manager;
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		shapeTypeLabel = new JLabel();
		shapeTypeCombo = new JComboBox(getShapeTypes());
		shapeNameLabel = new JLabel();
		shapeNameTF = new JTextField();
		xValuesLabel = new JLabel();
		xValuesTF = new JTextField();
		yValuesLabel = new JLabel();
		yValuesTF = new JTextField();
		interiorExteriorLabel = new JLabel();
		panel1 = new JPanel();
		interiorRadio = new JRadioButton();
		exteriorRadio = new JRadioButton();
		buttonBar = new JPanel();
		createOkButton = new JButton();
		createCancelButton = new JButton();

		//======== this ========
		setTitle("Create Shape");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridLayout(5, 2));

				//---- shapeTypeLabel ----
				shapeTypeLabel.setText("Shape Type:");
				contentPanel.add(shapeTypeLabel);

				//---- shapeTypeCombo ----
				shapeTypeCombo.addItemListener(new ItemListener()
								{
															public void itemStateChanged(ItemEvent ie)
															{
																ShapeType type = (ShapeType)shapeTypeCombo.getSelectedItem();
																if (type == ShapeType.POLYGON ||type ==  ShapeType.COMPLEX_POLYGON)
																{
																	interiorRadio.setEnabled(true);
																	interiorRadio.setSelected(true);
																	exteriorRadio.setEnabled(true);
																}
																else
																{
																	interiorRadio.setEnabled(false);
																	interiorRadio.setSelected(false);
																	exteriorRadio.setEnabled(false);
																}
															}
								});
				contentPanel.add(shapeTypeCombo);

				//---- shapeNameLabel ----
				shapeNameLabel.setText("Shape Name:");
				contentPanel.add(shapeNameLabel);
				contentPanel.add(shapeNameTF);

				//---- xValuesLabel ----
				xValuesLabel.setText("X Values:");
				contentPanel.add(xValuesLabel);
				contentPanel.add(xValuesTF);

				//---- yValuesLabel ----
				yValuesLabel.setText("Y Values: ");
				contentPanel.add(yValuesLabel);
				contentPanel.add(yValuesTF);

				//---- interiorExteriorLabel ----
				interiorExteriorLabel.setText("Interior/Exterior");
				contentPanel.add(interiorExteriorLabel);

				//======== panel1 ========
				{
					panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

					//---- interiorRadio ----
					interiorRadio.setText("Interior");
					interiorRadio.setEnabled(false);
					panel1.add(interiorRadio);

					//---- exteriorRadio ----
					exteriorRadio.setText("Exterior");
					exteriorRadio.setEnabled(false);
					panel1.add(exteriorRadio);
				}
				contentPanel.add(panel1);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- createOkButton ----
				createOkButton.setText("OK");
				createOkButton.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
											createShape(e);
									}
								});
				buttonBar.add(createOkButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- createCancelButton ----
				createCancelButton.setText("Cancel");
				createCancelButton.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
										cancel(e);
									}
								});
				buttonBar.add(createCancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());

		//---- buttongGroup1 ----
		ButtonGroup buttongGroup1 = new ButtonGroup();
		buttongGroup1.add(interiorRadio);
		buttongGroup1.add(exteriorRadio);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	
	
	private ShapeType[] getShapeTypes()
	{
		ShapeType[] types = ShapeType.values();
		String[] names = new String[types.length];
		
//		for (int i = 0; i < types.length; i ++)
//		{
//			names[i] = types[i].name();
//		}
		
		return types;
	}

	protected void createShape(MouseEvent e) 
	{
		ShapeType type = (ShapeType)shapeTypeCombo.getSelectedItem();
		String[] xValString = xValuesTF.getText().split(",");
		String[] yValString = yValuesTF.getText().split(",");
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
		
		manager.createShape(type, shapeNameTF.getText(), sb.toString());
		((MidnightRunWindow)getOwner()).setShapes(manager.toJShape(manager.getSDOShapes()));
		this.dispose();
		
	}

	private void cancel(MouseEvent e) 
	{
		this.dispose();
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel shapeTypeLabel;
	private JComboBox shapeTypeCombo;
	private JLabel shapeNameLabel;
	private JTextField shapeNameTF;
	private JLabel xValuesLabel;
	private JTextField xValuesTF;
	private JLabel yValuesLabel;
	private JTextField yValuesTF;
	private JLabel interiorExteriorLabel;
	private JPanel panel1;
	private JRadioButton interiorRadio;
	private JRadioButton exteriorRadio;
	private JPanel buttonBar;
	private JButton createOkButton;
	private JButton createCancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
