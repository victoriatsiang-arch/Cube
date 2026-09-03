import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

public class Cube extends JPanel{
    int r;
    double c_squared; 
    Point3D cube; Point3D screen; Point3D eye; 
    //int z_cube; int z_screen; int z_eye; 
    Point3D[] real_points = new Point3D[8]; // one; Point two; Point three; Point four; 
    Point[] screen_points= new Point[8];
    Timer timer; int seconds = 0; 
    int[][] connectionsList = new int[7][3]; 

    public Cube (int radius){
        r = radius; 
        c_squared = 2*r*r; 
        //the close screen to cube the bigger
        cube = new Point3D(0,0,0);
        screen = new Point3D(0,0,70);
        eye = new Point3D(0,0,120); 
        generateRealPoints(); 
        
        for(int i =6; i >= 0; i--){
            connectionsList[i] = createConnectionList(i+1); 
            for(int j = 0; j < 3; j++){
                System.out.print(connectionsList[i][j] + " ");
            }
            System.out.println();
        }
        timer = new Timer(30, new TimerListener());
        timer.start();
    } 

    public void generateRealPoints(){ 
        //starts with a "1" and then adds in 0s 
        for(int i = 1; i <= 8; i++){
            int x = (i < 5) ? -r : r; 
            int y = (i%4 == 1 || i%4 == 2) ? -r : r; 
            int z = (i%2 == 0) ? -r : r; 
            //minus 1 bc we go to index 8 
            real_points[i-1] = new Point3D(x,y,z); 
            //System.out.println(x + ", " + y + ", " + z); 
        }
    }
    
    public class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            seconds++; 
            seconds %= 360;
            generateScreenPoints();
            //xz_spin();
        }
    }

    public void xz_spin(){
        //we get the angle and then we add pi/4 because thats
        //where the first point is 
        //once you know the position of one point you have the rest
        double degrees = seconds * (2*3.14/360) + 45; 
        Point tr = new Point ((int) (Math.sqrt(c_squared)*Math.cos(degrees)), (int) (Math.sqrt(c_squared)*Math.sin(degrees))); 

        degrees += 45;
        Point br = new Point ((int) (Math.sqrt(c_squared)*Math.cos(degrees)), (int) (Math.sqrt(c_squared)*Math.sin(degrees))); 


        //only spins on the xz plane therefore we 
        //don't need to update the y value 
        for(int i = 0; i < 8; i++){
            real_points[i].x = 12; 
            real_points[i].z = 12; 
        }

    }

    //where the real math occurs
    public void generateScreenPoints(){
        //the z_screen pt isn't super accurate 
        //seconds = degrees

        //generateReadPoints()
        double radians = seconds * (2*3.14/360); 

        //cycles through all the points 
        for(int i =0; i< 8; i++){
            double z_s = screen.z - real_points[i].z; 
            double z_e = eye.z - real_points[i].z; 
            double constant = z_s/z_e; 
            //System.out.println("z_s:" + z_s + " z_e:" +z_e + " constant:" + constant);
            //the minus is perspective of eye 
            int temp_x = (int) ((real_points[i].x-Math.cos(radians)*120)*constant); 
            int temp_y = (int) ((real_points[i].y)*constant);  
            screen_points[i] = new Point(temp_x,temp_y);
            
            //System.out.print("x:" + temp_x); 
            //System.out.print("y:" + temp_y + " ");
        }
        //System.out.println();
        repaint();
        
    }
    

     private int[] createConnectionList(int decimal){
        int[] list = {-1,-1,-1}; 
        String binary = Integer.toBinaryString(decimal); 
        int counter = 0; 
        for(int i = 0; i < binary.length(); i++){
            if(binary.charAt(i) == '1'){
                String newBinary = binary.substring(0, i) + "0"
                    + binary.substring(i+1, binary.length()); 

                //System.out.println("binary:" + binary + " newBinary:" + newBinary);
                list[counter] = Integer.parseInt(newBinary, 2);
                counter++; 
            }
        }
        return list; 
    }

  

    //this is connecting the points 
    //so its like grey code 
    //only if one changes then its a connection 
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen); //makes sure we have a background
        
        // 0, 2 so then we have 4 5 6 7  so then its 5,7
        // 0,2 and 5,7
        int offset = 300; 

        
        pen.setColor(Color.GREEN);
        
        for(int i = 1; i <= 7; i++){
            for(int j = 0; j < 3; j++){
                if(connectionsList[i-1][j] != -1){
                    int index = connectionsList[i-1][j];
                    System.out.println("point1:" + i + " point2:" + index);
                    pen.drawLine(screen_points[i].x+offset, screen_points[i].y+offset, screen_points[index].x+offset, screen_points[index].y+offset);
                }
            }
            System.out.println(); 
        }
         
        pen.dispose();
    }
}