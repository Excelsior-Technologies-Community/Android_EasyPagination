package com.ext.easypagination.pagination

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ext.easypagination.adapter.LoadStateAdapter
import com.ext.easypagination.adapter.PaginationAdapter
import com.ext.easypagination.ui.DefaultEmptyView
import com.ext.easypagination.ui.DefaultErrorView
import com.ext.easypagination.ui.DefaultLoadingView

class EasyPagination {

    companion object {

        fun <T> attach(
            recyclerView: RecyclerView,
            swipeRefreshLayout: SwipeRefreshLayout? = null,
            adapter: PagingDataAdapter<T>,
            recyclerAdapter: RecyclerView.Adapter<*>,
            onLoadPage: (page: Int) -> Unit
        ): EasyPagingController<T> {

            val parent = recyclerView.parent as ViewGroup

            val loadingView = DefaultLoadingView(recyclerView.context)
            val emptyView = DefaultEmptyView(recyclerView.context)
            val errorView = DefaultErrorView(recyclerView.context)

            parent.addView(loadingView)
            parent.addView(emptyView)
            parent.addView(errorView)

            lateinit var controller: EasyPagingController<T>

            val footerAdapter = LoadStateAdapter {
                controller.retry()
            }

            val paginationAdapter = PaginationAdapter(recyclerAdapter, footerAdapter)
            recyclerView.adapter = paginationAdapter.adapter

            controller = EasyPagingController(
                recyclerView,
                loadingView,
                emptyView,
                errorView,
                paginationAdapter,
                adapter,
                retryRequest = { page ->
                    onLoadPage(page)
                }
            )

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            // First Load
            controller.refresh()

            // Scroll Pagination
            recyclerView.addOnScrollListener(object :
                PaginationScrollListener(layoutManager) {

                override fun loadMoreItems() {
                    controller.loadNextPage()
                }

                override fun isLastPage(): Boolean = controller.isLastPage()

                override fun isLoading(): Boolean = controller.isLoading()
            })

            // Pull Refresh
            swipeRefreshLayout?.setOnRefreshListener {
                controller.refresh()
                swipeRefreshLayout.isRefreshing = false
            }

            return controller
        }
    }
}
