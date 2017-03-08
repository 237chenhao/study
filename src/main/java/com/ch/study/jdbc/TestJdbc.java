package com.ch.study.jdbc;

import java.sql.*;

/**
 * Created by chenhao on 2016/10/28.
 */
public class TestJdbc {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Savepoint sp = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://rdsi2772xzt41b58m16p.mysql.rds.aliyuncs.com:3306/product?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&allowMultiQueries=true",
                    "center","center_123");
            connection.setAutoCommit(false);
            sp = connection.setSavepoint();
            preparedStatement = connection.prepareStatement("INSERT INTO `product`.`upload_task_queue` (`id`, `type`, `status`, `desc`, `create_time`, `finish_time`) VALUES ('170', '1', '1', 'ppppppppppp', '2016-10-11 16:44:17', '2016-10-11 16:44:18');\n");

            preparedStatement.execute();

        } catch (SQLException e) {
            if(connection != null){
                try {
                    connection.rollback(sp);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
