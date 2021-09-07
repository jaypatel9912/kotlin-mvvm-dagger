package com.demoapp.adapterutils

import com.google.common.base.Preconditions
import com.google.common.collect.ImmutableMap.Builder
import java.lang.IllegalArgumentException

object CollectionsUtils {
    fun <K, V> addToMapBuilderIfNotNull(mapBuilder: Builder<K, V>, key: K?, value: V?) {
        Preconditions.checkNotNull(mapBuilder, "map builder == null")
        if (key != null && value != null) {
            mapBuilder.put(key, value)
        }
    }

    fun <E> addToSetBuilderIfNotNull(
        setBuilder: com.google.common.collect.ImmutableSet.Builder<E>,
        element: E?
    ) {
        Preconditions.checkNotNull(setBuilder, "set builder == null")
        if (element != null) {
            setBuilder.add(element)
        }
    }

    fun isNullOrEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    fun isNullOrEmpty(map: Map<*, *>?): Boolean {
        return map == null || map.isEmpty()
    }

    fun <T> checkNotNullOrEmpty(collection: Collection<T>?, errorMessage: String): Collection<T>? {
        return if (isNullOrEmpty(collection)) {
            throw IllegalArgumentException(errorMessage)
        } else {
            collection
        }
    }

    fun equals(list1: List<*>?, list2: List<*>): Boolean {
        return if (isNullOrEmpty(list1 as Collection<*>?) && isNullOrEmpty(list2 as Collection<*>)) {
            true
        } else {
            if (list1 != null) list1 == list2 else false
        }
    }

}
