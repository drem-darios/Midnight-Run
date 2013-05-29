/*
 * Created by JFormDesigner on Thu May 02 19:38:37 PDT 2013
 */

package spatialdb.MidnightRun.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import spatialdb.MidnightRun.controller.SDOManager;
import spatialdb.MidnightRun.view.shapes.JShape;

public class MidnightRunWindow extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private Map<Integer, JShape> shapes = new HashMap<Integer, JShape>();
	private SDOManager manager;
	
	public MidnightRunWindow(SDOManager manager, List<JShape> initShapes) 
	{
		this.manager = manager;
		initComponents();
		setShapes(initShapes);
	}
	
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar = new JMenuBar();
		fileMenu = new JMenu();
		createShapeItem = new JMenuItem();
		editMenu = new JMenu();
		editShapeItem = new JMenuItem();
		deleteShapeItem = new JMenuItem();
		viewMenu = new JMenu();
		zoomInItem = new JMenuItem();
		zoomOutItem = new JMenuItem();
		applyOperatorItem = new JMenuItem();
		resetItem = new JMenuItem();
		visualizerPanel = new VisualizerPanel();

		//======== this ========
		setResizable(false);
		setTitle("Midnight Run");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(600, 300));
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar ========
		{
			menuBar.setMargin(new Insets(10, 15, 10, 15));

			//======== fileMenu ========
			{
				fileMenu.setText("File");

				//---- createShapeItem ----
				createShapeItem.setText("Create shape");
				createShapeItem.setName("createShapeItem");
				createShapeItem.addActionListener(this);
				fileMenu.add(createShapeItem);
			}
			menuBar.add(fileMenu);

			//======== editMenu ========
			{
				editMenu.setText("Edit");

				//---- editShapeItem ----
				editShapeItem.setText("Edit shape");
				editShapeItem.setName("editShapeItem");
				editShapeItem.addActionListener(this);
				editMenu.add(editShapeItem);

				//---- deleteShapeItem ----
				deleteShapeItem.setText("Delete shape");
				deleteShapeItem.setName("deleteShapeItem");
				deleteShapeItem.addActionListener(this);
				editMenu.add(deleteShapeItem);
			}
			menuBar.add(editMenu);

			//======== viewMenu ========
			{
				viewMenu.setText("View");

				//---- zoomInItem ----
				zoomInItem.setText("Zoom in");
				zoomInItem.setName("zoomInItem");
				zoomInItem.setEnabled(false);
				viewMenu.add(zoomInItem);

				//---- zoomOutItem ----
				zoomOutItem.setText("Zoom out");
				zoomOutItem.setName("zoomOutItem");
				zoomOutItem.setEnabled(false);
				viewMenu.add(zoomOutItem);
				viewMenu.addSeparator();

				//---- applyOperatorItem ----
				applyOperatorItem.setText("Apply operator");
				applyOperatorItem.setName("applyOperatorItem");
				applyOperatorItem.addActionListener(this);
				viewMenu.add(applyOperatorItem);

				//---- resetItem ----
				resetItem.setText("Reset");
				resetItem.setName("resetItem");
				resetItem.addActionListener(this);
				viewMenu.add(resetItem);
			}
			menuBar.add(viewMenu);
		}
		setJMenuBar(menuBar);

		//======== visualizerPanel ========
		{
			visualizerPanel.setBorder(new LineBorder(Color.black, 2));
			visualizerPanel.setLayout(new BorderLayout(5, 5));
		}
		contentPane.add(visualizerPanel, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem createShapeItem;
	private JMenu editMenu;
	private JMenuItem editShapeItem;
	private JMenuItem deleteShapeItem;
	private JMenu viewMenu;
	private JMenuItem zoomInItem;
	private JMenuItem zoomOutItem;
	private JMenuItem applyOperatorItem;
	private JMenuItem resetItem;
	private JPanel visualizerPanel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
	private class VisualizerPanel extends JPanel
	{
		private static final long serialVersionUID = 1L;
		public void paintComponent(Graphics g) 
		{
			super.paintComponent(g);
			
			for (JShape shape : shapes.values())
			{
				shape.draw(g);
			}
			
	    }
		
		
	}
	
	public void setShapes(List<JShape> newShapes)
	{
		shapes.clear();
		for(JShape shape : newShapes)
		{
			shapes.put(shape.getId(), shape);
		}
		
		repaint();
	}
	
	public List<JShape> getShapes()
	{
		return (List<JShape>) shapes.values();
	}
	
	public void actionPerformed(ActionEvent event) 
	{
		JMenuItem menuItem = (JMenuItem)event.getSource();
		if(menuItem.getName().equals("editShapeItem"))
		{
			EditDialog edit = new EditDialog(this, manager);
			edit.setSize(450, 175);
			edit.setEnabled(true);
			edit.setVisible(true);
		}
		else if(menuItem.getName().equals("deleteShapeItem"))
		{

			DeleteDialog delete = new DeleteDialog(this, manager);
			delete.setSize(300, 150);
			delete.setVisible(true);
		}
		else if(menuItem.getName().equals("createShapeItem"))
		{
			CreateDialog od = new CreateDialog(this, manager);
			od.setSize(450,225);
			od.setEnabled(true);
			od.setVisible(true);
		}
		else if(menuItem.getName().equals("zoomInItem"))
		{
			
		}
		else if(menuItem.getName().equals("zoomOutItem"))
		{
			
		}
		else if(menuItem.getName().equals("applyOperatorItem"))
		{
			ApplyOperatorDialog od = new ApplyOperatorDialog(this, manager);
			od.setSize(450,225);
			od.setEnabled(true);
			od.setVisible(true);
		}
		
		else if(menuItem.getName().equals("resetItem"))
		{
			setShapes(manager.getJShapes());
		}
		
		repaint();
	}
	
	public SDOManager getManager()
	{
		return manager;
	}
	
}
