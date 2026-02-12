package com.ext.easypagination.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.ext.easypagination.R

class DefaultLoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        inflate(context, R.layout.default_loading_view, this)
    }
}
