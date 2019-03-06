package com.xhystc.cozy.filter;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author xiehongyang
 * @date 2018/12/16 3:26 PM
 */
public interface ConnectionFilter
{
    Connection filter(Connection connection) throws SQLException;
}
