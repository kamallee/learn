package com.crazyfish.kamal.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-07-06 下午5:24
 */
public class CustomDateConverter implements Converter<String,Date>{

    @Override
    public Date convert(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            return sdf.parse(s);
        }catch (Exception e){}
        return null;
    }
}
