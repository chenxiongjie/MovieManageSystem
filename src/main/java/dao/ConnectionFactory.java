/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.awt.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author caster
 */
public class ConnectionFactory {
    private static final String driver;
    private static final String url;
    private static final String user;
    private static final String password;

    static {
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://127.0.0.1:3306/movie?useSSL=false";
        user = "root";
        password = "root";
    }
    
    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rs;
            
    /**
     * 获取连接
     * */
    public static Connection getConnection() throws Exception {
        Class.forName(driver);
        conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * 释放资源
     * */
    public static void close() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
    
    public static void close(ResultSet rs, PreparedStatement pstmt,
            Connection conn) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    /**
     * excute create or update sql
     * @param sql
     * @return
     */
    public static long queryReturnPrimaryKey(String sql) {
        long generateKey = 0;
        String[] keysName = {"id"};
        try {
            Connection connection = null;
            PreparedStatement pstmt = null;
            try {
                connection = ConnectionFactory.getConnection();
                pstmt = connection.prepareStatement(sql, keysName);
                pstmt.executeUpdate();
                
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                while (generatedKeys.next()) {
                      generateKey = generatedKeys.getLong(1);
                }
            } finally {
                ConnectionFactory.close(null, pstmt, connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return generateKey;
    }
    
    /**
     * excute delete sql
     * @param sql
     * @return Boolean
     */
    public static Boolean queryReturnBoolean (String sql) {
        try {
            Connection connection = null;
            PreparedStatement pstmt = null;
            try {
                connection = ConnectionFactory.getConnection();
                pstmt = connection.prepareStatement(sql);
                int rows = pstmt.executeUpdate();
                
                if (rows > 0) {
                    return true;
                }
            } finally {
                ConnectionFactory.close(null, pstmt, connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    
    /**
     * excute select sql
     * @param sql
     * @return ArrayList
     */
    public static ArrayList queryReturnArrayList(String sql) {    
        ArrayList results = new ArrayList();
        
        try {
            ResultSet rs = null;
            PreparedStatement pstmt = null;
            Connection conn = null;
            try {
                conn = ConnectionFactory.getConnection();
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                results = ConnectionFactory.toList(rs);
            } finally {
                ConnectionFactory.close(rs, pstmt, conn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return results;
    }
    
    /**
     * transfrom ResultSet to ArrayList
     * @param rs
     * @return Arraylist
     * @throws SQLException
     */
    public static ArrayList<Map> toList(ResultSet rs) throws SQLException {
        ArrayList<Map> list = new ArrayList<Map>();
        
        //获取键名
        ResultSetMetaData md = rs.getMetaData();
        
        //获取行的数量
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map rowData = new HashMap();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }
}
