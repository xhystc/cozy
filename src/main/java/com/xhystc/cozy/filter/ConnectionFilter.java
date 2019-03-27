package com.xhystc.cozy.filter;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author xiehongyang
 *
 */
public interface ConnectionFilter
{
    Connection filter(Connection connection) throws SQLException;
}
