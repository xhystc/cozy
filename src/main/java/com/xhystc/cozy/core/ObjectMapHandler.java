package com.xhystc.cozy.core;

import com.xhystc.cozy.converter.Converter;
import com.xhystc.cozy.exception.CozyException;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author xiehongyang
 *
 */
public class ObjectMapHandler<T> implements ResultHandler<T>
{
    private Class<T> clazz;
    private RowMapContext context;

    public ObjectMapHandler(Class<T> clazz){
        this.clazz = clazz;
        context = new RowMapContext();
    }

    private Object mapRow(ResultSet resultSet,RowMapContext context,Class clazz) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        EntityInfo entityInfo = EntityInfo.getEntityInfo(clazz);
        Object currObject = context.getLastMapObject().get(clazz);
        Map<String,String> currRowValue = context.getLastRowValues().get(clazz);
        Map<String,String> rowValue = new HashMap<>();
        boolean newObject = false;
        boolean notNullRow = false;
        context.getMapStack().push(clazz);

        for(FieldInfo fieldInfo : entityInfo.getBasicInfoList()){
            String col = fieldInfo.getColumName();
            String colValue = getColString(resultSet,col);

            if(colValue!=null){
                rowValue.put(col,colValue);
                notNullRow = true;
            }

        }
        if((currObject==null || !rowValue.equals(currRowValue)) && notNullRow){
            currObject = clazz.newInstance();
            currRowValue = rowValue;
            setObjectBasicProperties(currObject,entityInfo,currRowValue);
            newObject = true;
            context.getLastRowValues().put(clazz,currRowValue);
            context.getLastMapObject().put(clazz,currObject);
        }

        if(!notNullRow){
            return null;
        }

        for(FieldInfo fieldInfo : entityInfo.getEntityInfoList()){
            Object o = null;
            if(context.getMapStack().contains(fieldInfo.getClazz())){
                o = context.getLastMapObject().get(fieldInfo.getClazz());
            }else if(newObject){
                context.getLastRowValues().remove(fieldInfo.getClazz());
                o = mapRow(resultSet,context,fieldInfo.getClazz());
            }

            if(o!=null){
                setObjectEntityField(currObject,entityInfo,o,fieldInfo);
            }
        }

        for(FieldInfo fieldInfo : entityInfo.getColectionInfoList()){
            if(context.getMapStack().contains(fieldInfo.getOfType())){
                continue;
            }
            if(newObject){
                context.getLastRowValues().remove(fieldInfo.getOfType());
            }
            Object o = mapRow(resultSet,context,fieldInfo.getOfType());
            if(o != null){
                addObjectCollectionField(currObject,entityInfo,o,fieldInfo);
            }

        }
        context.getMapStack().pop();
        if(newObject){
            return currObject;
        }
        return null;
    }

    private void setObjectBasicProperties(Object o,EntityInfo entityInfo,Map<String,String> rowValue) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = entityInfo.getEntityClazz();
        for(FieldInfo fieldInfo : entityInfo.getBasicInfoList()){
            Field field =  clazz.getDeclaredField(fieldInfo.getFieldName());
            field.setAccessible(true);
            Converter converter = fieldInfo.getConverter();
            Object value = converter.convert(fieldInfo.getClazz(),fieldInfo.getFieldName(),rowValue.get(fieldInfo.getColumName()));
            field.set(o,value);
        }
    }

    private void setObjectEntityField(Object o,EntityInfo entityInfo,Object fieldValue,FieldInfo fieldInfo) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = entityInfo.getEntityClazz();
        if(!fieldInfo.getClazz().equals(fieldValue.getClass())){
            throw new CozyException("field type not match");
        }
        Field field = clazz.getDeclaredField(fieldInfo.getFieldName());
        field.setAccessible(true);
        field.set(o,fieldValue);
    }

    private void addObjectCollectionField(Object o,EntityInfo entityInfo,Object fieldValue,FieldInfo fieldInfo) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        Class clazz = entityInfo.getEntityClazz();

        Field field = clazz.getDeclaredField(fieldInfo.getFieldName());
        field.setAccessible(true);
        if(!Collection.class.isAssignableFrom(field.getType())){
            throw new CozyException("field:"+field+" not collection!!!");
        }

        Collection collection = (Collection) field.get(o);

        if(collection==null){
            Collection c = newCollection(fieldInfo.getClazz());
            field.set(o,c);
            collection = c;
        }
        collection.add(fieldValue);
    }


    private Collection newCollection(Class clazz) throws IllegalAccessException, InstantiationException {
        if(clazz.isInterface()){
            if(List.class.isAssignableFrom(clazz)){
                return new LinkedList();
            }else if(Set.class.isAssignableFrom(clazz)){
                return new HashSet();
            }else{
                throw new CozyException("unsupported collection:"+clazz.getName());
            }
        }else {
            return (Collection) clazz.newInstance();
        }
    }

    private String getColString(ResultSet resultSet,String name){
        try {
            return resultSet.getString(name);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public T handle(ResultSet resultSet) throws SQLException, IllegalAccessException, NoSuchFieldException, InstantiationException {

        return (T) mapRow(resultSet,context,clazz);
    }
}
