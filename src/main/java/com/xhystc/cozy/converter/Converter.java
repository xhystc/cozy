package com.xhystc.cozy.converter;

/**
 * @author xiehongyang
 *
 */
public interface Converter
{
    Object convert(Class targetClass,String fieldname,String value);
}
