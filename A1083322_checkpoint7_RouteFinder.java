import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class A1083322_checkpoint7_RouteFinder {
    //Description : The target block.
    private A1083322_checkpoint7_Block target;
    //Description : The hashmap that records the parent block.
    private HashMap<A1083322_checkpoint7_Block, A1083322_checkpoint7_Block> ParentBlock;
    //Description : Record which block has been visited.
    private boolean[][] visited ;
    // Description : The root frame.
    private A1083322_checkpoint7_GameFrame parentFrame;
    //Description : the map with all blocks.
    //You can get the location block you want with typing map[x][y].
    private A1083322_checkpoint7_Block[][] map;
    //Description : record the cost if you go on the block.
    public static HashMap<A1083322_checkpoint7_Block, Integer> accumulatedCost;
    // Description : The route searching algorithm.
    private int algorithm;
    private A1083322_checkpoint7_Fringe fringe;
    private static final int DFS = 0;
    private static final int BFS = 1;
    private static final int UCS = 2;
    public static ArrayList<A1083322_checkpoint7_Block> routefind;
    public A1083322_checkpoint7_RouteFinder(A1083322_checkpoint7_GameFrame parentFrame, A1083322_checkpoint7_Block target, A1083322_checkpoint7_Block root,int algorithm, A1083322_checkpoint7_Block[][] map){
        
        this.target = target;
        this.ParentBlock = new HashMap<A1083322_checkpoint7_Block, A1083322_checkpoint7_Block>();
        this.parentFrame = parentFrame;
        this.visited = new boolean[4096 / 256][4096 / 256];
        accumulatedCost = new HashMap<A1083322_checkpoint7_Block, Integer>();
        this.algorithm = algorithm;
        this.map = map;
        for(int x = 0 ; x < 4096 / 256; x++ ){
            for(int y = 0 ; y < 4096 / 256; y++ ){
                visited[x][y] = false;
            }
        }
        /**** TODO 1 ****/
        if( algorithm == 0 )
            fringe = new A1083322_checkpoint7_BlockStack();
        else if( algorithm == 1 )
            fringe = new A1083322_checkpoint7_BlockQueue();
        else if( algorithm == 2 )
            fringe = new A1083322_checkpoint7_BlockPriorityQueue(new Comparator<A1083322_checkpoint7_Block>() {
                @Override
                public int compare(A1083322_checkpoint7_Block b1, A1083322_checkpoint7_Block b2) {
                    int b1_cost = A1083322_checkpoint7_RouteFinder.accumulatedCost.get(b1);
                    int b2_cost = A1083322_checkpoint7_RouteFinder.accumulatedCost.get(b2);
                    return b1_cost - b2_cost;
                }
            });
        fringe.add(root);
        accumulatedCost.put(root, root.getCost());
        routefind = new ArrayList<>();
    }
    private A1083322_checkpoint7_Block search(){
        
        A1083322_checkpoint7_Block current;
        while(!fringe.isEmpty())
        {  
            current = fringe.remove();
            visited[current.getX()][current.getY()] = true;
            System.out.println("Searching at ("+Integer.toString(current.getX())+", "+Integer.toString(current.getY())+")");
            if(current == target)
            {
                return current;
            }
            else
            {
                for( A1083322_checkpoint7_Block x : expand(current, ParentBlock, visited))
                {
                    ParentBlock.put(x, current);
                    accumulatedCost.put(x, accumulatedCost.get(ParentBlock.get(x)) + x.getCost());
                    fringe.add(x);
                }
            }
        }
        return null;
    }
    private ArrayList<A1083322_checkpoint7_Block> expand(A1083322_checkpoint7_Block currentBlock,HashMap<A1083322_checkpoint7_Block, A1083322_checkpoint7_Block> ParentBlock, boolean[][] visited){
        ArrayList<A1083322_checkpoint7_Block> ans = new ArrayList<A1083322_checkpoint7_Block>();
        A1083322_checkpoint7_Block NORTH = null;
        A1083322_checkpoint7_Block WEST = null;
        A1083322_checkpoint7_Block SOUTH = null;
        A1083322_checkpoint7_Block EAST = null;
        int x = currentBlock.getX();
        int y = currentBlock.getY();

        if( y-1 > -1 && visited[x][y-1]!=true && A1083322_checkpoint7_GameFrame.locationVarify(x, y-1, false))                     //上
        {
            NORTH = map[x][y-1];
            ans.add(NORTH);
            visited[x][y-1] = true;
        }
        if( x-1 > -1 && visited[x-1][y]!=true && A1083322_checkpoint7_GameFrame.locationVarify(x-1, y, false))                     //左
        {
            WEST = map[x-1][y];
            ans.add(WEST);
            visited[x-1][y] = true;
        }
        if( y+1 < 16 && visited[x][y+1]!=true && A1083322_checkpoint7_GameFrame.locationVarify(x, y+1, false))                     //下
        {
            SOUTH = map[x][y+1];
            ans.add(SOUTH);
            visited[x][y+1] = true;
        }
        if( x+1 < 16 && visited[x+1][y]!=true && A1083322_checkpoint7_GameFrame.locationVarify(x+1, y, false))                     //右
        {
            EAST = map[x+1][y];
            ans.add(EAST);
            visited[x+1][y] = true;
        }

        return ans;
    }

    public A1083322_checkpoint7_RouteLinkedList createRoute(){
        
        A1083322_checkpoint7_Block start = ParentBlock.get(search());
        while(start!=null)
        {
            routefind.add(start);
            start = ParentBlock.get(start);
        }
        
        Collections.reverse(routefind);
        routefind.add(target);
        routefind.remove(0);
        return null;
    }
}
