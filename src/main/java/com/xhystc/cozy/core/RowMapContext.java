package com.xhystc.cozy.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author xiehongyang
 *
 */
public class RowMapContext
{
    private Map<Class, Map<String,String>> lastRowValues = new HashMap<>();
    private Map<Class,Object> lastMapObject = new HashMap<>();
    private Stack<Class> mapStack = new Stack<>();


    public Map<Class, Map<String, String>> getLastRowValues() {
        return lastRowValues;
    }

    public void setLastRowValues(Map<Class, Map<String, String>> lastRowValues) {
        this.lastRowValues = lastRowValues;
    }

    public Map<Class, Object> getLastMapObject() {
        return lastMapObject;
    }

    public void setLastMapObject(Map<Class, Object> lastMapObject) {
        this.lastMapObject = lastMapObject;
    }

    public Stack<Class> getMapStack() {
        return mapStack;
    }

    public void setMapStack(Stack<Class> mapStack) {
        this.mapStack = mapStack;
    }
}

