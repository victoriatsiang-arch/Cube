import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class run{
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Cube c = new Cube(40); 
        frame.add(c);
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
