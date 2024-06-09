package ru.rsreu.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListUtils {

    @SafeVarargs
    public static <T> List<T> listOf(T... args) {
        return new ArrayList<>(Arrays.asList(args));
    }

}
