package com.soar.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        Properties properties = new Properties();
        //通过类加载器得到当前baseDao的路径，紧接着得到db.properties的输入流
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            //加载驱动
            Class.forName(driver);
            //获取连接对象
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    //编写查询公共类
    public static ResultSet execute(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet, String sql, Object[] paramas) throws Exception {
        //获取预编译对象,sql在后面直接执行就行
        preparedStatement = connection.prepareStatement(sql);
        //得到参数
        for (int i = 0; i < paramas.length; i++) {
            //setObject,占位符从1开始，数组从零开始
            preparedStatement.setObject(i + 1, paramas[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    //编写增删改公共方法
    public static int execute(Connection connection, PreparedStatement preparedStatement, String sql, Object[] paramas) throws Exception {
        //获取预处理对象
        preparedStatement = connection.prepareStatement(sql);
        //得到参数
        for (int i = 0; i < paramas.length; i++) {
            //setObject,占位符从1开始，数组从零开始
            preparedStatement.setObject(i + 1, paramas[i]);
        }
        int updateRows = preparedStatement.executeUpdate();
        return updateRows;
    }

    //释放资源
    public static boolean closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        boolean flag = true;
        if (resultSet != null) {
            try {
                resultSet.close();
                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;

    }
}
