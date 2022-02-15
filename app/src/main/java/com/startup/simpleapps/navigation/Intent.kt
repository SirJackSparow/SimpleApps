package com.startup.simpleapps.navigation

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle

inline fun <reified  T : Activity> Activity.start(enterAnim: Int, exitAnim: Int) {
    startActivity(intent<T>())
    overridePendingTransition(enterAnim, exitAnim)
}

inline fun <reified  T : Activity> Activity.startFinish(enterAnim: Int, exitAnim: Int) {
    startActivity(intent<T>())
    overridePendingTransition(enterAnim, exitAnim)
    this.finish()
}

inline fun <reified T : Context> Context.intent(): Intent {
    return Intent(this, T::class.java)
}
