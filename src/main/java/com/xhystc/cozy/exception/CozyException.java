package com.xhystc.cozy.exception;

/**
 * @author xiehongyang
 * @date 2018/12/15 2:15 PM
 */
public class CozyException extends RuntimeException
{
    public CozyException(String msg){
        super(msg);
    }

    public CozyException(Exception e){
        super(e);
    }

    public String toString(){
        return super.toString();
    }
}
