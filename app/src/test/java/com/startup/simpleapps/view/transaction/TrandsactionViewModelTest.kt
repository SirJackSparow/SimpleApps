package com.startup.simpleapps.view.transaction

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.startup.simpleapps.MainCoroutineRule
import com.startup.simpleapps.fakerepository.FakeBalanceRepository
import com.startup.simpleapps.fakerepository.FakeTransactionRepository
import com.startup.simpleapps.getOrAwaitValueTest
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TrandsactionViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var vm: TrandsactionViewModel

    @Before
    fun setUp() {
        vm = TrandsactionViewModel(FakeTransactionRepository(), FakeBalanceRepository())
    }

    @Test
    fun `transaction test`() {
        vm.getDataTransaction("")
        val value = vm.TransactionResponse.getOrAwaitValueTest()
        when (value) {
            is Success -> assertTrue(true)
            is Failed -> assertFalse(true)
        }
    }

}
