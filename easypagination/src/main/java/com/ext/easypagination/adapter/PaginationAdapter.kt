package com.ext.easypagination.adapter

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView

class PaginationAdapter(
    mainAdapter: RecyclerView.Adapter<*>,
    private val footerAdapter: LoadStateAdapter
) {

    val adapter: ConcatAdapter =
        ConcatAdapter(mainAdapter, footerAdapter)

    fun showFooterLoading() {
        footerAdapter.setState(LoadStateAdapter.LoadState.Loading)
    }

    fun showFooterError(message: String) {
        footerAdapter.setState(LoadStateAdapter.LoadState.Error(message))
    }

    fun hideFooter() {
        footerAdapter.setState(LoadStateAdapter.LoadState.Hidden)
    }
}
