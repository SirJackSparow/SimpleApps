package com.startup.simpleapps.view.transfer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.startup.simpleapps.R
import com.startup.simpleapps.data.model.Data

class SpinnerAdapterPayees(context: Context, val dataPayees: ArrayList<Data>) :
    ArrayAdapter<Data>(context, android.R.layout.simple_list_item_1, dataPayees) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, confertView: View?, parent: ViewGroup): View {
        var confertView = confertView
        if (confertView == null) {

            confertView = LayoutInflater.from(context).inflate(
                R.layout.item_payees, parent, false
            )
        }
        val textView = confertView?.findViewById<TextView>(R.id.nameOutlet)
        val models = getItem(position)
        if (models != null) {
            textView?.text = models.name
        }
        return confertView!!
    }
}
