package com.demoapp.adapterutils

object CollectionsUtils {

    fun isNullOrEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    fun equals(list1: List<*>?, list2: List<*>): Boolean {
        return if (isNullOrEmpty(list1 as Collection<*>?) && isNullOrEmpty(list2 as Collection<*>)) {
            true
        } else {
            if (list1 != null) list1 == list2 else false
        }
    }

}
