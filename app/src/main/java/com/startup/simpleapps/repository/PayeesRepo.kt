package com.startup.simpleapps.repository

import com.startup.simpleapps.data.model.PayeesModel
import com.startup.simpleapps.utils.Resource

interface PayeesRepo {
    suspend fun getPayeesData(auth: String) :  Resource<PayeesModel>
}