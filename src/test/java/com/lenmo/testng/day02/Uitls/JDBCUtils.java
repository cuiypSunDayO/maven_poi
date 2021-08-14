package com.lenmo.testng.day02.Uitls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtils {

    public static Connection getConnection() {
        //定义数据库连接
        String url=Constants.JDBC_URL;
        String user=Constants.JDBC_USERNAME;
        String password=Constants.JDBC_PASSWORD;
//        String url="jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
//        String user="future";
//        String password="123456";
        //定义数据库连接对象
        Connection conn = null;
        try {
            //你导入的数据库驱动包， mysql。
            conn = DriverManager.getConnection(url, user,password);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static void close(Connection conn)  {
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}

