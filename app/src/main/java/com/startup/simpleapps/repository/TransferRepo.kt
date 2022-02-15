package com.startup.simpleapps.repository

import com.startup.simpleapps.data.model.TransferModel
import com.startup.simpleapps.data.requestbody.TransferRequest
import com.startup.simpleapps.utils.Resource

interface TransferRepo {
    suspend fun transfer(auth: String,requestTransfer: TransferRequest) : Resource<TransferModel>
}