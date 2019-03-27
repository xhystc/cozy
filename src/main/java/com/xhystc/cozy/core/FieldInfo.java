package com.xhystc.cozy.core;

import com.xhystc.cozy.converter.Converter;

/**
 * @author xiehongyang
 *
 */
public class FieldInfo
{
    private boolean isEntity;
    private boolean isCollection;
    private String fieldName;
    private Class clazz;
    private String columName;
    private Class ofType;
    private Converter converter;

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public boolean isEntity() {
        return isEntity;
    }

    public void setEntity(boolean entity) {
        isEntity = entity;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getColumName() {
        return columName;
    }

    public void setColumName(String columName) {
        this.columName = columName;
    }

    public Class getOfType() {
        return ofType;
    }

    public void setOfType(Class ofType) {
        this.ofType = ofType;
    }
}
