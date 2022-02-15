package com.startup.simpleapps.view.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.startup.simpleapps.MainCoroutineRule
import com.startup.simpleapps.data.model.AuthModel
import com.startup.simpleapps.data.requestbody.AuthRequest
import com.startup.simpleapps.fakerepository.FakeAuthRepository
import com.startup.simpleapps.getOrAwaitValueTest
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var vmSuccess: LoginViewModel
    private lateinit var vmFailed: LoginViewModel

    @Before
    fun setUp() {
        vmSuccess = LoginViewModel(FakeAuthRepository(AuthModel(status = "success")))
        vmFailed = LoginViewModel(FakeAuthRepository(AuthModel(status = "failed")))
    }

    @Test
    fun `login success`() {
        vmSuccess.login(AuthRequest(username = "test", password = "asdasd"))
        val value = vmSuccess.loginState.getOrAwaitValueTest()
        when (value) {
            is Success -> assertTrue(true)
            is FailedMessage -> assertFalse(true)
        }
    }

    @Test
    fun `login failed`() {
        vmFailed.login(AuthRequest(username = "", password = ""))
        val value = vmFailed.loginState.getOrAwaitValueTest()
        when (value) {
            is Success -> assertFalse(true)
            is FailedMessage -> assertTrue(true)
        }
    }
}
