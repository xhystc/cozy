package com.xhystc.cozy.core;

import com.xhystc.cozy.converter.Converter;
import com.xhystc.cozy.exception.CozyException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xiehongyang
 * @date 2018/12/16 3:50 PM
 */
public class ObjectConvertHandler<T> implements ResultHandler<Object>
{
    private Class<T> clazz;

    public ObjectConvertHandler(Class<T> clazz){
        this.clazz = clazz;
    }
    @Override
    public T handle(ResultSet resultSet) throws SQLException {
        Entity entity = clazz.getAnnotation(Entity.class);
        String alias = entity.prefix();
        try {
            T res = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for(Field field : fields){
                com.xhystc.cozy.core.Field cozyField = field.getAnnotation(com.xhystc.cozy.core.Field.class);
                String fieldname = field.getName();
                if(cozyField != null) {
                    if(cozyField.entity()){
                        ObjectConvertHandler fieldHandler = new ObjectConvertHandler(field.getType());
                        Object o = fieldHandler.handle(resultSet);
                        field.setAccessible(true);
                        field.set(res,o);
                    }else {
                        String colname = cozyField.name();
                        Converter converter = cozyField.converter().newInstance();
                        field.setAccessible(true);
                        field.set(res,converter.convert(field.getType(),fieldname,resultSet.getString(getFieldString(alias,colname))));
                    }
                }

            }
            return res;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CozyException(e);
        }


    }

    private String getFieldString(String alias,String filedname){
        if(!StringUtils.isEmpty(alias)){
            return alias+"_"+filedname;
        }
        return filedname;
    }
}
