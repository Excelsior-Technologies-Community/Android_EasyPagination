package com.ext.easypagination.pagination

sealed class PaginationState {

    object Loading : PaginationState()

    object Success : PaginationState()

    object Empty : PaginationState()

    data class Error(val message: String) : PaginationState()
}
