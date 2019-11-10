package com.jext.util

class ListUtil {
    public static boolean isEmpty(Collection list) {
        return !list || list.isEmpty();
    }

    public static <T> T[] toArray(Collection<T> list) {
        if (!list || list.isEmpty()) {
            return null;
        }

        T[] arr = new T[list.size()];
        list.toArray(arr);
        return arr;
    }
}
