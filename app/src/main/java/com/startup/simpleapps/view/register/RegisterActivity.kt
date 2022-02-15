package com.startup.simpleapps.view.register

import android.view.LayoutInflater
import android.widget.Toast
import com.startup.simpleapps.base.BaseActivity
import com.startup.simpleapps.data.requestbody.AuthRequest
import com.startup.simpleapps.databinding.ActivityRegisterBinding
import com.startup.simpleapps.navigation.navigateToTransaction
import com.startup.simpleapps.utils.ButtonState
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private val vm: RegisterViewModel by inject()

    override fun onInit() {
        onAction()
        vm.registerResponse.observe(this, { state ->
            when (state) {
                is Loading -> bind?.registerBtn?.setState(ButtonState.LOADING)
                is Success -> {
                    state.data.token?.let {
                        CoroutineScope(Dispatchers.Default).launch {
                            dataStore.setToken(it)
                            dataStore.setUserName(bind?.usernameEdt?.text.toString())
                        }
                        navigateToTransaction(this)
                        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                    }
                }
                is Failed -> Toast.makeText(this, "Failed Registration", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onAction() {
        bind?.back?.setOnClickListener {
            onBackPressed()
        }
        bind?.registerBtn?.setOnClickListener {
            if (!checkValidation()){
                return@setOnClickListener
            }
            vm.register(
                AuthRequest(
                    username = bind?.usernameEdt?.text.toString(),
                    password = bind?.passwordEdt?.text.toString()
                )
            )
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
            bind?.confirmPasswordEdt?.text.toString() != bind?.passwordEdt?.text.toString() -> {
                bind?.confirmPasswordEdt?.setError("Confirm not match")
                return false
            }
        }
        return true
    }

    override fun getActivityBinding(layoutinflater: LayoutInflater): ActivityRegisterBinding =
        ActivityRegisterBinding.inflate(layoutInflater)

}
