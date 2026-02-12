package com.ext.easypagination.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import com.ext.easypagination.R

class DefaultErrorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val errorText: TextView
    private val retryButton: Button

    init {
        inflate(context, R.layout.default_error_view, this)

        errorText = findViewById(R.id.txtErrorMessage)
        retryButton = findViewById(R.id.btnRetryMain)
    }

    fun setError(message: String) {
        errorText.text = message
    }

    fun setRetryAction(action: () -> Unit) {
        retryButton.setOnClickListener {
            action()
        }
    }
}
