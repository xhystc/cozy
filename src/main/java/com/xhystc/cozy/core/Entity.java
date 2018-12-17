package com.xhystc.cozy.core;

import java.lang.annotation.*;

/**
 * @author xiehongyang
 * @date 2018/12/16 3:04 PM
 */

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity
{
    String prefix() default "";

}
