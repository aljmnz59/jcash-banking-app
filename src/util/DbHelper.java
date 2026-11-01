package util;

import java.sql.*;

public class DbHelper {
    static void main(String[] args) {
        try{
            String url = "jdbc:mysql://localhost:3306/employees";
            Connection conn = DriverManager.getConnection(url, "root", "");
            System.out.println(conn);
            System.out.println("Connected to the database successfully");
            String positionsql = "select * from position";
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet res = statement.executeQuery(positionsql);
            while(res.next()){
                System.out.println();
                System.out.println(
                        "[" +
                        res.getInt("id") + "," +
                        res.getString("name") + "," +
                        res.getString("description") +
                        "]"
                );

            }
        }
        catch(java.sql.SQLException e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}
