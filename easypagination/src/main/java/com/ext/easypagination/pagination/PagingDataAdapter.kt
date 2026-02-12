package com.ext.easypagination.pagination

interface PagingDataAdapter<T> {

    fun setItems(items: List<T>)

    fun addItems(items: List<T>)
}
