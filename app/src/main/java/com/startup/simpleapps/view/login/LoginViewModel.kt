package com.startup.simpleapps.view.login

import androidx.lifecycle.LiveData
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

    private val _loginState = MutableLiveData<Login>()
    val loginState: LiveData<Login> = _loginState

    fun login(request: AuthRequest) = viewModelScope.launch {
        _loginState.postValue(Loading)
        when (val result = repo.login(request)) {
            is Resource.Success -> _loginState.postValue(result.data?.let { Success(it) })
            is Resource.Failed -> _loginState.postValue(result.message?.let { FailedMessage(it) })
        }
    }
}
