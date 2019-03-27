package com.xhystc.cozy.core;

import com.xhystc.cozy.converter.BasicTypeConverter;
import com.xhystc.cozy.converter.Converter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xiehongyang
 *
 */

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field
{
    String columnName() default "";
    Class<? extends Converter> converter() default BasicTypeConverter.class;
    boolean entity() default false;
    Class<? extends Object> ofType() default NoneType.class;
}
