package com.ext.easypagination.pagination

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ext.easypagination.adapter.PaginationAdapter

class EasyPagingController<T>(
    private val recyclerView: RecyclerView,
    private val loadingView: View,
    private val emptyView: View,
    private val errorView: View,
    private val paginationAdapter: PaginationAdapter,
    private val adapter: PagingDataAdapter<T>,
    private val retryRequest: (page: Int) -> Unit
) {

    private var currentPage = 1
    private var isLoading = false
    private var isLastPage = false

    // ----------------------------
    // PUBLIC API
    // ----------------------------

    fun submitPage(
        page: Int,
        items: List<T>,
        isLastPage: Boolean
    ) {

        currentPage = page
        this.isLastPage = isLastPage
        this.isLoading = false

        paginationAdapter.hideFooter()

        // Empty first page
        if (page == 1 && items.isEmpty()) {
            showEmpty()
            return
        }

        // Replace vs Append
        if (page == 1) adapter.setItems(items)
        else adapter.addItems(items)

        showContent()
    }

    fun submitError(message: String) {

        isLoading = false

        if (currentPage == 1) {
            showError()
        } else {
            paginationAdapter.showFooterError(message)
        }
    }

    fun loadNextPage() {
        if (isLoading || isLastPage) return

        isLoading = true
        paginationAdapter.showFooterLoading()

        retryRequest(currentPage + 1)
    }

    fun refresh() {
        currentPage = 1
        isLastPage = false
        isLoading = true

        adapter.setItems(emptyList())

        showLoading()
        retryRequest(1)
    }

    fun retry() {
        isLoading = true
        paginationAdapter.showFooterLoading()
        retryRequest(currentPage)
    }

    fun canPaginate(): Boolean = !isLoading && !isLastPage

    fun isLoading(): Boolean = isLoading

    fun isLastPage(): Boolean = isLastPage

    // ----------------------------
    // UI STATES
    // ----------------------------

    private fun showLoading() {
        loadingView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
    }

    private fun showContent() {
        recyclerView.visibility = View.VISIBLE
        loadingView.visibility = View.GONE
        emptyView.visibility = View.GONE
        errorView.visibility = View.GONE
    }

    private fun showEmpty() {
        emptyView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE
    }

    private fun showError() {
        errorView.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        loadingView.visibility = View.GONE
        emptyView.visibility = View.GONE
    }
}
