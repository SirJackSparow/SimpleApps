package com.startup.simpleapps.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.startup.simpleapps.data.model.AuthModel
import androidx.lifecycle.viewModelScope
import com.startup.simpleapps.data.requestbody.AuthRequest
import com.startup.simpleapps.repository.AuthRepo
import com.startup.simpleapps.utils.Resource
import kotlinx.coroutines.launch

sealed class Login
data class Success(val data: AuthModel) : Login()
object Loading : Login()
data class FailedMessage(val errorMessage: String) : Login()

class LoginViewModel(private val repo: AuthRepo) : ViewModel() {

    val loginState = MutableLiveData<Login>()

    fun login(request: AuthRequest) = viewModelScope.launch {
        loginState.postValue(Loading)
        when (val result = repo.login(request)) {
            is Resource.Success -> loginState.postValue(result.data?.let { Success(it) })
            is Resource.Failed -> loginState.postValue(result.message?.let { FailedMessage(it) })
        }
    }
}
