package com.xhystc.cozy;

import com.xhystc.cozy.core.Session;
import com.xhystc.cozy.exception.CozyException;
import com.xhystc.cozy.filter.ConnectionFilter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xiehongyang
 *
 */
public class Cozy
{
    static private Map<String, DataSource> dataSourceMap = new HashMap<>();
    static private Map<String,LinkedList<ConnectionFilter>> filters = new HashMap<>();
    static public void register(String key,DataSource dataSource){
        dataSourceMap.put(key,dataSource);
    }

    static public Session createSession(String key){
        if(!dataSourceMap.containsKey(key)){
            throw new IllegalArgumentException("key:"+key+" not found");
        }
        try {
            Connection connection = dataSourceMap.get(key).getConnection();
            connection = doFilter(key,connection);
            return new Session(connection);
        } catch (SQLException e) {
            throw new CozyException(e);
        }
    }

    static public void addFilter(String key,ConnectionFilter filter){
        if(!filters.containsKey(key)){
            filters.put(key,new LinkedList<ConnectionFilter>());
        }
        filters.get(key).add(filter);
    }
    static private Connection doFilter(String key,Connection connection) throws SQLException {
        Connection ret = connection;
        List<ConnectionFilter> filterList = filters.get(key);
        if(filterList != null){
            for(ConnectionFilter connectionFilter : filterList){
                ret = connectionFilter.filter(ret);
            }
        }
        return ret;
    }
}


