package com.startup.simpleapps.repository

import android.util.Log
import com.startup.simpleapps.data.model.TransferModel
import com.startup.simpleapps.data.requestbody.TransferRequest
import com.startup.simpleapps.data.service.Services
import com.startup.simpleapps.utils.Resource
import java.lang.Exception

class TransferRepoImpl(private val services: Services) : TransferRepo {
    override suspend fun transfer(auth:String,requestTransfer: TransferRequest): Resource<TransferModel> {
        return try {
            val response = services.transfer(auth,requestTransfer)
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