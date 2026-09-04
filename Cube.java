import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

public class Cube extends JPanel{
    int r;
    double c_squared; 
    Point3D cube; Point3D screen; Point3D eye; 
    Point3D[] perfect_real_points = new Point3D[8];
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

        generatePerfectRealPoints(); 
        
        for(int i =0; i < 7; i++){
            //goes from 1 to 7 inclusive 
            connectionsList[i] = createConnectionList(i+1); 
            for(int j = 0; j < 3; j++){
                //System.out.print(connectionsList[i][j] + " ");
            }
            //System.out.println();
        }

        timer = new Timer(30, new TimerListener());
        timer.start();
    } 

    // this is where i want to do the binary shift 
    public void generatePerfectRealPoints(){ 
        //starts with a "1" and then adds in 0s 
        for(int i = 1; i <= 8; i++){
            int x = (i < 5) ? -r : r; 
            int y = (i%4 == 1 || i%4 == 2) ? -r : r; 
            //i have to add a z value because my cube is no
            //longer at position (0,0,0) its at 
            // (0,0,z)
            int z = (i%2 == 0) ? -(cube.z +r) : (cube.z+r); 

            //minus 1 bc we go to index 8 
            real_points[i-1] = new Point3D(x,y,z); 
            perfect_real_points[i-1] = new Point3D(x, y, z); 
            //System.out.println(x + ", " + y + ", " + z); 
        }
    }
    
    public class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            seconds++; 
            seconds %= 360;
            xz_spin();
        }
    }

    //problem is there is a small lag (not continuous in rotation)
    public void xz_spin(){
        //we get the angle and then we add pi/4 because thats
        //where the first point is 
        //once you know the position of one point you have the rest
        double radians = (seconds/100.0);// % (2*Math.PI); 

        //only spins on the xz plane therefore we 
        //don't need to update the y value 
        for(int i = 0; i < 8; i++){
            //angle in radians
            //this is according to matrix mult 
            real_points[i].z = (int) (perfect_real_points[i].z*Math.cos(radians)
                - perfect_real_points[i].x*Math.sin(radians)); 

            real_points[i].x = (int) (perfect_real_points[i].z*Math.sin(radians)
                + perfect_real_points[i].x*Math.cos(radians)); 

            System.out.println("x:" + real_points[i].x +
                " z:" + real_points[i].z 
            ); 
        }

        System.out.println(); 

        //this then converts points to 2d and then displays
        transform(); 

    }

    // this is like project onto the screen 
    public void transform(){
        for(int i =0; i< 8; i++){
            double constant = (eye.z -screen.z - 0.0)/(eye.z - real_points[i].z);
            //double constant = (real_points[i].z + 0.0)/screen.z; 
            int temp_x = (int) ((real_points[i].x)*constant); 
            int temp_y = (int) ((real_points[i].y)*constant);  
            screen_points[i] = new Point(temp_x,temp_y);
        }
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
                    //System.out.println("point1:" + i + " point2:" + index);
                    pen.drawLine(screen_points[i].x+offset, screen_points[i].y+offset, screen_points[index].x+offset, screen_points[index].y+offset);
                }
            }
            //System.out.println(); 
        }
         
        pen.dispose();
    }
}