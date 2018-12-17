package com.xhystc.cozy.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xiehongyang
 * @date 2018/12/16 3:41 PM
 */
public interface ResultHandler<T>
{
    T handle(ResultSet resultSet) throws SQLException;
}
