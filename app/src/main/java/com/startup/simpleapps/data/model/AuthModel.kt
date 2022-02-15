package com.startup.simpleapps.data.model

import com.startup.simpleapps.utils.Constants.EMPTY_STRING

data class AuthModel(
    val accountNo: String? = EMPTY_STRING,
    val status: String? = EMPTY_STRING,
    val token: String? = EMPTY_STRING,
    val username: String? = EMPTY_STRING
)