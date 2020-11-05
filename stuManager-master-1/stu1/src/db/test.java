package db;

import java.sql.ResultSet;

/**
 * Created by dell on 2020/10/18.
 */
public class test {
    public static void main(String[] args) {
        try {
            dbConn db = new dbConn();
            int i = db.getUpdate("insert into tb_user(userName, userPwd, userType) values('lwk', '123', '2');");
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
