
package com.offline.utils.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Converts JSON String to POJO.
 */
inline fun <reified T> String.toPojo(): T {
    return Gson().fromJson(this, T::class.java)
}

/**
 * Converts any object to JSON string.
 */
inline fun <reified T> T.toJson(): String {
    return Gson().toJson(this)
}

/**
 * Converts JSONArray String to List<T>.
 */
inline fun <reified T> fromJson(json: String): T = Gson().fromJson(json, object: TypeToken<T>() {}.type)

/**
 * Converts List<T> to String.
 */
inline fun <reified T> toList(json: T): String = Gson().toJson(json, object : TypeToken<T>() {}.type)
