package com.xhystc.cozy.filter;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author xiehongyang
 * @date 2018/12/24 10:36 AM
 */
public class DefaultFilter implements ConnectionFilter
{
    @Override
    public Connection filter(Connection connection) throws SQLException {
        connection.setAutoCommit(true);
        return connection;
    }
}
