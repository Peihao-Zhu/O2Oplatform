package com.example.jaden.thefirst;

import android.database.SQLException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.logging.Logger;

public class MysqlControl {
    //远程数据库账号
    public static final String SQLITEURL = "jdbc:mysql://192.168.6.221:3306/test";
    //远程数据库账号
    public static final String SQLITEUSER = "root";
    //远程数据库密码
    public static final String SQLITEPW = "zph123";

    public static Connection connection=null;

    public static void main(String[] args) throws SQLException, java.sql.SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(SQLITEURL,SQLITEUSER,SQLITEPW);
            if (connection == null) {
                connection = (Connection) DriverManager.getConnection(SQLITEURL, SQLITEUSER, SQLITEPW);
            }
        } catch (ClassNotFoundException e) {
            Logger.getLogger("连接数据库错误=========" + e.toString());
            e.printStackTrace();
        } catch (SQLException e) {
            Logger.getLogger("数据库错误=========" + e.toString());
            e.printStackTrace();
            String name="猪头";
            String sex="男";
            //sql语句
            String sqlMessage = "insert into people values(name,sex)";
            //获取查询的字段
            String keyWord = "";
            //            PreparedStatement preparedStatement = (PreparedStatement)         connection.prepareStatement(sqlMessage);
            //            preparedStatement.setString(1,"");
            //            preparedStatement.setString(2,"");
            //            preparedStatement.setString(3,"");
            //            // 执行更新操作
            //            preparedStatement.executeUpdate();
            Statement statement = (Statement) connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sqlMessage);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columns = resultSetMetaData.getColumnCount();
            //显示列,表格的表头
            for (int i = 1; i <= columns; i++) {
                System.out.print(resultSetMetaData.getColumnName(i));
                System.out.print("\t\t");
            }

            System.out.println();
            //显示表格内容
            while (resultSet.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(resultSet.getString(i));
                    System.out.print("\t\t");
                }
                System.out.println();
            }

        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}
