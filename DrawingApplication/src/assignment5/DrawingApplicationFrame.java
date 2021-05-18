
package assignment5;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/*
Comp Sci 221 Assignment 5
Michael McDonough
25 October 2019
*/
public class DrawingApplicationFrame extends JFrame{
    
    public Color color1;
    public Color color2;
    public String shape;
    public ArrayList<MyShapes> shapeList;
    // Create the panels for the top of the application. One panel for each
    // line and one to contain both of those panels.
    JPanel firstLinePanel;
    JPanel secondLinePanel;
    JPanel thirdPanel;
    
    // create the widgets for the firstLine Panel.
    public JButton undo;
    public JButton clear;
    public JLabel shapelabel;
     String[] shapeTypes;
    public JComboBox<String> shapeBox;
    public JCheckBox filledbox;
    
    //create the widgets for the secondLine Panel.
    public JCheckBox gradientbox;
    public JButton color1button;
    public JButton color2button;
    public JLabel width;
    public JTextField widthfield; 
    public JLabel length;
    public JTextField lengthfield; 
    public JCheckBox dashCheckBox;
    
    
    // Variables for drawPanel.
    public JPanel drawPanel;

    // add status label
    public JLabel statusLabel;
    // Constructor for DrawingApplicationFrame
    public DrawingApplicationFrame()
    {
    shapeList = new ArrayList<MyShapes>();
    setTitle("Java 2D Drawings");    
    setLayout( new BorderLayout());
    // Initialize all widgets and panels
    statusLabel = new JLabel("(,)"); 
    undo = new JButton("Undo");
    clear = new JButton("Clear");
    shapelabel = new JLabel("Shape:");
    String[] shapeTypes = { "Oval", "Rectangle", "Line" };
    shapeBox = new JComboBox<>(shapeTypes);
    filledbox = new JCheckBox("filled");
        
    gradientbox = new JCheckBox("Use Gradient");
    color1button = new JButton("1st Color...");
    color2button = new JButton("2nd Color...");
    width = new JLabel("Line Width:");
    widthfield = new JTextField(2); 
    length = new JLabel("Dash Length:");
    lengthfield = new JTextField(2);
    dashCheckBox = new JCheckBox("Dashed");
    
    
       firstLinePanel = new JPanel();
       secondLinePanel = new JPanel();
       thirdPanel = new JPanel();
       drawPanel = new DrawPanel();
       drawPanel.setBackground(Color.WHITE);
       
       firstLinePanel.setLayout(new FlowLayout());
       secondLinePanel.setLayout(new FlowLayout());
       thirdPanel.setLayout(new BorderLayout());
        // add widgets to panels
        // firstLine widgets
       
        firstLinePanel.add(undo);
        firstLinePanel.add(clear);
        firstLinePanel.add(shapelabel);
        firstLinePanel.add(shapeBox);
        firstLinePanel.add(filledbox);
        // secondLine widgets
        secondLinePanel.add(gradientbox);
        secondLinePanel.add(color1button);
        secondLinePanel.add(color2button);
        secondLinePanel.add(width);
        secondLinePanel.add(widthfield);
        secondLinePanel.add(length);
        secondLinePanel.add(lengthfield);
        secondLinePanel.add(dashCheckBox);
        // add top panel of two panels
        thirdPanel.add(firstLinePanel, BorderLayout.NORTH);
        thirdPanel.add(secondLinePanel, BorderLayout.SOUTH);
        
        
        // add topPanel to North, drawPanel to Center, and statusLabel to South
        add(thirdPanel, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
       setSize(650,550);
       setVisible(true);
        //add listeners and event handlers
       
       // Create event handler for the first color
       shapeBox.addActionListener(new ActionListener() {

           @Override
           public void actionPerformed(ActionEvent e) {
               // TODO Auto-generated method stub
               JComboBox<String> cb = (JComboBox<String>) e.getSource();
               shape = (String)cb.getSelectedItem();
           }
       });
       
       // Create event handler for the first color
       color1button.addActionListener(new ActionListener(){
               
               @Override
               public void actionPerformed(ActionEvent event){
                   color1 = JColorChooser.showDialog(DrawingApplicationFrame.this, "Choose a color", color1);
               
                   if (color1 == null)
                       color1 = Color.LIGHT_GRAY;
                   
               }
           }
               
       );
       // Create event handler for second color
       color2button.addActionListener(new ActionListener(){
               
               @Override
               public void actionPerformed(ActionEvent event){
                   color2 = JColorChooser.showDialog(DrawingApplicationFrame.this, "Choose a color", color2);
               
                   if (color2 == null)
                       color2 = Color.LIGHT_GRAY;
                   
               }
           }
               
       );
       //Clear the drawPanel
       clear.addActionListener((ActionEvent event) -> {
           shapeList.clear();
           repaint();
    });
       
       //Undo the last shape
       undo.addActionListener((ActionEvent event) -> {
           if (!shapeList.isEmpty()) {
               shapeList.remove(shapeList.size() - 1);
               repaint();
           }
    });
    
    }
    // Create event handlers, if needed
    

    // Create a private inner class for the DrawPanel.
    private class DrawPanel extends JPanel
    {
        Stroke stroke;
        public ArrayList<MyShapes> shapeList = new ArrayList<MyShapes>();
        
        public DrawPanel()
        {
            
            super();
            this.addMouseListener(new MouseHandler());
            this.addMouseMotionListener(new MouseHandler());
            
        }
        
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
              
            //loop through and draw each shape in the shapes arraylist
            for (int i = 0; i < shapeList.size(); i++) { 		      
              shapeList.get(i).draw(g2d);
               
        }
        }

        private class MouseHandler extends MouseAdapter implements MouseMotionListener
        {
            public Paint paint;
            public Stroke stroke;
            
            
            
            @Override
            public void mousePressed(MouseEvent event){
                
                
              Boolean filled = false;
              //Create Gradient
                if (gradientbox.isSelected()){
                   
                    paint = new GradientPaint(0, 0, color1, 50, 50, color2, true);
                }
                else{
                    paint = color1;
                }
                //Make the dashed shapes
                if (dashCheckBox.isSelected()){
                    float[] dashLength = {Integer.parseInt(lengthfield.getText())};
                    stroke = new BasicStroke(Integer.parseInt(widthfield.getText()), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashLength, 0);
            }   
                else{
                    stroke = new BasicStroke(Integer.parseInt(widthfield.getText()), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            }
                //Fill in shapes
                if (filledbox.isSelected()){
                  filled = true;
                } 
                  else{
                 filled = false;
                          
            }  
              // Add the chosen shape to the Arraylist to be drawn
              if (shapeBox.getSelectedItem() == "Line"){
                  MyLine Line;
                  Line = new MyLine(event.getPoint(), event.getPoint(), paint, stroke);
                  shapeList.add(Line);
                  
              }
              if (shapeBox.getSelectedItem() == "Oval"){
                  MyOval Oval;
                  Oval = new MyOval(event.getPoint(), event.getPoint(), paint, stroke, filled);
                  shapeList.add(Oval);
              }
              if (shapeBox.getSelectedItem() == "Rectangle"){
                  MyRectangle Rectangle;
                  Rectangle = new MyRectangle(event.getPoint(), event.getPoint(), paint, stroke, filled);
                  shapeList.add(Rectangle);
              }
                repaint();
              
            }
            @Override
            public void mouseReleased(MouseEvent event)
            {
                
                Point end = event.getPoint();
                shapeList.get(shapeList.size() - 1).setEndPoint(end);
           
                repaint();
                }
                

            @Override
            public void mouseDragged(MouseEvent event)
            {
                Point end = event.getPoint();
                shapeList.get(shapeList.size() - 1).setEndPoint(end);
                repaint();
               
            }

            @Override
            public void mouseMoved(MouseEvent event)
            {
                // Get the Mouse Position for the Status Label
                String position = "(" + event.getX() + "," + event.getY() + ")";
                statusLabel.setText(position);
            }
        }
    }

}