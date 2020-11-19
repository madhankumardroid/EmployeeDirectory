package com.whiterabbit.employeedirectory.widgets

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.whiterabbit.employeedirectory.R

class DoubleTextView : ConstraintLayout {
    private var tvTitle: TextView? = null
    private var tvValue: TextView? = null

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
            context!!, attrs
    ) {
        setAttributes(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context!!, attrs, defStyleAttr
    ) {
        setAttributes(context, attrs)
    }

    private fun setAttributes(context: Context?, attributeSet: AttributeSet?) {
        inflate(context, R.layout.layout_double_text, this)
        tvTitle = findViewById(R.id.tv_double_text_title)
        tvValue = findViewById(R.id.tv_double_text_value)
        if (context != null) {
            val typedArray = context.theme.obtainStyledAttributes(
                    attributeSet,
                    R.styleable.DoubleTextView,
                    0,
                    0
            )
            val titleText = typedArray.getString(R.styleable.DoubleTextView_titleText)
            val valueText = typedArray.getString(R.styleable.DoubleTextView_valueText)
            val titleColor = typedArray.getColor(R.styleable.DoubleTextView_titleColor, 0)
            val valueColor = typedArray.getColor(R.styleable.DoubleTextView_valueColor, 0)
            val titleSize = typedArray.getDimension(R.styleable.DoubleTextView_titleTextSize, 0f)
            val valueSize = typedArray.getDimension(R.styleable.DoubleTextView_valueTextSize, 0f)
            val padding = typedArray.getDimensionPixelSize(R.styleable.DoubleTextView_padding, 0)

            //set values
            setTitle(titleText)
            setValue(valueText)
            if (titleColor > 0) {
                tvTitle?.setTextColor(titleColor)
            }
            if (valueColor > 0) {
                tvValue?.setTextColor(valueColor)
            }
            if (titleSize > 0) {
                tvTitle?.textSize = titleSize
            }
            if (valueSize > 0) {
                tvValue?.textSize = valueSize
            }
            if (padding > 0) {
                tvTitle?.setPadding(padding, padding, padding, padding)
                tvValue?.setPadding(padding, padding, padding, padding)
            }
        }
    }

    private fun setTitle(title: String?) {
        if (!TextUtils.isEmpty(title)) {
            tvTitle!!.text = title
        }
    }

    fun setValue(value: String?) {
        if (!TextUtils.isEmpty(value)) {
            tvValue!!.text = value
        } else {
            tvValue!!.text = "-"
        }
    }
}