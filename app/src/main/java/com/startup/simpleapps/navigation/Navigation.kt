package com.startup.simpleapps.navigation

import android.app.Activity
import android.content.Context
import com.startup.simpleapps.view.register.RegisterActivity
import com.startup.simpleapps.R.anim.*
import com.startup.simpleapps.view.login.LoginActivity
import com.startup.simpleapps.view.transaction.TransactionListActivity
import com.startup.simpleapps.view.transfer.TransferActivity

fun navigateToRegistration(context: Context) {
    if (context != null && context is Activity) {
        val activity = context
        activity.start<RegisterActivity>(right_in, left_out)
    }
}

fun navigateToTransaction(context: Context) {
    if (context != null && context is Activity) {
        val activity = context
        activity.startFinish<TransactionListActivity>(right_in, left_out)
    }
}

fun navigateToLogin(context: Context) {
    if (context != null && context is Activity) {
        val activity = context
        activity.startFinish<LoginActivity>(left_in, right_out)
    }
}

fun navigateToTransfer(context: Context) {
    if (context != null && context is Activity) {
        val activity = context
        activity.start<TransferActivity>(right_in, left_out)
    }
}
