package com.startup.simpleapps.fakerepository

import com.startup.simpleapps.data.model.TransactionListModel
import com.startup.simpleapps.repository.TransactionRepo
import com.startup.simpleapps.utils.Resource

class FakeTransactionRepository : TransactionRepo {
    override suspend fun getTransactionData(auth: String): Resource<TransactionListModel> {
        return Resource.Success(TransactionListModel(null,"success"))
    }
}