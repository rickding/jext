package com.jext.util

class EmptyUtil {
    public static <T> boolean isEmpty(T[] arr) {
        return arr == null || arr.length <= 0;
    }
}
