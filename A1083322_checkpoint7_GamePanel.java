
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class A1083322_checkpoint7_GamePanel extends JPanel {
    //Description : the obstacle location set in GUI index version.
    private ArrayList<Integer[]> obstacleList;
    //Description : the obstacle images set. bar_id -> obstacle image
    private HashMap<Integer,Image> obstacleImg = new HashMap<>();
    //Description : the image object of the map.
    private Image mapImg = new ImageIcon("Resource/map.png").getImage();
    //Description : the displaysize of the map
    private int scaler;
    private ArrayList<A1083322_checkpoint7_House> houseList = new ArrayList<A1083322_checkpoint7_House>();
    private ArrayList<A1083322_checkpoint7_Barrack> barrackList = new ArrayList<A1083322_checkpoint7_Barrack>();
    private ArrayList<A1083322_checkpoint7_Pyramid> pyramidList = new ArrayList<A1083322_checkpoint7_Pyramid>();
    private ArrayList<A1083322_checkpoint7_Soldier> soldierList = new ArrayList<A1083322_checkpoint7_Soldier>();
    //Description : the normal image size.
    //Hint : while the mapsize is not normal size, you have to think of the displaysize.
    private int originalGridLen = 256;
    //Description : the image displaysize.
    private int gridLen;
    //Description : the map center point x-axis location.
    //Hint : While dragging the map, you may need to set the map location via this.
    private Integer centerX = 0;
    //Description : the map center point y-axis location.
    //Hint : While dragging the map, you may need to set the map location via this.
    private Integer centerY = 0; 

    public A1083322_checkpoint7_GamePanel(ArrayList<A1083322_checkpoint7_House> houseList, ArrayList<A1083322_checkpoint7_Barrack> barrackList, ArrayList<A1083322_checkpoint7_Pyramid> pyramidList, ArrayList<Integer[]> obstacleList,HashMap<Integer,Image> obstacleImg,int scaler, ArrayList<A1083322_checkpoint7_Soldier> soldierList){
        this.obstacleList = obstacleList;
        this.scaler = scaler;   
        this.obstacleImg = obstacleImg;
        this.houseList = houseList;
        this.barrackList = barrackList;
        this.pyramidList = pyramidList;
        this.soldierList = soldierList;
        gridLen = originalGridLen/scaler;
        
        //依照scaler更換起始中央點
        centerX = 2048/scaler;
        centerY = 2048/scaler;
    }

    //Description : While painting this JPanel, we draw map on the given location and other obstacles.
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        //找到地圖起始座標
        Integer MapX = 0-(centerX-250);
        Integer MapY = 0-(centerY-250);

        //g.drawImage(圖片, 繪製起點x, 繪製起點y, 繪製大小x, 繪製大小y, null)
        g.drawImage(mapImg, MapX, MapY, 4096/scaler, 4096/scaler, null);

        //障礙物xy位移量
        int obsmove = 256/scaler;

        for( int n=0; n<obstacleList.size(); n++)
        {
            g.drawImage(obstacleImg.get(obstacleList.get(n)[2]), MapX+obstacleList.get(n)[0]*obsmove, MapY+obstacleList.get(n)[1]*obsmove, 256/scaler, 256/scaler, null);
        }
        for( A1083322_checkpoint7_Soldier soldier : soldierList)
        {
            soldier.setLocation(MapX+soldier.getlocationX()*obsmove, MapY+soldier.getlocationY()*obsmove);
        }
        for( A1083322_checkpoint7_Barrack x : barrackList)
        {
            x.setLocation(MapX+x.getlocationX()*obsmove, MapY+x.getlocationY()*obsmove);
        }
        
        for( A1083322_checkpoint7_House x : houseList)
        {
            x.setLocation(MapX+x.getlocationX()*obsmove, MapY+x.getlocationY()*obsmove);
        }

        for( A1083322_checkpoint7_Pyramid x : pyramidList)
        {
            x.setLocation(MapX+x.getlocationX()*obsmove, MapY+x.getlocationY()*obsmove);
        }
    }
    
    public Integer getCenterX(){
        return this.centerX;
    }
    public void setCenterX(Integer centerX){
        this.centerX = centerX;
    }
    public Integer getCenterY(){
        return this.centerY;
    }
    public void setCenterY(Integer centerY){
        this.centerY = centerY;
    }
    public Integer getGridLen(){
        return this.gridLen;
    }
    public void setHouseList(ArrayList<A1083322_checkpoint7_House> houseList){
        this.houseList = houseList;
    }
    public void setBarrackList(ArrayList<A1083322_checkpoint7_Barrack> barrackList){
        this.barrackList = barrackList;
    }
    public void setPyramidList(ArrayList<A1083322_checkpoint7_Pyramid> pyramidList){
        this.pyramidList = pyramidList;
    }
    public void addToSoldierList(A1083322_checkpoint7_Soldier soldier){
        this.soldierList.add(soldier);
    }
    public ArrayList<A1083322_checkpoint7_Soldier> getSoldierList(){
        return soldierList;
    }
}
