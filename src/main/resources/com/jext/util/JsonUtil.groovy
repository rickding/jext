package com.jext.util

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class JsonUtil {
    static String toJson(Object obj) {
        if (!obj) { return null }

        JsonOutput jsonOutput = new JsonOutput()
        return jsonOutput.toJson(obj)
    }

    static Object parseJson(String jsonStr) {
        if (!jsonStr) { return null }

        JsonSlurper jsonSlurper = new JsonSlurper()
        try {
            // Map or List
            return jsonSlurper.parseText(jsonStr)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return null
    }
}
