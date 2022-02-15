package com.startup.simpleapps.repository

import com.startup.simpleapps.data.requestbody.AuthRequest
import com.startup.simpleapps.data.model.AuthModel
import com.startup.simpleapps.data.service.Services
import com.startup.simpleapps.utils.Resource
import java.lang.Exception

class AuthRepoImpl(private val services: Services) : AuthRepo {
    override suspend fun login(requestBody: AuthRequest): Resource<AuthModel> {
        return try {
            val response = services.login(requestBody)
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

    override suspend fun register(requestBody: AuthRequest): Resource<AuthModel> {
        return try {
            val response = services.register(requestBody)
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