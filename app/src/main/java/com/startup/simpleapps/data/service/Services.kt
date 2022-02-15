package com.startup.simpleapps.data.service

import com.startup.simpleapps.data.model.*
import com.startup.simpleapps.data.requestbody.AuthRequest
import com.startup.simpleapps.data.requestbody.TransferRequest
import retrofit2.Response
import retrofit2.http.*

interface Services {
    @POST("/login")
    suspend fun login(@Body requestAuth: AuthRequest): Response<AuthModel>

    @POST("/register")
    suspend fun register(@Body requestAuth: AuthRequest): Response<AuthModel>

    @POST("/transfer")
    suspend fun transfer(@Header("Authorization") auth: String,@Body requestTransferRequest: TransferRequest) :  Response<TransferModel>

    @GET("/balance")
    suspend fun balance(@Header("Authorization") auth: String) : Response<BalanceModel>

    @GET("/payees")
    suspend fun payees(@Header("Authorization") auth: String) : Response<PayeesModel>

    @GET("/transactions")
    suspend fun transactionsList(@Header("Authorization") auth: String): Response<TransactionListModel>

}
