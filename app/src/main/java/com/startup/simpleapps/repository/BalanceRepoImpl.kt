package com.startup.simpleapps.repository

import android.util.Log
import com.startup.simpleapps.data.model.BalanceModel
import com.startup.simpleapps.data.service.Services
import com.startup.simpleapps.utils.Resource
import java.lang.Exception

class BalanceRepoImpl(private val services: Services) : BalanceRepo {
    override suspend fun getBalanceData(auth: String): Resource<BalanceModel> {
        return try {
            val response = services.balance(auth)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Failed(message = response.message())
            }
        } catch (e: Exception) {
            Resource.Failed(e.message ?: "An Occured Error")
        }
    }
}