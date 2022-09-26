import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class A1083322_checkpoint7_SpawnMenu extends JPanel {
    // Description : The root frame.
    private A1083322_checkpoint7_GameFrame parentFrame;
    // Description : Base location for spawning the soldier.
    private int baseLocationX,baseLocationY;

    public A1083322_checkpoint7_SpawnMenu() {
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton soldierButton = new JButton("Soldier");
        soldierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(parentFrame.locationVarify(baseLocationX, baseLocationY+1, false))
                {
                    if(baseLocationX > 15 || baseLocationY+1 > 15)
                    {
                        closeMenu();
                        return;
                    }
                    A1083322_checkpoint7_Soldier s = new A1083322_checkpoint7_Soldier(baseLocationX, baseLocationY+1, parentFrame.jfScaler, SwingConstants.CENTER, parentFrame.algorithm);
                    parentFrame.soldierList.add(s);
                    parentFrame.gamePanel.addToSoldierList(s);
                    parentFrame.gamePanel.add(s);
                    Thread t = new Thread(s);
                    t.start();
                    parentFrame.gamePanel.revalidate();
                    parentFrame.gamePanel.repaint();
                }
                closeMenu();

            }
        });
        this.add(soldierButton);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setVisible(false);


}
    // Description : To set the base location for spawning the soldier.
    public void setBase(int baseLocationX,int baseLocationY){
        this.baseLocationX=baseLocationX;
        this.baseLocationY=baseLocationY;
    }
    // Description : To set root frame.
    public void setParentFrame(){
        this.parentFrame =(A1083322_checkpoint7_GameFrame) SwingUtilities.getWindowAncestor(this);
    }
    // Description : Close the spawnMenu.
    private void closeMenu() {
        parentFrame.spawnMenu.setVisible(false);
    }
    // Description : Open the spawnMenu.
    private void showMenu() {
        parentFrame.spawnMenu.setVisible(true);
    }

}
