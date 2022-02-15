package com.startup.simpleapps.data.requestbody

data class TransferRequest(
    val amount: Int?,
    val description: String?,
    val receipientAccountNo: String?
)