import javax.swing.*;
import java.awt.*;

public class A1083322_checkpoint7_Pyramid extends JLabel implements  Runnable {
    // Description : the grid location X of player.
    private int locationX;
    // Description : the grid location Y of player.
    private int locationY;
    // Description : the normal image size.
    private int originalGridLen;
    // Description : the Image the player is.
    private ImageIcon icon;
    private boolean understructure;
    private int jfScaler;
    private int maximum;
    // Description : The number of this type of building.
    private String produced_num;
    // Description : The image of under constructing.
    private ImageIcon constructingIcon;
        
        @Override
        public void run() {
            this.setText("0%");
            doNothing(500);
            this.setText("10%");
            doNothing(500);
            this.setText("20%");
            doNothing(500);
            this.setText("30%");
            doNothing(500);
            this.setText("40%");
            doNothing(500);
            this.setText("50%");
            doNothing(500);
            this.setText("60%");
            doNothing(500);
            this.setText("70%");
            doNothing(500);
            this.setText("80%");
            doNothing(500);
            this.setText("90%");
            doNothing(500);
            this.setText(produced_num);
            this.setIcon(icon);

       }
    
        // Description : Stop the thread in milliseconds.
        private static void doNothing(int milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                System.out.println("Unexpected interruption");
                System.exit(0);
            }
        }

    // Description: this is the player's constructor, we set icon of player here and calculate the distance every step.
    // Hint text : "player", horizontalAlignment : SwingConstants.CENTER
    public A1083322_checkpoint7_Pyramid(int locationX, int locationY, String text, int scaler, int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.locationX = locationX;
        this.locationY = locationY;
        this.understructure = true;
        this.jfScaler = scaler;
        this.originalGridLen = 256;
        this.icon = new ImageIcon("Resource/pyramid.png");
        this.maximum = 5;
        this.produced_num = text;
        Image img = icon.getImage();
        img = img.getScaledInstance(originalGridLen / scaler * 2, originalGridLen / scaler * 2, Image.SCALE_DEFAULT);
        icon.setImage(img);
        
        this.constructingIcon = new ImageIcon("Resource/constructing.png");
        Image img2 = constructingIcon.getImage();
        img2 = img2.getScaledInstance(originalGridLen / scaler * 2, originalGridLen / scaler * 2, Image.SCALE_DEFAULT);
        constructingIcon.setImage(img2);
        
        this.setIcon(constructingIcon);
         

        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    public int getlocationX() {
        return this.locationX;
    }

    public void setlocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getlocationY() {
        return this.locationY;
    }

    public void setlocationY(int locationY) {
        this.locationY = locationY;
    }

    public Image getImage() {
        return this.icon.getImage();
    }

}
