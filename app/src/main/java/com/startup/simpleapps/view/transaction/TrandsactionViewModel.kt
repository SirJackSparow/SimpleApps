package com.startup.simpleapps.view.transaction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.startup.simpleapps.data.model.BalanceModel
import com.startup.simpleapps.data.model.DataX
import com.startup.simpleapps.repository.BalanceRepo
import com.startup.simpleapps.repository.TransactionRepo
import com.startup.simpleapps.utils.Resource
import kotlinx.coroutines.launch

sealed class Transaction
object Loading : Transaction()
data class Success(val data: List<DataX>) : Transaction()
data class Failed(val message: String) : Transaction()

sealed class Balance
object LoadingBalance : Balance()
data class SuccessBalance(val data: BalanceModel) : Balance()
data class FailedBalance(val message: String) : Balance()

class TrandsactionViewModel(val repoTransaction: TransactionRepo, val repoBalance: BalanceRepo) :
    ViewModel() {

     val TransactionResponse = MutableLiveData<Transaction>()
     val BalanceResponse = MutableLiveData<Balance>()

    fun getDataTransaction(auth:String) = viewModelScope.launch {
        TransactionResponse.postValue(Loading)
        when (val result = repoTransaction.getTransactionData(auth)) {
            is Resource.Success -> TransactionResponse.postValue(result.data?.data?.let { Success(it) })
            is Resource.Failed -> TransactionResponse.postValue(result.message?.let { Failed(it) })
        }
    }

    fun getDataBAlance(auth:String) = viewModelScope.launch {
        BalanceResponse.postValue(LoadingBalance)
        when (val result = repoBalance.getBalanceData(auth)) {
            is Resource.Success -> BalanceResponse.postValue(result.data?.let { SuccessBalance(it) })
            is Resource.Failed -> BalanceResponse.postValue(result.message?.let { FailedBalance(it) })
        }
    }

}
