package db;

import java.sql.*;

/**
 * Created by dell on 2020/10/18.
 *
 * 数据可连接模块
 */
public class dbConn {

    public dbConn(){
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Statement conn(){

        try {
            //加载驱动
            Class.forName("com.mysql.jdbc.Driver");

            //数据库信息
            String url = "jdbc:mysql://localhost:3306/DumpStructure_and_Data_1";
            String user = "root";
            String pwd ="19990130";

            //连接
            Connection con = null;
            con = DriverManager.getConnection(url, user, pwd);
            Statement stat = con.createStatement();
            return stat;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    //查询数据库
    public ResultSet getRs(String sql){

        try {
            Statement state = conn();
            ResultSet rs = state.executeQuery(sql);
            //System.out.println(rs.toString());
            return rs;
        } catch (SQLException e) {
            System.out.println("---" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    //更新数据
    public int getUpdate(String sql){
        Statement stat = conn();
        int i = -1;
        try {
            i = stat.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    private void jbInit() throws Exception{
        conn();
    }
}
