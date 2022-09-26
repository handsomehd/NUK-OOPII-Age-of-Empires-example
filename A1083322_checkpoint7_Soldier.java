import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
// Description : This class will implement Runnable and A1083322_checkpoint7_Soldier_Movement.(may need to rename)
public class A1083322_checkpoint7_Soldier extends JLabel implements Runnable, A1083322_checkpoint7_Soldier_Movement {
    // Description : the grid location X of player.
    private int locationX;
    // Description : the grid location Y of player.
    private int locationY;
    // Description : the normal image size.
    private int originalGridLen;
    // Description : the Image the player is.
    private ImageIcon icon;
    // Description : The image of progress to grow up.
    private ImageIcon[] growingIcons;
    // Description : The root frame.
    private A1083322_checkpoint7_GameFrame parentFrame;
    // Description : To check if this soldier is selected.
    public boolean isSelected;
    // Description : To check if this soldier is a grown up.
    private boolean isGrown;
    // Description : To store the route to get to the destination.
    private A1083322_checkpoint7_RouteLinkedList route = new A1083322_checkpoint7_RouteLinkedList();
    // Description : The grid location of destination.
    private int destinationX, destinationY;
    //Description : UCS : 2, BFS : 1, DFS : 0
    private int algorithm;
    public A1083322_checkpoint7_RouteFinder finder;

    public A1083322_checkpoint7_Soldier(int locationX, int locationY, int scaler, int horizontalAlignment, int algorithm) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.originalGridLen = 256;
        this.icon = new ImageIcon("Resource/soldier.png");
        Image img = icon.getImage();
        img = img.getScaledInstance(originalGridLen / scaler, originalGridLen / scaler, Image.SCALE_DEFAULT);
        icon.setImage(img);
        this.isSelected = false;
        this.isGrown = false;
        this.algorithm = algorithm;
        setParentFrame();
        
        growingIcons = new ImageIcon[6];
        
        for( int i=0; i<6; i++)
        {
            growingIcons[i] = new ImageIcon("Resource/baby"+Integer.toString(i)+".png");
            Image img2 = growingIcons[i].getImage();
            img2 = img2.getScaledInstance(originalGridLen / scaler, originalGridLen / scaler, Image.SCALE_DEFAULT);
            growingIcons[i].setImage(img2);
        }
        
        this.setIcon(growingIcons[0]);

        this.addMouseListener(new MouseAdapter()
                {
                    public void mouseClicked(MouseEvent e)
                    {
                        if(isGrown && !isSelected)    //換另一個士兵移動
                        {
                            for( A1083322_checkpoint7_Soldier x : parentFrame.soldierList)
                            {
                                x.isSelected = false;
                                x.repaint();
                            }
                            isSelected = true;
                            setSelectedSoldier();
                            repaint();
                            return;
                        }
                        if(isSelected)                 //第一個士兵移動
                        {
                            for( A1083322_checkpoint7_Soldier x : parentFrame.soldierList)
                            {
                                x.isSelected = false;
                                x.repaint();
                            }
                            resetSelectedSoldier();
                            return;
                        }
                    }
                });

        this.setHorizontalTextPosition(JLabel.CENTER);
        this.setVerticalTextPosition(JLabel.CENTER);

    }

    @Override
    public void run() {

        doNothing(500);
        this.setIcon(growingIcons[0]);
        doNothing(500);
        this.setIcon(growingIcons[1]);
        doNothing(500);
        this.setIcon(growingIcons[2]);
        doNothing(500);
        this.setIcon(growingIcons[3]);
        doNothing(500);
        this.setIcon(growingIcons[4]);
        doNothing(500);
        this.setIcon(growingIcons[5]);
        doNothing(500);
        this.setIcon(icon);
        isGrown = true;
        synchronized(this)
        {
            while(true)
            {
                try{this.wait();}
                catch(Exception ex){System.out.println("error");}
                isSelected = false;
                repaint();
                detectRoute();
                startMove();
                resetSelectedSoldier();
            }
        }

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
    
    @Override
    public void detectRoute() {
        /**** TODO 11 ****/
        finder = new A1083322_checkpoint7_RouteFinder(parentFrame, parentFrame.getMap()[destinationX][destinationY], parentFrame.getMap()[locationX][locationY], algorithm, parentFrame.getMap());
        route = finder.createRoute();
    }

    @Override
    public void startMove() {
        /**** TODO 12 ****/
        if( finder.routefind == null )
            return;
        for( A1083322_checkpoint7_Block x : finder.routefind)
        {
            doNothing(500);
            if(parentFrame.locationVarify(x.getX(), x.getY(), false))
            {
                locationX = x.getX();
                locationY = x.getY();
                parentFrame.gamePanel.repaint();
            }
            else
                break;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (isSelected && isGrown) //Draw a rectangle border around the selected soldier.
        {
            g.drawRect(0, 0, getImage().getWidth(null) - 1, getImage().getHeight(null) - 1);
        }
    }
    // Description : Set the root frame.
    public void setParentFrame() {
        this.parentFrame = (A1083322_checkpoint7_GameFrame) SwingUtilities.getWindowAncestor(this);
    }

    // Description : Set the destination in grid location format.
    public void setDestination(int destinationX, int destinationY) {
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }

    // Description : Set selected soldier.
    public void setSelectedSoldier() {
        setParentFrame();
        parentFrame.selectedSoldier = this;
    }

    // Description : Reset selected soldier.
    public void resetSelectedSoldier() {
        setParentFrame();
        parentFrame.selectedSoldier = null;
    }
    // Description : Return current position X.
    public int getlocationX() {
        return this.locationX;
    }
    // Description : Set current position X.
    public void setlocationX(int locationX) {
        this.locationX = locationX;
    }
    // Description : Return current position Y.
    public int getlocationY() {
        return this.locationY;
    }
    // Description : Set current position Y.
    public void setlocationY(int locationY) {
        this.locationY = locationY;
    }

    public Image getImage() {
        return this.icon.getImage();
    }
    // Description : Return self.
    public A1083322_checkpoint7_Soldier getSelf(){
        return this;
    }

}
