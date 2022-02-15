package com.startup.simpleapps.fakerepository

import com.startup.simpleapps.data.model.AuthModel
import com.startup.simpleapps.data.requestbody.AuthRequest
import com.startup.simpleapps.repository.AuthRepo
import com.startup.simpleapps.utils.Resource

class FakeAuthRepository(val data: AuthModel) : AuthRepo {

    override suspend fun login(requestBody: AuthRequest): Resource<AuthModel> {
        if (data.status == "failed") {
            return Resource.Failed(message = "data failed")
        }
        return Resource.Success(AuthModel(status = "success"))
    }

    override suspend fun register(requestBody: AuthRequest): Resource<AuthModel> {
        if (data.status == "failed") {
            return Resource.Failed(message = "data failed")
        }
        return Resource.Success(AuthModel(status = "success"))
    }
}
