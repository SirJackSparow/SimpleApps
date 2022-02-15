package com.startup.simpleapps.repository

import android.util.Log
import com.startup.simpleapps.data.model.PayeesModel
import com.startup.simpleapps.data.service.Services
import com.startup.simpleapps.utils.Resource
import java.lang.Exception

class PayeesRepoImpl(private val services: Services) :  PayeesRepo {
    override suspend fun getPayeesData(auth:String): Resource<PayeesModel> {
        return try {
            val response = services.payees(auth)
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