
package assignment5;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author acv
 */
public class Java2dDrawingApplication{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        DrawingApplicationFrame frame = new DrawingApplicationFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,500);
        frame.setVisible(true);
    }
    
}
