package com.xhystc.cozy.converter;

/**
 * @author xiehongyang
 * @date 2018/12/16 3:11 PM
 */
public interface Converter
{
    Object convert(Class targetClass,String fieldname,String value);
}
