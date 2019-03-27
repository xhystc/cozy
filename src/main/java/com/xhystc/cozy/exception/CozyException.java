package com.xhystc.cozy.exception;

/**
 * @author xiehongyang
 *
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
