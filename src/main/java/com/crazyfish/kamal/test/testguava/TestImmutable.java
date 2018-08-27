package com.crazyfish.kamal.test.testguava;

import com.google.common.collect.ImmutableSet;
import org.springframework.beans.BeanUtils;

import java.util.Set;

/**
 * @author lipengpeng
 * @desc
 * @date 2016-07-08 下午3:10
 */
public class TestImmutable {
    private static final Set<String> IGNORE_FIELD_OF_UPDATE = ImmutableSet.of("id");


    public void copyFrom() {
        String[] x = (String[]) IGNORE_FIELD_OF_UPDATE.toArray(new String[IGNORE_FIELD_OF_UPDATE.size()]);
    }

    public static void main(String args[]){
        Set<String> immutableNamedColors = ImmutableSet.<String>builder()
                .add("red", "green","black","white","grey")
                .build();
        //immutableNamedColors.add("abc");
        for (String color : immutableNamedColors) {
            System.out.println(color);
        }
    }

    public static final ImmutableSet<String> COLOR_NAMES = ImmutableSet.of(
            "red",
            "orange",
            "yellow",
            "green",
            "blue",
            "purple");


}
