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
    Point3D[] points_3d=new Point3D[8]; // one; Point two; Point three; Point four; 
    Point[] s_point= new Point[8];
    Timer timer; int seconds = 0; 

    public Cube (int radius){
        r = radius; 
        c_squared = 2*r*r; 
        //the close screen to cube the bigger
        cube = new Point3D(0,0,0);
        screen = new Point3D(0,0,70);
        eye = new Point3D(0,0,120); 
        generateRealPoints(); 
        timer = new Timer(30, new TimerListener());
        timer.start();
    } 

    public void generateRealPoints(){
        
        //could prob rewrite to take less lines?
        // points_3d[0] = new Point3D(r, r, cube.z + r); 
        // points_3d[1] = new Point3D(-r, r, cube.z + r); 
        // points_3d[2] = new Point3D(-r,-r, cube.z + r);
        // points_3d[3] = new Point3D(r, -r, cube.z + r); 
        // points_3d[4] = new Point3D(r, r, cube.z - r); 
        // points_3d[5] = new Point3D(-r, r, cube.z - r); 
        // points_3d[6] = new Point3D(-r,-r, cube.z - r);
        // points_3d[7] = new Point3D(r, -r, cube.z - r);
        
        //starts with a "1" and then adds in 0s 
        for(int i = 1; i <= 8; i++){
            int x = (i < 5) ? r : -r; 
            int y = (i%4 == 1 || i%4 == 2) ? r : -r; 
            int z = (i%2 == 0) ? r : -r; 
            //minus 1 bc we go to index 8 
            points_3d[i-1] = new Point3D(x,y,z); 
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
            points_3d[i].x = 12; 
            points_3d[i].z = 12; 
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
            double z_s = screen.z - points_3d[i].z; 
            double z_e = eye.z - points_3d[i].z; 
            double constant = z_s/z_e; 
            //System.out.println("z_s:" + z_s + " z_e:" +z_e + " constant:" + constant);
            //the minus is perspective of eye 
            int temp_x = (int) ((points_3d[i].x-Math.cos(radians)*120)*constant); 
            int temp_y = (int) ((points_3d[i].y)*constant);  
            s_point[i] = new Point(temp_x,temp_y);
            
            //System.out.print("x:" + temp_x); 
            System.out.print("y:" + temp_y + " ");
        }
        System.out.println();
        repaint();
        
    }

  

    //this is connecting the points 
    //so its like grey code 
    //only if one changes then its a connection 
    public void paintComponent(Graphics pen) {
        super.paintComponent(pen); //makes sure we have a background
        
        // 0, 2 so then we have 4 5 6 7  so then its 5,7
        // 0,2 and 5,7
        int offset = 300; 
        pen.setColor(Color.RED); 
        // pen.drawLine(points_3d[0].x+offset, points_3d[0].y+offset, points_3d[1].x+offset, points_3d[1].y+offset);
        // pen.drawLine(points_3d[0].x+offset, points_3d[0].y+offset, points_3d[3].x+offset, points_3d[3].y+offset);
        // pen.drawLine(points_3d[2].x+offset, points_3d[2].y+offset, points_3d[1].x+offset, points_3d[1].y+offset);
        // pen.drawLine(points_3d[2].x+offset, points_3d[2].y+offset, points_3d[3].x+offset, points_3d[3].y+offset);
       // pen.fillRect(s_point[0].x+offset, s_point[0].x+offset, r*2, r*2);
        
        pen.setColor(Color.GREEN);
        pen.drawLine(s_point[0].x+offset, s_point[0].y+offset, s_point[1].x+offset, s_point[1].y+offset);
        pen.drawLine(s_point[0].x+offset, s_point[0].y+offset, s_point[3].x+offset, s_point[3].y+offset);
        pen.drawLine(s_point[0].x+offset, s_point[0].y+offset, s_point[4].x+offset, s_point[4].y+offset);
        //System.out.println((s_point[0].x+offset) + " " + (s_point[0].y+offset)+ " " + (s_point[1].x+offset)+ " " + (s_point[1].y+offset));

        pen.drawLine(s_point[2].x+offset, s_point[2].y+offset, s_point[1].x+offset, s_point[1].y+offset);
        pen.drawLine(s_point[2].x+offset, s_point[2].y+offset, s_point[3].x+offset, s_point[3].y+offset);
        pen.drawLine(s_point[2].x+offset, s_point[2].y+offset, s_point[6].x+offset, s_point[6].y+offset);
        //System.out.println((s_point[2].x+offset) + " " + (s_point[2].y+offset)+ " " + (s_point[1].x+offset)+ " " + (s_point[1].y+offset));


        pen.drawLine(s_point[5].x+offset, s_point[5].y+offset, s_point[1].x+offset, s_point[1].y+offset);
        pen.drawLine(s_point[5].x+offset, s_point[5].y+offset, s_point[4].x+offset, s_point[4].y+offset);
        pen.drawLine(s_point[5].x+offset, s_point[5].y+offset, s_point[6].x+offset, s_point[6].y+offset);

        pen.drawLine(s_point[7].x+offset, s_point[7].y+offset, s_point[3].x+offset, s_point[3].y+offset);
        pen.drawLine(s_point[7].x+offset, s_point[7].y+offset, s_point[4].x+offset, s_point[4].y+offset);
        pen.drawLine(s_point[7].x+offset, s_point[7].y+offset, s_point[6].x+offset, s_point[6].y+offset);

        //timer things 
        pen.setColor(Color.RED);
        pen.drawLine(s_point[0].x+offset, s_point[0].y+offset, s_point[1].x+offset, s_point[1].y+offset);
        pen.drawLine(s_point[0].x+offset, s_point[0].y+offset, s_point[3].x+offset, s_point[3].y+offset);
        pen.drawLine(s_point[2].x+offset, s_point[2].y+offset, s_point[1].x+offset, s_point[1].y+offset);
        pen.drawLine(s_point[2].x+offset, s_point[2].y+offset, s_point[3].x+offset, s_point[3].y+offset);
        

         
        pen.dispose();
    }
}