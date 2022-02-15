package com.startup.simpleapps.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.startup.simpleapps.data.datastore.DataStorePreferences
import org.koin.android.ext.android.inject

abstract class BaseActivity<vb: ViewBinding?> : AppCompatActivity() {

    var bind : vb? = null
    protected val dataStore : DataStorePreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = getActivityBinding(layoutInflater)
        val view = bind?.root
        setContentView(view)
        onInit()
    }

    override fun onDestroy() {
        super.onDestroy()
        bind = null
    }

    abstract fun onInit()
    abstract fun getActivityBinding(layoutinflater: LayoutInflater ) : vb
}
