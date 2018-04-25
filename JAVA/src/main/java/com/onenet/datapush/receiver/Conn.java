package com.onenet.datapush.receiver;

import java.sql.*;

/**简单的jdbc数据库操作
 * Created by chengwengao on 2018/4/22.
 */
public class Conn {
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String jdbc = "jdbc:mysql://127.0.0.1:3306/firedb?characterEncoding=GBK";
    private static String username = "root";
    private static String passwd = "123456";

    public static void main(String[] args) throws Exception{
        Connection conn = DriverManager.getConnection(Conn.jdbc, Conn.username, Conn.passwd);//链接到数据库
        Statement state = conn.createStatement();   //容器
        String sql = "select value, label from sys_dict where type = 'amp_monitor_type'";
        ResultSet rs = state.executeQuery(sql);
        while (rs.next()){
            String value = rs.getString(1);
            String label = rs.getString(2);
            System.out.println("value:"+value+",label:"+label);
        }
        conn.close();
    }
    private static ResultSet select(String sql){
        System.out.println(sql);
        Connection conn = null;
        try {
            Class.forName(driverName);//加载驱动
            conn = DriverManager.getConnection(Conn.jdbc, Conn.username, Conn.passwd);//链接到数据库

            Statement state = conn.createStatement();   //容器
            ResultSet rs = state.executeQuery(sql);     //将sql语句传至数据库，返回的值为一个字符集用一个变量接收

            conn.close();//关闭通道
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            if (null != conn){
                try {
                    conn.close();//关闭通道
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void update(String sql) {
        System.out.println(sql);
        Connection conn = null;
        try {
            Class.forName(driverName);//加载驱动
            conn = DriverManager.getConnection(Conn.jdbc, Conn.username, Conn.passwd);//链接到数据库

            Statement state = conn.createStatement();   //容器
            state.executeUpdate(sql);         //将sql语句上传至数据库执行

            conn.close();//关闭通道
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally {
            if (null != conn){
                try {
                    conn.close();//关闭通道
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
