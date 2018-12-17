package com.xhystc.cozy.core;

import com.xhystc.cozy.exception.CozyException;
import com.xhystc.cozy.filter.ConnectionFilter;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author xiehongyang
 * @date 2018/12/15 2:10 PM
 */
public class Session
{
    static private final Logger logger = Logger.getLogger("cozy");

    private Connection connection;
    private boolean isClose = false;
    private List<Query> queryList = new LinkedList<>();


    public Session(Connection connection){
      this.connection = connection;
    }
    public void transaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new CozyException(e);
        }
    }

    public Query creqteQuery(){
        if(!connectionOpened()){
            throw new CozyException("connection is closed");
        }
        Query query = new Query(connection);
        queryList.add(query);
        return query;
    }

    public void transaction(long timeout){
        transaction();
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask()
        {

            @Override
            public void run() {
                try {
                    if(!connection.isClosed()){
                        for(Query query : queryList){
                            query.cancel();
                        }
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        },timeout);
    }

    public void rollback(){
        if(connectionOpened()){
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new CozyException(e);
            }
            close();
        }
    }

    public void commit(){
        if(connectionOpened()){
            try {
                connection.commit();
            } catch (SQLException e) {
                throw new CozyException(e);
            }
            close();
        }
    }

    private boolean connectionOpened(){
        if(isClose)
            return false;
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    private void close(){
        isClose = true;
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("connection close error");
        }

    }


}
