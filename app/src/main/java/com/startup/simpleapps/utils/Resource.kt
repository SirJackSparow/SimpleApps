package com.startup.simpleapps.utils

sealed class Resource<D>(val data: D?, val message: String?) {
    class Success<D>(data: D?) : Resource<D>(data, null)
    class Failed<D>(message: String?) : Resource<D>(null, message)
}
