package com.startup.simpleapps.view.login

import android.view.LayoutInflater
import android.widget.Toast
import com.startup.simpleapps.base.BaseActivity
import com.startup.simpleapps.data.requestbody.AuthRequest
import com.startup.simpleapps.databinding.ActivityLoginBinding
import com.startup.simpleapps.navigation.navigateToRegistration
import com.startup.simpleapps.navigation.navigateToTransaction
import com.startup.simpleapps.utils.ButtonState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val vm: LoginViewModel by inject()

    override fun onInit() {
        CoroutineScope(Dispatchers.Main).launch {
            if (dataStore.isLogin.first()) {
                navigateToTransaction(this@LoginActivity)
            }
        }
        onAction()
        vm.loginState.observe(this) { state ->
            when (state) {
                is Loading -> {
                    bind?.loginBtn?.setState(ButtonState.LOADING)
                    bind?.registrationBtn?.isEnabled = false
                }
                is Success -> {
                    state.data.token?.let {
                        CoroutineScope(Dispatchers.Default).launch {
                            dataStore.setToken(it)
                            dataStore.setUserName(bind?.usernameEdt?.text.toString())
                            dataStore.setLogin(true)
                        }
                        navigateToTransaction(this)
                        Toast.makeText(this@LoginActivity, "success", Toast.LENGTH_SHORT).show()
                    }
                }
                is FailedMessage -> {
                    bind?.registrationBtn?.isEnabled = true
                    bind?.loginBtn?.setState(ButtonState.ENABLED)
                    Toast.makeText(this, "Failed Login", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun onAction() {
        bind?.loginBtn?.setOnClickListener {
            if (!checkValidation()) {
                return@setOnClickListener
            }
            vm.login(
                AuthRequest(
                    bind?.usernameEdt?.text.toString(),
                    bind?.passwordEdt?.text.toString()
                )
            )
        }
        bind?.registrationBtn?.setOnClickListener {
            navigateToRegistration(this)
        }
    }

    private fun checkValidation(): Boolean {
        when {
            bind?.usernameEdt?.text.toString().isEmpty() -> {
                bind?.usernameEdt?.setError("Empty Username")
                return false
            }
            bind?.passwordEdt?.text.toString().isEmpty() -> {
                bind?.passwordEdt?.setError("Empty Password")
                return false
            }
        }
        return true
    }

    override fun getActivityBinding(layoutinflater: LayoutInflater): ActivityLoginBinding =
        ActivityLoginBinding.inflate(layoutinflater)
}
