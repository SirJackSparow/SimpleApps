package com.startup.simpleapps.repository

import com.startup.simpleapps.data.model.BalanceModel
import com.startup.simpleapps.utils.Resource

interface BalanceRepo {
    suspend fun getBalanceData(auth: String) :  Resource<BalanceModel>
}