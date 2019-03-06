package com.xhystc.cozy.core;

import com.xhystc.cozy.exception.CozyException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xiehongyang
 * @date 2018/12/21 11:21 AM
 */
public class EntityInfo
{
    static private Map<Class,EntityInfo> entityInfoMap = new HashMap<>();

    private List<FieldInfo> entityInfoList = new LinkedList<>();
    private List<FieldInfo> basicInfoList = new LinkedList<>();
    private List<FieldInfo> colectionInfoList = new LinkedList<>();
    private Class entityClazz;

    public List<FieldInfo> getEntityInfoList() {
        return entityInfoList;
    }

    public void setEntityInfoList(List<FieldInfo> entityInfoList) {
        this.entityInfoList = entityInfoList;
    }

    public Class getEntityClazz() {
        return entityClazz;
    }

    public void setEntityClazz(Class entityClazz) {
        this.entityClazz = entityClazz;
    }

    public List<FieldInfo> getBasicInfoList() {
        return basicInfoList;
    }

    public void setBasicInfoList(List<FieldInfo> basicInfoList) {
        this.basicInfoList = basicInfoList;
    }

    public List<FieldInfo> getColectionInfoList() {
        return colectionInfoList;
    }

    public void setColectionInfoList(List<FieldInfo> colectionInfoList) {
        this.colectionInfoList = colectionInfoList;
    }

    synchronized static public EntityInfo getEntityInfo(Class clazz) throws IllegalAccessException, InstantiationException {
        EntityInfo ret = entityInfoMap.get(clazz);
        if(ret==null){
            if(clazz.getAnnotation(QueryResult.class)==null){
                throw new CozyException("class:"+clazz.getName()+" not entity!!!");
            }
            EntityInfo entityInfo = new EntityInfo();
            entityInfo.setEntityClazz(clazz);
            Field[] fields = clazz.getDeclaredFields();
            for(Field field : fields){
                com.xhystc.cozy.core.Field cozyField;
                if((cozyField=field.getAnnotation(com.xhystc.cozy.core.Field.class)) != null){
                    FieldInfo fieldInfo = new FieldInfo();
                    fieldInfo.setEntity(cozyField.entity());
                    fieldInfo.setColumName(cozyField.columnName());
                    fieldInfo.setConverter(cozyField.converter().newInstance());
                    fieldInfo.setClazz(field.getType());
                    fieldInfo.setFieldName(field.getName());
                    if(!cozyField.ofType().equals(NoneType.class)){
                        fieldInfo.setCollection(true);
                        fieldInfo.setOfType(cozyField.ofType());
                    }
                    if(fieldInfo.isCollection()){
                        entityInfo.getColectionInfoList().add(fieldInfo);
                    }else if(fieldInfo.isEntity()){
                        entityInfo.getEntityInfoList().add(fieldInfo);
                    }else {
                        entityInfo.getBasicInfoList().add(fieldInfo);
                    }
                }

            }
            ret = entityInfo;
            entityInfoMap.put(clazz,ret);
        }
        return ret;
    }
}
