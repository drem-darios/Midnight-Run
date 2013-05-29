/*
 * Created by JFormDesigner on Wed May 15 01:56:36 PDT 2013
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
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import spatialdb.MidnightRun.controller.SDOManager;
import spatialdb.MidnightRun.controller.SDOOperator;
import spatialdb.MidnightRun.model.SDOShape;
import spatialdb.MidnightRun.view.shapes.JShape;

/**
 * @author User #7
 */
public class ApplyOperatorDialog extends JDialog {
	
	private SDOManager manager;
	private List<SDOShape> shapes;
	public ApplyOperatorDialog(Frame owner, SDOManager manager) {
		super(owner, true);
		this.shapes = manager.getSDOShapes();
		this.manager = manager;
		initComponents();
	}

	public ApplyOperatorDialog(Dialog owner, SDOManager manager) {
		super(owner, true);
		this.shapes = manager.getSDOShapes();
		this.manager = manager;
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		areaRadio = new JRadioButton();
		convexhullRadio = new JRadioButton();
		coversRadio = new JRadioButton();
		distanceRadio = new JRadioButton();
		mbrRadio = new JRadioButton();
		nnRadio = new JRadioButton();
		touchesRadio = new JRadioButton();
		unionRadio = new JRadioButton();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();
		panel1 = new JPanel();
		firstShapeBox = new JComboBox(getShapeNames());
		secondShapeBox = new JComboBox(getShapeNames());

		//======== this ========
		setTitle("Apply Operator");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridLayout(2, 0));

				//---- areaRadio ----
				areaRadio.setText("Area");
				areaRadio.setHorizontalAlignment(SwingConstants.LEFT);
				areaRadio.setSelected(true);
				areaRadio.addItemListener(new ItemListener(){
											public void itemStateChanged(ItemEvent ie){
												if (areaRadio.isSelected())
												{
													secondShapeBox.setEnabled(false);
												}
												else
												{
													secondShapeBox.setEnabled(true);
												}
											}
										});

				contentPanel.add(areaRadio);

				//---- convexhullRadio ----
				convexhullRadio.setText("Convex Hull");
				convexhullRadio.setHorizontalAlignment(SwingConstants.LEFT);
				convexhullRadio.addItemListener(new ItemListener(){
											public void itemStateChanged(ItemEvent ie){
												if (convexhullRadio.isSelected())
												{
													secondShapeBox.setEnabled(false);
												}
												else
												{
													secondShapeBox.setEnabled(true);
												}
											}
										});

				contentPanel.add(convexhullRadio);

				//---- coversRadio ----
				coversRadio.setText("Covers");
				coversRadio.setHorizontalAlignment(SwingConstants.LEFT);
				contentPanel.add(coversRadio);

				//---- distanceRadio ----
				distanceRadio.setText("Distance");
				distanceRadio.setHorizontalAlignment(SwingConstants.LEFT);
				contentPanel.add(distanceRadio);

				//---- mbrRadio ----
				mbrRadio.setText("MBR");
				mbrRadio.setHorizontalAlignment(SwingConstants.LEFT);
				mbrRadio.addItemListener(new ItemListener(){
											public void itemStateChanged(ItemEvent ie){
												if (mbrRadio.isSelected())
												{
													secondShapeBox.setEnabled(false);
												}
												else
												{
													secondShapeBox.setEnabled(true);
												}
											}
										});

				contentPanel.add(mbrRadio);

				//---- nnRadio ----
				nnRadio.setText("NN");
				nnRadio.setHorizontalAlignment(SwingConstants.LEFT);
				nnRadio.addItemListener(new ItemListener(){
											public void itemStateChanged(ItemEvent ie){
												if (nnRadio.isSelected())
												{
													secondShapeBox.setEnabled(false);
												}
												else
												{
													secondShapeBox.setEnabled(true);
												}
											}
										});

				contentPanel.add(nnRadio);

				//---- touchesRadio ----
				touchesRadio.setText("Touches");
				touchesRadio.setHorizontalAlignment(SwingConstants.LEFT);
				contentPanel.add(touchesRadio);

