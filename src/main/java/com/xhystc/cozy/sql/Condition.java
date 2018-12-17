package com.xhystc.cozy.sql;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;

/**
 * @author xiehongyang
 * @date 2018/12/15 1:20 PM
 */
public class Condition
{
    static final private String AND = " AND ";
    static final private String OR = " OR ";

    private String value;


    public Condition(String s){
        if(StringUtils.isEmpty(s)){
            throw new IllegalArgumentException("parameter is empty!!!");
        }
        value = s;
    }
    public Condition and(String s){
        if(StringUtils.isEmpty(s)){
            throw new IllegalArgumentException("parameter is empty!!!");
        }
        return new Condition(value+AND+s);
    }

    public Condition and(Condition condition){
        if(condition==null || StringUtils.isEmpty(condition.value)){
            throw new IllegalArgumentException("parameter is empty!!!");
        }
        return new Condition(value+AND+condition.toString());
    }


    public Condition or(String s){
        if(StringUtils.isEmpty(s)){
            throw new IllegalArgumentException("parameter is empty!!!");
        }
        return new Condition(value+OR+s);
    }

    public Condition or(Condition condition){
        if(condition==null || StringUtils.isEmpty(condition.value)){
            throw new IllegalArgumentException("parameter is empty!!!");
        }

        return new Condition(value+OR+condition.toString());
    }


    @Override
    public String toString() {
        return "("+value+")";
    }
}
