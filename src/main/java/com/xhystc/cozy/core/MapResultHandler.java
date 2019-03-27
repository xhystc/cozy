package com.xhystc.cozy.core;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xiehongyang
 *
 */
public class MapResultHandler implements ResultHandler<Map<String,Object>>
{
    @Override
    public Map<String,Object> handle(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Map<String,Object> ret = new HashMap<>();
        for(int i=1;i<=metaData.getColumnCount();i++){
            if(metaData.getColumnTypeName(i).toLowerCase().contains("double") || metaData.getColumnTypeName(i).toLowerCase().contains("float")){
                ret.put(metaData.getColumnName(i),resultSet.getDouble(i));

            }else if(metaData.getColumnTypeName(i).toLowerCase().contains("bool")){
                ret.put(metaData.getColumnName(i),resultSet.getBoolean(i));

            }else if(metaData.getColumnTypeName(i).toLowerCase().contains("int")){
                ret.put(metaData.getColumnName(i),resultSet.getLong(i));

            }else {
                ret.put(metaData.getColumnName(i),resultSet.getString(i));
            }

        }
        return ret;
    }
}
