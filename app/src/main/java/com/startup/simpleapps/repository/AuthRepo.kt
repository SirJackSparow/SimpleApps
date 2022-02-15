package com.startup.simpleapps.repository

import com.startup.simpleapps.data.requestbody.AuthRequest
import com.startup.simpleapps.data.model.AuthModel
import com.startup.simpleapps.utils.Resource

interface AuthRepo {
    suspend fun login(requestBody: AuthRequest) : Resource<AuthModel>
    suspend fun register(requestBody: AuthRequest) : Resource<AuthModel>
}