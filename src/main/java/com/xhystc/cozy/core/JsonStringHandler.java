package com.xhystc.cozy.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/**
 * @author xiehongyang
 * @date 2018/12/16 3:43 PM
 */
public class JsonStringHandler implements ResultHandler<String>
{
    @Override
    public String handle(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        JSONObject json = new JSONObject();
        for(int i=1;i<=metaData.getColumnCount();i++){
            if(metaData.getColumnTypeName(i).toLowerCase().contains("json")){
                json.put(metaData.getColumnName(i), JSON.parse(resultSet.getString(i)));
            }else if(metaData.getColumnTypeName(i).toLowerCase().contains("double") || metaData.getColumnTypeName(i).toLowerCase().contains("float")){
                json.put(metaData.getColumnName(i),resultSet.getDouble(i));

            }else if(metaData.getColumnTypeName(i).toLowerCase().contains("bool")){
                json.put(metaData.getColumnName(i),resultSet.getBoolean(i));

            }else if(metaData.getColumnTypeName(i).toLowerCase().contains("int")){
                json.put(metaData.getColumnName(i),resultSet.getLong(i));

            }else {
                json.put(metaData.getColumnName(i),resultSet.getString(i));
            }

        }
        return json.toJSONString();
    }
}
