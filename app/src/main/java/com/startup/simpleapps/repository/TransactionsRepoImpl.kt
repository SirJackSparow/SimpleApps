package com.startup.simpleapps.repository

import android.util.Log
import com.startup.simpleapps.data.model.TransactionListModel
import com.startup.simpleapps.data.service.Services
import com.startup.simpleapps.utils.Resource
import java.lang.Exception

class TransactionsRepoImpl(private val services: Services) : TransactionRepo {
    override suspend fun getTransactionData(auth:String): Resource<TransactionListModel> {
        return try {
            val response = services.transactionsList(auth)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Log.e("success", result.toString() )
                Resource.Success(result)

            } else {
                Resource.Failed(message = response.message())
            }
        } catch (e: Exception) {
            Resource.Failed(e.message ?: "An Occured Error")
        }
    }
}