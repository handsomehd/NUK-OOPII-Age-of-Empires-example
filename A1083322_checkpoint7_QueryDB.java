import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class A1083322_checkpoint7_QueryDB {
    //Description : the obstacle set queried from database.
    private static ArrayList<Integer[]> data  = new ArrayList<Integer[] >();
    //Description : the filename of obstacle image queried from database.
    private static HashMap<Integer, String> typeChar = new HashMap<Integer, String>();
    //Description : the primary key of map in database.
    private static String mapID = "0";

    public static void queryData(ArrayList<Integer[]> data,HashMap<Integer, String> typeChar) {
        final String DATABASE_URL = "jdbc:postgresql://140.127.220.226:5432/oopiickp";
        String QUERY = null;
        try
        {
            Connection conn = DriverManager.getConnection(DATABASE_URL, "fallckp", "2021OOPIIpwd");
            Statement statement = conn.createStatement();
            ResultSet resultSet = null;
            int ColumnNumber = 0;
            //取得障礙物座標
            QUERY = "SELECT obstacle_info.x_coordinate, obstacle_info.y_coordinate, obstacle_info.obstacle_type "+
                    "FROM obstacle_info "+
                    "WHERE obstacle_info.map_id="+mapID;
            resultSet = statement.executeQuery(QUERY);
            Integer[] obs_number = new Integer[3];
            while(resultSet.next())
            {
                obs_number = new Integer[3];
                obs_number[0] = resultSet.getInt(1);
                obs_number[1] = resultSet.getInt(2);
                obs_number[2] = resultSet.getInt(3);
                data.add(obs_number);
            }

           //取得障礙物類型
           QUERY = "SELECT obstacle_type, display "+
                   "FROM obstacle_style ";
           resultSet = statement.executeQuery(QUERY);

           while(resultSet.next())
           {
               typeChar.put(resultSet.getInt(1),resultSet.getString(2));
           }
        }
        catch(Exception err)
        {
            err.printStackTrace(System.err);
        }
    }
    public ArrayList getObstacle(){
        return this.data;
    }
    public void setObstacle(ArrayList<Integer[]> data){
        this.data = data;
    }
    public String getMapID(){
        return this.mapID;
    }
    public void setMapID(String mapID){
        this.mapID = mapID;
    }
    public HashMap getObstacleImg(){
        return this.typeChar;
    }
    public void setObstacleImg(HashMap<Integer, String> typeChar){
        this.typeChar = typeChar;
    }
}
