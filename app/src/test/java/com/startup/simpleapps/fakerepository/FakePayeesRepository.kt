package com.startup.simpleapps.fakerepository

import com.startup.simpleapps.data.model.PayeesModel
import com.startup.simpleapps.repository.PayeesRepo
import com.startup.simpleapps.utils.Resource

class FakePayeesRepository : PayeesRepo {
    override suspend fun getPayeesData(auth: String): Resource<PayeesModel> {
        return Resource.Success(PayeesModel(null, status = "success"))
    }
}