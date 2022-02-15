package com.startup.simpleapps.view.transfer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.startup.simpleapps.MainCoroutineRule
import com.startup.simpleapps.data.requestbody.TransferRequest
import com.startup.simpleapps.fakerepository.FakePayeesRepository
import com.startup.simpleapps.fakerepository.FakeTransferRepository
import com.startup.simpleapps.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TransferViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var vm: TransferViewModel


    @Before
    fun setUp() {
        vm = TransferViewModel(FakePayeesRepository(), FakeTransferRepository())
    }

    @Test
    fun `transfer test`() {
        vm.getTransfer("", TransferRequest(200, "desc", "1231"))
        val value = vm.transferResponse.getOrAwaitValueTest()
        when (value) {
            is Success -> assertTrue(true)
            is Failed -> assertFalse(true)
        }
    }

    @Test
    fun `payees test` () {
        vm.getPayees("")
        val value = vm.payeesResponse.getOrAwaitValueTest()
        when(value) {
            is PayeesData -> assertTrue(true)
            is FailedGetDataPayees -> assertFalse(false)
        }
    }
}
