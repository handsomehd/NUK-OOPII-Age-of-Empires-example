import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class A1083322_checkpoint7_GameFrame extends JFrame {
    //Description : Width of Frame
    private int FWidth;
    //Description : Height of Frame
    private int FHeight;
    //Description : the displaysize of the map
    public static int jfScaler = 2;
    //Description : the obstacle images set. bar_id -> obstacle image
    private HashMap<Integer,Image> obstacleImg = new HashMap<>();
    //Description : the filenames  of the obstacle image set.  bar_id -> filename 
    private HashMap<Integer, String> typeChar = new HashMap<Integer, String>();
    //Description : the obstacle location set queryed from database
    private ArrayList<Integer[]> obstacleDataStructure; 
    //Description : the obstacle location set in GUI index version.
    private static ArrayList<Integer[]> obstacleList;
    //Description : the object to query data.
    private A1083322_checkpoint7_QueryDB querydb;
    private static ArrayList<A1083322_checkpoint7_House> houseList = new ArrayList<A1083322_checkpoint7_House>();
    private static ArrayList<A1083322_checkpoint7_Barrack> barrackList = new ArrayList<A1083322_checkpoint7_Barrack>();
    private static ArrayList<A1083322_checkpoint7_Pyramid> pyramidList = new ArrayList<A1083322_checkpoint7_Pyramid>();
    public static ArrayList<A1083322_checkpoint7_Soldier> soldierList = new ArrayList<A1083322_checkpoint7_Soldier>();
    private static int PressedX = 0;
    private static int PressedY = 0;
    private static int ReleasedX = 0;
    private static int ReleasedY = 0;
    private static int ClickedX = 0;
    private static int ClickedY = 0;
    private static int keytype = 1;
    private static int MoveX = 0;
    private static int MoveY = 0;
    // Description : The main panel.
    public static A1083322_checkpoint7_GamePanel gamePanel;
    // Description : The UI panel of spawnMenu.
    public static A1083322_checkpoint7_SpawnMenu spawnMenu;
    // Description : The soldier that is selected.
    public A1083322_checkpoint7_Soldier selectedSoldier;
    
    //Description : the cost of sand weight;
    private final int GRASSWEIGHT = 3;
    //Description : the cost of space weight;
    private final int SAPCEWEIGHT = 1;
    //Description : the map with all blocks.
    //You can get the location block you want with typing map[x][y].
    private A1083322_checkpoint7_Block[][] map;
    // Description : The route searching algorithm.
    public static int algorithm;

    public A1083322_checkpoint7_GameFrame(int FWidth, int FHeight,String mapID,int jfScaler,int algorithm) throws HeadlessException {
        this.FWidth = FWidth;
        this.FHeight = FHeight;
        this.setTitle("Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(FWidth, FHeight);
        this.jfScaler = jfScaler;
        this.obstacleList = new ArrayList<Integer[]>();
        this.obstacleDataStructure = new ArrayList<Integer[]>();
        this.querydb = new A1083322_checkpoint7_QueryDB();
        this.querydb.setMapID(mapID);
        this.setFocusable(true);
        this.algorithm = algorithm;

        //儲存障礙物資料
        querydb.queryData(obstacleDataStructure, typeChar);

        //讀取障礙物圖片
        Image obsimg = null;
        obsimg = new ImageIcon("Resource/coconut_tree.png").getImage();
        obstacleImg.put(0,obsimg);
        obsimg = new ImageIcon("Resource/dinosaur.png").getImage();
        obstacleImg.put(1,obsimg);
        obsimg = new ImageIcon("Resource/grass.png").getImage();
        obstacleImg.put(2,obsimg);
        obsimg = new ImageIcon("Resource/house.png").getImage();
        obstacleImg.put(3,obsimg);
        obsimg = new ImageIcon("Resource/pine_tree.png").getImage();
        obstacleImg.put(4,obsimg);
        obsimg = new ImageIcon("Resource/stone.png").getImage();
        obstacleImg.put(5,obsimg);
        obsimg = new ImageIcon("Resource/worker.png").getImage();
        obstacleImg.put(6,obsimg);

        //轉換障礙物座標
        toGUIIdx(obstacleDataStructure, obstacleList);

        /**** TODO 1 ****/
        map = createMap(16, 16);

        this.setLayout(new BorderLayout());
        spawnMenu = new A1083322_checkpoint7_SpawnMenu();
        spawnMenu.setParentFrame();
        add(spawnMenu, BorderLayout.SOUTH);

        //宣告panel
        gamePanel = new A1083322_checkpoint7_GamePanel(houseList, barrackList, pyramidList, obstacleList, obstacleImg, jfScaler, soldierList);
        
        this.addComponentListener(new ComponentAdapter() {
            @Override
            //Description : While resizing the windows, the evnet will be happenned(Reset the location of player).
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                if(houseList.size()!=0){
                    int x = gamePanel.getWidth()/2-gamePanel.getCenterX();
                    int y = gamePanel.getHeight()/2-gamePanel.getCenterY();
                    for(A1083322_checkpoint7_House house : houseList){
                        house.setLocation(x + house.getlocationX()*gamePanel.getGridLen(),y + house.getlocationX()*gamePanel.getGridLen());
                    }    
                }
            }
        });

        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke)
            {
                keytype = ke.getKeyCode();
            }
        });
        
        gamePanel.addMouseListener(new MouseAdapter() {
            //Description : the event happenned while mouse be pressed.
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                PressedX = e.getX();
                PressedY = e.getY();
            }
            //Description : the event happenned while mouse be released
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                ReleasedX = e.getX();
                ReleasedY = e.getY();
                int mapmoveX = ReleasedX - PressedX;
                int mapmoveY = ReleasedY - PressedY;

                gamePanel.setCenterX(gamePanel.getCenterX()-mapmoveX);
                gamePanel.setCenterY(gamePanel.getCenterY()-mapmoveY);
                gamePanel.repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e){
            /**** TODO 2 ****/
                super.mouseClicked(e);
                //計算點擊到網格的座標
                int grid = 256/jfScaler;//一格有多大
                int mapx = 0 - (2048/jfScaler - 250);//地圖的起始點x
                int mapy = 0 - (2048/jfScaler - 250);//地圖的起始點y

                ClickedX = e.getX() + MoveX - mapx;
                ClickedY = e.getY() + MoveY - mapy;

                if(ClickedX < 0)
                    ClickedX = -1;
                else if(ClickedX >= 0)
                    ClickedX = ClickedX/grid;

                if(ClickedY < 0)
                    ClickedY = -1;
                else if(ClickedY >= 0)
                    ClickedY = ClickedY/grid;

                if(selectedSoldier != null) //移動士兵
                {
                    if(!locationVarify(ClickedX, ClickedY, false))
                    {
                        selectedSoldier.isSelected = false;
                        selectedSoldier.repaint();
                        selectedSoldier = null;
                        return;
                    }
                    selectedSoldier.setDestination(ClickedX, ClickedY);
                    synchronized(selectedSoldier){selectedSoldier.notify();}
                }
                else //建築
                {
                    if(locationVarify(ClickedX, ClickedY, true))
                        gamePanel.revalidate();
                }
                repaint();
            }
        });
        
        gamePanel.addMouseMotionListener(new MouseAdapter(){
            //Description : the event happenned while mouse be dragged.
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                ReleasedX = e.getX();
                ReleasedY = e.getY();
                int mapmoveX = ReleasedX - PressedX;
                int mapmoveY = ReleasedY - PressedY;
                gamePanel.setCenterX(gamePanel.getCenterX()-mapmoveX);
                gamePanel.setCenterY(gamePanel.getCenterY()-mapmoveY);
                gamePanel.repaint();
                MoveX = MoveX - (ReleasedX - PressedX);
                MoveY = MoveY - (ReleasedY - PressedY);
                PressedX = e.getX();
                PressedY = e.getY();
            }
        });
        add(gamePanel, BorderLayout.CENTER);
    }
    
    //Description : transform the obstacle location from database version to GUI index version
    //              data is the database one, and the other.
    public static void toGUIIdx(ArrayList<Integer[]> data,ArrayList<Integer[]> dataGui){
        for(Integer[] x : data){
            dataGui.add( new Integer[]{x[0]-1, x[1]-1, x[2]} );
        }
    }
    /**** TODO 2 草地不視為障礙物 ****/

    public static boolean locationVarify(int locationX,int locationY,boolean isBuilding)
    {
        if(locationX < 0 || locationY < 0 || locationX > 15 || locationY > 15)
            return false;
        if(isBuilding == true)
        {
            for(Integer[] x : obstacleList)
                if(x[0] == locationX && x[1] == locationY && x[2]!=2)
                    return false;
            for(A1083322_checkpoint7_House x : houseList)
                if(x.getlocationX() == locationX && x.getlocationY()== locationY)
                    return false;
            for(A1083322_checkpoint7_Barrack x : barrackList)
                if(x.getlocationX() == locationX && x.getlocationY()== locationY)
                    return false;
            for(A1083322_checkpoint7_Soldier x : soldierList)
                if(x.getlocationX() == locationX && x.getlocationY()== locationY)
                    return false;
            if(Pyramidcheck(ClickedX, ClickedY, pyramidList))
                return false;
            if(KeyEvent.getKeyText(keytype).equals("B"))
            {
                String bk = Integer.toString(barrackList.size());
                A1083322_checkpoint7_Barrack barrack = new A1083322_checkpoint7_Barrack(ClickedX, ClickedY, bk, jfScaler, SwingConstants.CENTER);
                barrackList.add(barrack);
                gamePanel.add(barrack);
                gamePanel.setBarrackList(barrackList);
                Thread t = new Thread(barrack);
                t.start();
            }
            else if(KeyEvent.getKeyText(keytype).equals("H") || keytype == 1)
            {
                String hs = Integer.toString(houseList.size());
                A1083322_checkpoint7_House house = new A1083322_checkpoint7_House(ClickedX, ClickedY, hs, jfScaler, SwingConstants.CENTER);
                houseList.add(house);
                gamePanel.add(house);
                gamePanel.setHouseList(houseList);
                Thread t = new Thread(house);
                t.start();
            }
            else
            {
                if(locationX + 1 == 16 || locationY + 1 == 16)
                    return false;
                if(Pyramidcheckself(locationX, locationY, pyramidList, houseList, barrackList, obstacleList))
                    return false;
                String pr = Integer.toString(pyramidList.size());
                A1083322_checkpoint7_Pyramid pyramid = new A1083322_checkpoint7_Pyramid(locationX, locationY, pr, jfScaler, SwingConstants.CENTER);
                pyramidList.add(pyramid);
                gamePanel.add(pyramid);
                gamePanel.setPyramidList(pyramidList);
                Thread t = new Thread(pyramid);
                t.start();
            }
            return true;
        }
        else
        {
            for(Integer[] x : obstacleList)
                if(x[0] == locationX && x[1] == locationY && x[2]!=2)
                    return false;
            for(A1083322_checkpoint7_House x : houseList)
                if(x.getlocationX() == locationX && x.getlocationY()== locationY)
                    return false;
            for(A1083322_checkpoint7_Barrack x : barrackList)
                if(x.getlocationX() == locationX && x.getlocationY()== locationY)
                    return false;
            for(A1083322_checkpoint7_Soldier x : soldierList)
                if(x.getlocationX() == locationX && x.getlocationY()== locationY)
                    return false;
            for(A1083322_checkpoint7_Pyramid x : pyramidList)
                if((x.getlocationX() == locationX && x.getlocationY()== locationY) || (x.getlocationX()+1 == locationX && x.getlocationY()== locationY) || (x.getlocationX() == locationX && x.getlocationY()+1== locationY) || (x.getlocationX()+1 == locationX && x.getlocationY()+1== locationY))
                    return false;
            return true;
        }
    }

    //Description : create the map, if each of the loaction will be tag as "grass", "obstacle" or "space".
    public A1083322_checkpoint7_Block[][] createMap(int height,int width){
        A1083322_checkpoint7_Block[][] map = new A1083322_checkpoint7_Block[width][height];
        for (Integer[] block: obstacleList){
            int cost = block[2] == 2 ? GRASSWEIGHT : 100;
            String type = block[2] == 2 ? "grass" : "obstacle";
            map[block[0]][block[1]] = new A1083322_checkpoint7_Block(block[0], block[1], type, cost);
        }
        for(int x = 0; x<width; x++){
            for(int y = 0; y<height; y++){
                if(map[x][y] == null){
                    map[x][y] = new A1083322_checkpoint7_Block(x,y,"space",SAPCEWEIGHT);
                }
            }
        }
        return map;
    }
    public A1083322_checkpoint7_Block[][] getMap(){
        return this.map;
    }
    
    public static boolean Pyramidcheck(int cx, int cy, ArrayList<A1083322_checkpoint7_Pyramid> pyramidList)
    {
        for( A1083322_checkpoint7_Pyramid x : pyramidList )
        {
            int ox = x.getlocationX();
            int oy = x.getlocationY();
            if( ox == cx && oy == cy)
                return true;
            if( ox == cx-1 && oy == cy-1)
                return true;
            if( ox == cx && oy == cy-1)
                return true;
            if( ox == cx-1 && oy == cy)
                return true;
        }
        return false;
    }
    public static boolean Pyramidcheckself(int cx, int cy, ArrayList<A1083322_checkpoint7_Pyramid> pyramidList, ArrayList<A1083322_checkpoint7_House> houseList, ArrayList<A1083322_checkpoint7_Barrack> barrackList, ArrayList<Integer[]> obstacleList)
    {
        int ox = 0;
        int oy = 0;

        for(Integer[] x : obstacleList)
        {
            if(x[0] == cx+1 && x[1] == cy+1)
                return true;
            if(x[0] == cx && x[1] == cy+1)
                return true;
            if(x[0] == cx+1 && x[1] == cy)
                return true;
        }
        for( A1083322_checkpoint7_House x : houseList )
        {
            ox = x.getlocationX();
            oy = x.getlocationY();
            if( ox == cx+1 && oy == cy+1)
                return true;
            if( ox == cx+1 && oy == cy)
                return true;
            if( ox == cx && oy == cy+1)
                return true;
        }
        for( A1083322_checkpoint7_Barrack x : barrackList )
        {
            ox = x.getlocationX();
            oy = x.getlocationY();
            if( ox == cx+1 && oy == cy+1)
                return true;
            if( ox == cx+1 && oy == cy)
                return true;
            if( ox == cx && oy == cy+1)
                return true;
        }
        
        for( A1083322_checkpoint7_Soldier x : gamePanel.getSoldierList() )
        {
            ox = x.getlocationX();
            oy = x.getlocationY();
            if( ox == cx+1 && oy == cy+1)
                return true;
            if( ox == cx+1 && oy == cy)
                return true;
            if( ox == cx && oy == cy+1)
                return true;
        }
        
        for( A1083322_checkpoint7_Pyramid x : pyramidList )
        {
            ox = x.getlocationX();
            oy = x.getlocationY();
            if( ox == cx+1 && oy == cy+1)
                return true;
            if( ox == cx+1 && oy == cy)
                return true;
            if( ox == cx && oy == cy+1)
                return true;
            if( ox == cx-1 && oy == cy-1)
                return true;
            if( ox == cx-1 && oy == cy)
                return true;
            if( ox == cx && oy == cy-1)
                return true;
            if( ox == cx+1 && oy == cy-1)
                return true;
            if( ox == cx-1 && oy == cy+1)
                return true;
        }
        return false;        
    }
}
