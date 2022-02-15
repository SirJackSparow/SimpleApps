package com.startup.simpleapps.view.register

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
class RegisterViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var vm: RegisterViewModel

    @Before
    fun setUp() {
        vm = RegisterViewModel(FakeAuthRepository(AuthModel(status = "success")))
    }

    @Test
    fun `register`() {
        vm.register(AuthRequest(username = "test", password = "asdasd"))
        val value = vm.registerResponse.getOrAwaitValueTest()
        when (value) {
            is Success -> assertTrue(true)
            is Failed -> assertFalse(true)
        }
    }
}
