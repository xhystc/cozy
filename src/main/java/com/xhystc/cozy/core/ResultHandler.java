package com.xhystc.cozy.core;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xiehongyang
 *
 */
public interface ResultHandler<T>
{
    T handle(ResultSet resultSet) throws Exception;
}
