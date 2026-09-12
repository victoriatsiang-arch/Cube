import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cube extends JPanel{
    int r;
    Point3D cube; Point3D screen; Point3D eye; 
    static Point3D[] perfect_real_points = new Point3D[8];
    Point3D[] real_points = new Point3D[8]; // one; Point two; Point three; Point four; 
    Point[] screen_points= new Point[8];
    Timer timer; int seconds = 0; 
    int[][] connectionsList = new int[7][3]; 

    public Cube (int radius){
        //the close screen to cube the bigger
        cube = new Point3D(0,0,0);
        screen = new Point3D(0,0,70);
        eye = new Point3D(0,0,120); 

        r = radius; 
        
        generatePerfectRealPoints(); 
        populate(connectionsList);
        createConnectionsList(8); // pass in the 8 b/c 8 points

        timer = new Timer(30, new TimerListener());
        timer.start();
    } 

    // idea of grey code so that i don't hardcode values
    public void generatePerfectRealPoints(){ 
        //starts with a "1" and then adds in 0s 
        for(int i = 1; i <= 8; i++){
            int x = (i < 5) ? -r : r; 
            int y = (i%4 == 1 || i%4 == 2) ? -r : r; 
            //i have to add a z value because my cube is no
            //longer at position (0,0,0) its at 
            // (0,0,z)
            int z = (i%2 == 1) ? -(cube.z +r) : (cube.z+r); 

            //minus 1 bc we go to index 8 
            real_points[i-1] = new Point3D(x,y,z); 
            perfect_real_points[i-1] = new Point3D(x, y, z); 
            System.out.println(x + ", " + y + ", " + z); 
        }
    }
    
    public class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            seconds++; 
            //seconds %= 360;
            spin(1);
        }
    }

    // when plane equals 0 -> spins in the xz plane
    // when plane equals 1 -> spins in the yz plane 
    public void spin(int plane){
        // angle in radians based off of time 
        double radians = (seconds/100.0);
        if(plane == 0){
            for(int i = 0; i < 8; i++){
                //this is according to matrix mult 
                //z acts as x value and x acts as y value 
                real_points[i].z = (int) (perfect_real_points[i].z*Math.cos(radians)
                    - perfect_real_points[i].x*Math.sin(radians)); 

                real_points[i].x = (int) (perfect_real_points[i].z*Math.sin(radians)
                    + perfect_real_points[i].x*Math.cos(radians)); 
            }
            //this then converts points to 2d and then displays
            projectToScreen(); 

        } else {
            for(int i = 0; i < 8; i++){
                //this is according to matrix mult 
                //z acts as x value and x acts as y value 
                real_points[i].z = (int) (perfect_real_points[i].z*Math.cos(radians)
                    - perfect_real_points[i].y*Math.sin(radians)); 

                real_points[i].y = (int) (perfect_real_points[i].z*Math.sin(radians)
                    + perfect_real_points[i].y*Math.cos(radians)); 
            }
            //this then converts points to 2d and then displays
            projectToScreen(); 
        } 
    }

    // this is like projectToScreen onto the screen 
    public void projectToScreen(){
        for(int i =0; i< 8; i++){
            double constant = (eye.z -screen.z - 0.0)/(eye.z - real_points[i].z);
            //double constant = (real_points[i].z + 0.0)/screen.z; 
            int temp_x = (int) ((real_points[i].x)*constant); 
            int temp_y = (int) ((real_points[i].y)*constant);  
            screen_points[i] = new Point(temp_x,temp_y);
        }
        repaint(); 
    }

    //the x value that we pass in is 8 
    //it doesn't matter if we start from 0 or 1 anymore 
    private void createConnectionsList(int length){ 
        int counter; 
        for(int i = 0; i < length - 1; i++){
            counter = 0; 
            for(int j = i + 1; j < length; j++){
                // boolean logic which searching for a difference
                // uses xor and and boolean logic operators 
                // of 1 
                int r = ((i ^ j) - 1) & (i^j); 
                if (r == 0){
                    //System.out.println("i:" + i + " j:" + j);
                    connectionsList[i][counter] = j; 
                    counter++;
                }
                
            }
        }
    }

    //this is connecting the points 
    //so its like grey code 
    //only if one changes then its a connection 
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen); //makes sure we have a background
        int offset = 300; 
        
        pen.setColor(Color.GREEN);
        
        for(int i = 0; i < connectionsList.length; i++){
            for(int j = 0; j < connectionsList[i].length; j++){
                if(connectionsList[i][j] != -1){
                    int point_index = connectionsList[i][j];
                    pen.drawLine(screen_points[i].x+offset, screen_points[i].y+offset, screen_points[point_index].x+offset, screen_points[point_index].y+offset);
                }
            }
        }
         
        pen.dispose();
    }

    //helper function to populate a blank array 
    //w/negative 1 values 
    public void populate(int[][] array){
        for(int i =0; i < array.length; i++){
            for(int j = 0; j< array[i].length; j++){
                array[i][j] = -1; 
            }
        }
    }
}