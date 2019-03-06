package com.xhystc.cozy.converter;

import org.apache.commons.lang3.StringUtils;

/**
 * @author xiehongyang
 * @date 2018/12/16 3:13 PM
 */
public class BasicTypeConverter implements Converter
{
    @Override
    public Object convert(Class targetClass, String fieldname, String value) {
        if(targetClass.equals(String.class)){
            return value;
        } else if(targetClass.equals(int.class) || targetClass.equals(Integer.class)){
            if(StringUtils.isEmpty(value)){
                if(targetClass.equals(int.class)){
                    return 0;
                }
                return null;
            }
            return Integer.parseInt(value);
        }else if(targetClass.equals(double.class) || targetClass.equals(Double.class)){
            if(StringUtils.isEmpty(value)){
                if(targetClass.equals(double.class)){
                    return 0;
                }
                return null;
            }
            return Double.parseDouble(value);
        }else if(targetClass.equals(float.class) || targetClass.equals(Float.class)){
            if(StringUtils.isEmpty(value)){
                if(targetClass.equals(float.class)){
                    return 0;
                }
                return null;
            }
            return Float.parseFloat(value);
        }else if(targetClass.equals(boolean.class) || targetClass.equals(Boolean.class)){
            if(StringUtils.isEmpty(value)){
                if(targetClass.equals(boolean.class)){
                    return false;
                }
                return null;
            }
            return value.contains("t") || value.equals("1");
        }else if(targetClass.equals(long.class) || targetClass.equals(Long.class)){
            if(StringUtils.isEmpty(value)){
                if(targetClass.equals(long.class)){
                    return 0;
                }
                return null;
            }
            return Long.parseLong(value);
        }
        return null;
    }
}
