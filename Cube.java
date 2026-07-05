import javax.swing.*;
import java.awt.*;

public class Cube extends JPanel{
    int r;
    int z_cube; int z_screen; int z_eye; 
    Point3D[] points_3d=new Point3D[8]; // one; Point two; Point three; Point four; 
    Point[] s_point= new Point[8];

    public Cube (int radius){
        r = radius; 
        //the close screen to cube the bigger
        z_cube = 0;
        z_screen = 60; 
        z_eye = 120; 
        generateRealPoints(); 
        generateScreenPoints();
    } 

    public void generateRealPoints(){
        points_3d[0] = new Point3D(r,r,z_cube + r); 
        points_3d[1] = new Point3D(-r, r, z_cube + r); 
        points_3d[2] = new Point3D(-r,-r, z_cube + r);
        points_3d[3] = new Point3D(r, -r, z_cube + r); 
        points_3d[4] = new Point3D(r,r,z_cube - r); 
        points_3d[5] = new Point3D(-r, r, z_cube - r); 
        points_3d[6] = new Point3D(-r,-r, z_cube - r);
        points_3d[7] = new Point3D(r, -r, z_cube - r); 
    }
    public void generateScreenPoints(){
        //the z_screen pt isn't super accurate 
        
        for(int i =0; i< 8; i++){
            //cube z value isn't at 0, cube center is 
            int z_s = z_screen - points_3d[i].z; 
            int z_e = z_eye - points_3d[i].z; 
            int constant = r * z_s/z_e; 
            int temp_x = points_3d[i].x - constant; 
            int temp_y = points_3d[i].y - constant;  
            s_point[i] = new Point(temp_x,temp_y);
            System.out.println("x:" + temp_x);
            System.out.println("y:" + temp_y);
            
        }
    }

    public void paintComponent(Graphics pen) {
        super.paintComponent(pen); //makes sure we have a background
        pen.setColor(Color.GREEN);
        // 0, 2 so then we have 4 5 6 7  so then its 5,7
        // 0,2 and 5,7
        int offset = 300; 
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

        pen.dispose();
    }
}