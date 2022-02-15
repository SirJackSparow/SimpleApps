package com.startup.simpleapps.view.transaction

import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.startup.simpleapps.base.BaseActivity
import com.startup.simpleapps.databinding.ActivityTransactionListBinding
import com.startup.simpleapps.navigation.navigateToLogin
import com.startup.simpleapps.navigation.navigateToTransfer
import com.startup.simpleapps.utils.Constants.convert
import com.startup.simpleapps.utils.Constants.invisible
import com.startup.simpleapps.utils.Constants.visible
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first
import org.koin.android.ext.android.inject

class TransactionListActivity : BaseActivity<ActivityTransactionListBinding>() {

    private val vm: TrandsactionViewModel by inject()
    private val transactionAdapter = TransactionAdapter()
    override fun onInit() {
        onGetData()
        vm.BalanceResponse.observe(this, { state ->
            when (state) {
                is SuccessBalance -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        bind?.money?.text = state.data.balance?.convert()
                        bind?.accountNo?.text = state.data.accountNo
                        bind?.accountHolder?.text = dataStore.getUsername.first()
                    }
                }
                is FailedBalance -> {
                    Toast.makeText(this, "Failed Connected", Toast.LENGTH_SHORT).show()
                }
            }
        })
        vm.TransactionResponse.observe(this, { state ->
            when (state) {
                is Loading ->{
                    bind?.loading?.visible()
                    bind?.listTransactions?.invisible()
                }
                is Success -> {
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(2000)
                        transactionAdapter.submitList(state.data.toList())
                        bind?.listTransactions?.apply {
                            layoutManager = LinearLayoutManager(this@TransactionListActivity)
                            adapter = transactionAdapter
                        }
                        bind?.loading?.invisible()
                        bind?.listTransactions?.visible()
                    }
                }
                is Failed -> Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
        })
        onAction()
    }

    private fun onAction() {
        bind?.makeTransferBtn?.setOnClickListener {
            navigateToTransfer(this)
        }
        bind?.logOut?.setOnClickListener {
            navigateToLogin(this)
        }
        bind?.refresh?.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                onGetData()
                bind?.refresh?.isRefreshing = false
            }
        })
    }

    private fun onGetData() {
        CoroutineScope(Dispatchers.Main).launch {
            vm.getDataBAlance(dataStore.getToken.first())
            vm.getDataTransaction(dataStore.getToken.first())
        }
    }

    override fun getActivityBinding(layoutinflater: LayoutInflater): ActivityTransactionListBinding =
        ActivityTransactionListBinding.inflate(layoutInflater)

}