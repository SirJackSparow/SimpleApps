package com.startup.simpleapps.fakerepository

import com.startup.simpleapps.data.model.TransferModel
import com.startup.simpleapps.data.requestbody.TransferRequest
import com.startup.simpleapps.repository.TransferRepo
import com.startup.simpleapps.utils.Resource

class FakeTransferRepository : TransferRepo {
    override suspend fun transfer(
        auth: String,
        requestTransfer: TransferRequest
    ): Resource<TransferModel> {
        return Resource.Success(TransferModel("","success"))
    }
}