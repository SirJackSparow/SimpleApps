package com.startup.simpleapps.view.transaction

import androidx.lifecycle.LiveData
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

class TrandsactionViewModel(
    private val repoTransaction: TransactionRepo,
    private val repoBalance: BalanceRepo
) :
    ViewModel() {

    private val _transactionResponse = MutableLiveData<Transaction>()
    val transactionResponse : LiveData<Transaction> = _transactionResponse


    private val _balanceResponse = MutableLiveData<Balance>()
     val balanceResponse : LiveData<Balance> = _balanceResponse

    fun getDataTransaction(auth: String) = viewModelScope.launch {
        _transactionResponse.postValue(Loading)
        when (val result = repoTransaction.getTransactionData(auth)) {
            is Resource.Success -> _transactionResponse.postValue(result.data?.data?.let { Success(it) })
            is Resource.Failed -> _transactionResponse.postValue(result.message?.let { Failed(it) })
        }
    }

    fun getDataBAlance(auth: String) = viewModelScope.launch {
        _balanceResponse.postValue(LoadingBalance)
        when (val result = repoBalance.getBalanceData(auth)) {
            is Resource.Success -> _balanceResponse.postValue(result.data?.let { SuccessBalance(it) })
            is Resource.Failed -> _balanceResponse.postValue(result.message?.let { FailedBalance(it) })
        }
    }

}
