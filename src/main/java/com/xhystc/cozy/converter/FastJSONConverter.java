package com.xhystc.cozy.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author xiehongyang
 * @date 2018/12/16 3:21 PM
 */
public class FastJSONConverter implements Converter
{
    @Override
    public Object convert(Class targetClass, String fieldname, String value) {
        if(StringUtils.isEmpty(value)){
            return null;
        }
        if(targetClass.equals(JSONObject.class)){
            return JSON.parseObject(value);
        }else if(targetClass.equals(JSONArray.class)){
            return JSON.parseArray(value);
        }
        return null;
    }
}
