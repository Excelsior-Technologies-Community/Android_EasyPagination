package com.ext.android_easypagination

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ext.android_easypagination.databinding.ActivityMainBinding
import com.ext.easypagination.pagination.EasyPagination
import com.ext.easypagination.pagination.EasyPagingController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: UserAdapter

    // ✅ Paging Controller
    private lateinit var paging: EasyPagingController<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Setup RecyclerView
        adapter = UserAdapter()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // ✅ Attach EasyPagination Library
        paging = EasyPagination.attach(
            recyclerView = binding.recyclerView,
            swipeRefreshLayout = binding.swipeRefresh,

            // Adapter must implement PagingDataAdapter<T>
            adapter = adapter,

            // RecyclerView.Adapter for showing list
            recyclerAdapter = adapter

        ) { page ->

            // Library will request page here
            loadPageFromFakeApi(page)
        }
    }

    // -----------------------------------------
    // Fake API Simulation
    // -----------------------------------------

    private fun loadPageFromFakeApi(page: Int) {

        Handler(Looper.getMainLooper()).postDelayed({

            // ✅ Simulate API Response
            val items: List<String> =
                if (page <= 3) {
                    // Page has data
                    List(15) {
                        "Item ${(page - 1) * 15 + it + 1}"
                    }
                } else {
                    // No more pages after page 3
                    emptyList()
                }

            // ✅ Submit Result to Pagination Controller
            paging.submitPage(
                page = page,
                items = items,
                isLastPage = items.isEmpty()
            )

        }, 1500)
    }
}
