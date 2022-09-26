import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class A1083322_checkpoint7_House extends JLabel implements Runnable, MouseListener{
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
    // Description : The root frame.
    A1083322_checkpoint7_GameFrame parentFrame;
    
    @Override
    public void run(){
        this.setText("0%");
        doNothing(500);
        this.setText("25%");
        doNothing(500);
        this.setText("50%");
        doNothing(500);
        this.setText("75%");
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

    public void mouseClicked(MouseEvent e)
    {
        if(this.getText().equals(produced_num))
        {
            openSpawnMenu();
            parentFrame.spawnMenu.setBase(locationX, locationY);
        }
    }
    public void mousePressed(MouseEvent e)
    {
    }
    public void mouseReleased(MouseEvent e)
    {
    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }

    // Description: this is the player's constructor, we set icon of player here and calculate the distance every step.
    // Hint text : "player", horizontalAlignment : SwingConstants.CENTER
    public A1083322_checkpoint7_House(int locationX, int locationY, String text, int scaler, int horizontalAlignment) {
        super(text, horizontalAlignment);
        this.locationX = locationX;
        this.locationY = locationY;
        this.understructure = true;
        this.jfScaler = scaler;
        this.originalGridLen = 256;
        this.maximum = 5;
        this.produced_num = text;
        understructure = true;

        this.icon = new ImageIcon("Resource/house.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(originalGridLen / scaler, originalGridLen / scaler, Image.SCALE_DEFAULT);
        icon.setImage(img);

        this.constructingIcon = new ImageIcon("Resource/constructing.png");
        Image img2 = constructingIcon.getImage();
        img2 = img2.getScaledInstance(originalGridLen / scaler, originalGridLen / scaler, Image.SCALE_DEFAULT);
        constructingIcon.setImage(img2);
        
        this.setIcon(constructingIcon);

        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);
        this.setVisible(true);
        this.addMouseListener(this);
    }
    public void setParentFrame() {
        this.parentFrame = (A1083322_checkpoint7_GameFrame) SwingUtilities.getWindowAncestor(this);
    }
    public void openSpawnMenu() {
        setParentFrame();
        parentFrame.spawnMenu.setVisible(true);
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
