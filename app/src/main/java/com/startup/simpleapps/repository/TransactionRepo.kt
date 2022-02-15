package com.startup.simpleapps.repository

import com.startup.simpleapps.data.model.TransactionListModel
import com.startup.simpleapps.utils.Resource

interface TransactionRepo {
    suspend fun getTransactionData(auth: String) :  Resource<TransactionListModel>
}