package com.ext.easypagination.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ext.easypagination.R

class LoadStateAdapter(
    private val retry: () -> Unit
) : RecyclerView.Adapter<LoadStateAdapter.StateViewHolder>() {

    sealed class LoadState {
        object Loading : LoadState()
        data class Error(val message: String) : LoadState()
        object Hidden : LoadState()
    }

    private var state: LoadState = LoadState.Hidden

    fun setState(newState: LoadState) {
        state = newState
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (state == LoadState.Hidden) 0 else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_load_state, parent, false)
        return StateViewHolder(view)
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        holder.bind(state)
    }

    inner class StateViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val progress: ProgressBar = view.findViewById(R.id.progressBar)
        private val errorText: TextView = view.findViewById(R.id.txtError)
        private val retryBtn: Button = view.findViewById(R.id.btnRetry)

        fun bind(state: LoadState) {

            when (state) {

                LoadState.Loading -> {
                    progress.visibility = View.VISIBLE
                    errorText.visibility = View.GONE
                    retryBtn.visibility = View.GONE
                }

                is LoadState.Error -> {
                    progress.visibility = View.GONE
                    errorText.visibility = View.VISIBLE
                    retryBtn.visibility = View.VISIBLE

                    errorText.text = state.message

                    retryBtn.setOnClickListener { retry() }
                }

                LoadState.Hidden -> {
                    progress.visibility = View.GONE
                    errorText.visibility = View.GONE
                    retryBtn.visibility = View.GONE
                }
            }
        }
    }
}
