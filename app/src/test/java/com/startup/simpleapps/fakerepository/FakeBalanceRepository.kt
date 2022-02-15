package com.startup.simpleapps.fakerepository

import com.startup.simpleapps.data.model.BalanceModel
import com.startup.simpleapps.repository.BalanceRepo
import com.startup.simpleapps.utils.Resource

class FakeBalanceRepository : BalanceRepo {
    override suspend fun getBalanceData(auth: String): Resource<BalanceModel> {
        return Resource.Success(BalanceModel(null, null, status = "success"))
    }
}