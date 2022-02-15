package com.startup.simpleapps.utils

import android.content.Context
import android.opengl.Visibility
import android.view.View
import com.startup.simpleapps.utils.Constants.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val EMPTY_STRING = ""

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    fun Double.convert(): String {
        val format = DecimalFormat("#,###.00")
        format.isDecimalSeparatorAlwaysShown = false
        return "SGD ${format.format(this)}"
    }

    fun String.datFormat() : String{
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val output = SimpleDateFormat("dd MMM yyyy")

        var d: Date? = null
        try {
            d = input.parse(this)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val formatted: String = output.format(d)
        return formatted
    }
}
