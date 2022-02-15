package com.startup.simpleapps.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.SystemClock
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.startup.simpleapps.R
import com.startup.simpleapps.utils.Constants.EMPTY_STRING
import com.startup.simpleapps.utils.Constants.invisible
import com.startup.simpleapps.utils.Constants.visible
import kotlinx.android.synthetic.main.loading_button.view.*


class LoadingButton : ConstraintLayout {
    private var _isLoading = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        if (!_isLoading) {
            if (SystemClock.elapsedRealtime() - lastTimeClicked < 1000) {
                return
            }
            lastTimeClicked = SystemClock.elapsedRealtime()
            btnV.setOnClickListener(l)
        }
    }

    private var lastTimeClicked: Long = 0L

    @SuppressLint("CustomViewStyleable")
    private fun init(attrs: AttributeSet? = null) {
        inflate(context, R.layout.loading_button, this)
        attrs.let {
            context.obtainStyledAttributes(it, R.styleable.LoadingButton).apply {
                try {
                    btnTxt.text = getString(R.styleable.LoadingButton_android_text)
                    btnTxt.isAllCaps = getBoolean(
                        R.styleable.LoadingButton_android_textAllCaps,
                        false
                    )
                    btnV.isEnabled = getBoolean(R.styleable.LoadingButton_android_enabled, true)

                    val buttonType = getInt(R.styleable.LoadingButton_button_type, 0)
                    setButtonType(buttonType)
                } finally {
                    recycle()
                }
            }
        }
    }

    var text: String = EMPTY_STRING
        set(value) {
            btnTxt.text = value
            field = value
        }
        get() = btnTxt.text.toString()

    private fun setButtonType(buttonType: Int) {
        if (buttonType == SECONDARY_BUTTON_TYPE) {
            btnV.background = ContextCompat.getDrawable(context, R.drawable.bg_oval)
            btnTxt.setTextColor(ContextCompat.getColor(context, R.color.grey))
            btn_progress.indeterminateTintList = ColorStateList.valueOf(
                ContextCompat.getColor(context, R.color.grey)
            )
        }
    }

    fun setState(state: ButtonState) {
        when (state) {
            ButtonState.DISABLED -> {
                btnTxt.visible()
                btn_progress.invisible()
                btnV.isEnabled = false
                btnV.isClickable = false
            }
            ButtonState.LOADING -> {
                btnTxt.invisible()
                btn_progress.visible()
                btnV.isEnabled = true
                btnV.isClickable = false
            }
            ButtonState.ENABLED -> {
                btnTxt.visible()
                btn_progress.invisible()
                btnV.isEnabled = true
                btnV.isClickable = true
            }
        }
    }

    fun showButtonLoading(isShow: Boolean) {
        _isLoading = isShow
        if (isShow) {
            setState(ButtonState.LOADING)
        } else {
            setState(ButtonState.ENABLED)
        }
    }

    companion object {
        private const val SECONDARY_BUTTON_TYPE = 1
    }

}


enum class ButtonState {
    DISABLED,
    LOADING,
    ENABLED
}