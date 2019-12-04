package com.jext.util

class StrUtil {
    public static boolean isEmpty(String str) {
        return !str || str.isEmpty() || str.trim().isEmpty();
    }

    public static String[] split(String str, String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return null;
        }
        return str.split(separator);
    }

    public static String joinObj(Object[] objArr, String separator) {
        return EmptyUtil.isEmpty(objArr) ? null : joinObj(Arrays.asList(objArr), separator);
    }

    public static String joinObj(Collection<Object> objList, String separator) {
        if (ListUtil.isEmpty(objList)) {
            return null;
        }

        List<String> strList = new ArrayList<String>();
        for (Object obj : objList) {
            strList.add(obj == null ? "" : obj.toString());
        }
        return join(strList, separator);
    }

    public static String join(String[] strArr, String separator) {
        return EmptyUtil.isEmpty(strArr) ? null : join(Arrays.asList(strArr), separator);
    }

    public static String join(Collection<String> strList, String separator) {
        if (ListUtil.isEmpty(strList) || separator == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (String str : strList) {
            sb.append(separator);
            sb.append(str);
        }
        return sb.substring(separator.length());
    }
}
