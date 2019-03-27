package com.xhystc.cozy.core;

import com.xhystc.cozy.exception.CozyException;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.*;

/**
 * @author xiehongyang
 *
 */

//todo sql语句foreach之类的组织方式,batch,JSqlParser,connection+properties+filter,自定义DIV,循环引用,null值?,使用sql关联加载
public class Query
{
    static private final Logger logger = Logger.getLogger("cozy");

    private Connection connection;
    private PreparedStatement statement;
    private boolean isclose = true;
    private int timeout = 0;
    private String sql = null;
    private Map<String,Object> varMap = new HashMap<>();
    private Map<String,Integer> indexMap = new HashMap<>();

    Query(Connection connection){
        this.connection = connection;
    }


    synchronized public void cancel(){
        if(!isclose && statement != null){
            try {
                statement.cancel();
                System.out.println("state cancel");
            } catch (SQLException e) {
                logger.error("query cancel error:"+e);
            }
        }
    }

    public Query timeout(int timeout){
        this.timeout = timeout;
        return this;
    }

    public Query sql(String sql){
        this.sql = sql;
        return this;
    }

    public Query var(String key,Object value){
        if(sql == null){
            throw new CozyException("sql is empty");
        }
        key = wrapeKey(key);
        int index = sql.indexOf(key);
        if(index<0){
            throw new CozyException("parameter not found:"+key);
        }
        int indexCount = 1;
        for(int i = 0;i<index;i++){
            if(sql.charAt(i) == '{'){
                indexCount++;
            }
        }
        indexMap.put(key,indexCount);
        varMap.put(key,value);
        return this;
    }

    public Query var(Map<String,?> param){
        if(sql == null){
            throw new CozyException("sql is empty");
        }

        for(Map.Entry<String,?> en : param.entrySet()){
            this.var(en.getKey(),en.getValue());
        }
        return this;
    }

    public <T> List<T> select(Class<T> clazz){
        if(sql == null){
            throw new CozyException("sql is empty");
        }
        try {
            if(connection.isClosed()){
                throw new CozyException("connection is closed");
            }

        } catch (SQLException e) {
            throw new CozyException("connection is closed:"+e.toString());
        }

        createStatement();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            List<T> res = new LinkedList<>();

            ObjectMapHandler<T> handler = new ObjectMapHandler<>(clazz);
            while (resultSet.next()){
                T row = handler.handle(resultSet);
                if(row != null){
                    res.add(row);
                }

            }
            return res;
        } catch (Exception e) {
            throw new CozyException(e);
        }finally {
            queryDone(resultSet);
        }
    }

     public List<Map<String,Object>> select(){
        if(sql == null){
            throw new CozyException("sql is empty");
        }
         try {
             if(connection.isClosed()){
                 throw new CozyException("connection is closed");
             }

         } catch (SQLException e) {
             throw new CozyException("connection is closed:"+e.toString());
         }

        createStatement();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery();
            MapResultHandler handler = new MapResultHandler();
            List<Map<String,Object>> res = new LinkedList<>();
            while (resultSet.next()){
                res.add(handler.handle(resultSet));
            }
            return res;
        } catch (SQLException e) {
            throw new CozyException(e);
        }finally {
            queryDone(resultSet);
        }
    }



    public int update(){
        if(sql == null){
            throw new CozyException("sql is empty");
        }
        try {
            if(connection.isClosed()){
                throw new CozyException("connection is closed");
            }

        } catch (SQLException e) {
            throw new CozyException("connection is closed:"+e.toString());
        }
        createStatement();
        int res = 0;


        try {
            res = statement.executeUpdate();
        } catch (SQLException e) {
            throw new CozyException(e);
        }finally {
            queryDone(null);
        }

        return res;
    }


    synchronized private void createStatement(){
        if(statement!=null){
            throw new CozyException("another query is executing");
        }
        try {
            if(varMap.size()>0){
                for(Map.Entry<String,Object> en : varMap.entrySet()){
                    sql = sql.replace(en.getKey(),"?");
                }

            }
            statement = connection.prepareStatement(sql);
            if(timeout>0){
                statement.setQueryTimeout(timeout);
            }
            if(varMap.size()>0){
                for(Map.Entry<String,Integer>  en : indexMap.entrySet()){
                    statement.setObject(en.getValue(),varMap.get(en.getKey()));
                }
            }

        } catch (SQLException e) {
            throw new CozyException(e);
        }
        isclose = false;
    }

    synchronized private void queryDone(ResultSet resultSet){
        if(resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error("result set close error:"+e);
            }
        }
        try {
            statement.close();
        } catch (SQLException e) {
            logger.error("statement close error:"+e);
        }
        isclose = true;
        statement = null;
    }


    private String wrapeKey(String key){
        return "{"+key+"}";
    }


}








