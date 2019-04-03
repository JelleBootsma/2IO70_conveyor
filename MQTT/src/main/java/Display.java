
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;




public class Display extends JPanel {

    JFrame frame; // Initiate the class variable frame

    BufferedImage image;
    private DiskManager diskManager;


    public Display(DiskManager diskManager){
        this.diskManager = diskManager;

    }


    void Refresh(){
        this.repaint();
    }

    public void Build(){
        frame = new JFrame("Trackinator 3000"); // Instantiate the windows


        try{
            URL path = Display.class.getResource("Belt.png");

            image = ImageIO.read(new File(path.getFile()));
        }
        catch (IOException e){
            System.out.println("Loading image failed");
        }


        // Define window variable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);
        frame.setLocation(50, 50);      // positions frame not tightly at the top left corner of the screen,
        // but 50 pixels to the right and down
        frame.add(this);
        frame.setVisible(true);

    }

    @Override
    public void paintComponent (Graphics g){
        super.paintComponent(g); //clears canvas to paint on, especially removes old obstacle

        g.drawImage(image, 0, 0, null);
        for (Disk disk : diskManager.getDiskList()){
            int diskLocation = disk.getLocation();
            int x;
            int y;
            if (diskLocation < 4){
                x = 598;
                y = (int)((double)diskLocation * 62.5) + 59;
            }
            else if (diskLocation < 12){
                x = (int)((double)diskLocation * -62.5) + 848;
                y = 309;
            }
            else if (diskLocation < 16){
                x = 98;
                y = (int)((double) diskLocation * - 62.5 ) + 1059;
            }
            else {
                y = 59;
                x = (int)((double)diskLocation * 62.5) - 902;
            }
            x -= 10;
            y -= 10;
            g.setColor(new Color(0,255,0));
            g.fillOval(x, y, 20, 20);
        }

    }





}
