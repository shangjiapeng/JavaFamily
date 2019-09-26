package com.shang.demo.transaction;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.shang.demo.util.JDBCUtils.getDataSource;


/**
 * 本地事务
 * 在数据库连接中使用本地事务示例如下
 */
public class LocalTransaction {


    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = getDataSource().getConnection();
            //将自动提交设置为false.若设置为true,则数据库将会把每一次数据更新认定为一个事务,并自动提交
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            //处理业务逻辑
            System.out.println("处理业务逻辑");
            //statement.execute("sql");
            connection.commit();

        } catch (SQLException e) {
            //发送异常,回滚事务
            connection.rollback();
            //事务回滚,转账的两步操作完全撤销
            statement.close();
            connection.close();
        }
    }


}
