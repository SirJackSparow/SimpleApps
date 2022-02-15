package com.startup.simpleapps.view.transfer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.startup.simpleapps.data.model.PayeesModel
import com.startup.simpleapps.data.model.TransferModel
import com.startup.simpleapps.data.requestbody.TransferRequest
import com.startup.simpleapps.repository.PayeesRepo
import com.startup.simpleapps.repository.TransferRepo
import com.startup.simpleapps.utils.Resource
import kotlinx.coroutines.launch

sealed class Transfer
data class Failed(val message: String) : Transfer()
data class Success(val data: TransferModel) : Transfer()
object Loading : Transfer()

sealed class Payees
data class PayeesData(val data: PayeesModel) : Payees()
data class FailedGetDataPayees(val message: String) : Payees()

class TransferViewModel(val repoPayees: PayeesRepo, val repoTransfer: TransferRepo) : ViewModel() {

    val transferResponse = MutableLiveData<Transfer>()
    val payeesResponse = MutableLiveData<Payees>()

    fun getPayees(auth: String) = viewModelScope.launch {
        when (val result = repoPayees.getPayeesData(auth)) {
            is Resource.Success -> payeesResponse.postValue(result.data?.let { PayeesData(it) })
            is Resource.Failed -> payeesResponse.postValue(result.message?.let {
                FailedGetDataPayees(
                    it
                )
            })
        }
    }

    fun getTransfer(auth: String, transferReq: TransferRequest) = viewModelScope.launch {
        transferResponse.postValue(Loading)
        when (val result = repoTransfer.transfer(auth, transferReq)) {
            is Resource.Success -> transferResponse.postValue(result.data?.let { Success(it) })
            is Resource.Failed -> transferResponse.postValue(result.message?.let { Failed(it) })
        }
    }
}