				//---- unionRadio ----
				unionRadio.setText("Union");
				unionRadio.setHorizontalAlignment(SwingConstants.LEFT);
				contentPanel.add(unionRadio);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				okButton.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
											applyOperator(e);
									}
								});
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseReleased(MouseEvent e) {
										cancel(e);
									}
								});
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);

			//======== panel1 ========
			{
				panel1.setLayout(new FlowLayout());
				panel1.add(firstShapeBox);

				//---- secondShapeBox ----
				secondShapeBox.setEnabled(false);
				panel1.add(secondShapeBox);
			}
			dialogPane.add(panel1, BorderLayout.NORTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());

		//---- buttonGroup1 ----
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(areaRadio);
		buttonGroup1.add(convexhullRadio);
		buttonGroup1.add(coversRadio);
		buttonGroup1.add(distanceRadio);
		buttonGroup1.add(mbrRadio);
		buttonGroup1.add(nnRadio);
		buttonGroup1.add(touchesRadio);
		buttonGroup1.add(unionRadio);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	private String[] getShapeNames() {
		String[] names = new String[shapes.size()];
		
		for (int i = 0; i < shapes.size(); i ++)
		{
			names[i] = shapes.get(i).getName();
		}
		
		return names;
	}

	protected void applyOperator(MouseEvent e) 
	{
		String firstShape = shapes.get(firstShapeBox.getSelectedIndex()).getName();
		String secondShape = shapes.get(secondShapeBox.getSelectedIndex()).getName();
		
		if (areaRadio.isSelected())
		{
			BigDecimal area = (BigDecimal) manager.applyOperator(SDOOperator.AREA, firstShape);
			ResultDialog rs = new ResultDialog(this, area.toString());
			rs.setSize(150, 150);
			rs.setVisible(true);
		}
		else if (convexhullRadio.isSelected())
		{
			JShape convexhull =(JShape) manager.applyOperator(SDOOperator.CONVEXHULL, firstShape);
			List<JShape> resultShapes = new ArrayList<JShape>();
			resultShapes.add(convexhull);
			((MidnightRunWindow)getOwner()).setShapes(resultShapes);
			this.dispose();
		}
		else if (coversRadio.isSelected())
		{
			Boolean covers = (Boolean)manager.applyOperator(SDOOperator.COVERS, firstShape, secondShape);
			ResultDialog rs = new ResultDialog(this, covers.toString());
			rs.setSize(150, 150);
			rs.setVisible(true);
		}
		else if (distanceRadio.isSelected())
		{
			BigDecimal distance = (BigDecimal)manager.applyOperator(SDOOperator.DISTANCE, firstShape, secondShape);
			ResultDialog rs = new ResultDialog(this, distance.toString());
			rs.setSize(150, 150);
			rs.setVisible(true);
		}
		else if (mbrRadio.isSelected())
		{
			JShape mbr = (JShape)manager.applyOperator(SDOOperator.MBR, firstShape);
			List<JShape> resultShapes = new ArrayList<JShape>();
			resultShapes.add(mbr);
			((MidnightRunWindow)getOwner()).setShapes(resultShapes);
			this.dispose();
		}
		else if(nnRadio.isSelected())
		{
			List<JShape> resultShapes = (List<JShape>) manager.applyOperator(SDOOperator.NN, firstShape);
			((MidnightRunWindow)getOwner()).setShapes(resultShapes);
			this.dispose();	
		}
		else if (touchesRadio.isSelected())
		{
			Boolean touches = (Boolean)manager.applyOperator(SDOOperator.TOUCHES, firstShape, secondShape);
			ResultDialog rs = new ResultDialog(this, touches.toString());
			rs.setSize(150, 150);
			rs.setVisible(true);
		}
		else if (unionRadio.isSelected())
		{
			JShape union = (JShape)manager.applyOperator(SDOOperator.UNION, firstShape, secondShape);
			List<JShape> resultShapes = new ArrayList<JShape>();
			resultShapes.add(union);
			((MidnightRunWindow)getOwner()).setShapes(resultShapes);
			this.dispose();
		}
	}

	protected void cancel(MouseEvent e) 
	{
		this.dispose();
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JRadioButton areaRadio;
	private JRadioButton convexhullRadio;
	private JRadioButton coversRadio;
	private JRadioButton distanceRadio;
	private JRadioButton mbrRadio;
	private JRadioButton nnRadio;
	private JRadioButton touchesRadio;
	private JRadioButton unionRadio;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;
	private JPanel panel1;
	private JComboBox firstShapeBox;
	private JComboBox secondShapeBox;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
