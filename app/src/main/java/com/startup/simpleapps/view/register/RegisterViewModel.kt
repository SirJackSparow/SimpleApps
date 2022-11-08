package com.startup.simpleapps.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.startup.simpleapps.data.model.AuthModel
import com.startup.simpleapps.data.requestbody.AuthRequest
import com.startup.simpleapps.repository.AuthRepo
import com.startup.simpleapps.utils.Resource
import kotlinx.coroutines.launch

sealed class Register
object Loading : Register()
data class Success(val data: AuthModel) : Register()
data class Failed(val message: String) : Register()

class RegisterViewModel(private val repo: AuthRepo) : ViewModel() {

    private val _registerResponse = MutableLiveData<Register>()
    val registerResponse : LiveData<Register> = _registerResponse

    fun register(request: AuthRequest) = viewModelScope.launch {
        _registerResponse.postValue(Loading)
        when (val result = repo.register(request)) {
            is Resource.Success -> _registerResponse.postValue(result.data?.let { Success(it) })
            is Resource.Failed -> _registerResponse.postValue(result.message?.let { Failed(it) })
        }
    }
}