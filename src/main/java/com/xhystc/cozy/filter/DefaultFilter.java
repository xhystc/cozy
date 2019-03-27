package com.xhystc.cozy.filter;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author xiehongyang
 *
 */
public class DefaultFilter implements ConnectionFilter
{
    @Override
    public Connection filter(Connection connection) throws SQLException {
        connection.setAutoCommit(true);
        return connection;
    }
}
