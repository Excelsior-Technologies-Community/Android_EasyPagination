package com.ext.easypagination.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.ext.easypagination.R

class DefaultEmptyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    init {
        inflate(context, R.layout.default_empty_view, this)
    }
}
