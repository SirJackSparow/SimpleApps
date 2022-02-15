package com.startup.simpleapps.view.transfer

import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.startup.simpleapps.base.BaseActivity
import com.startup.simpleapps.data.model.Data
import com.startup.simpleapps.data.requestbody.TransferRequest
import com.startup.simpleapps.databinding.ActivityTransferBinding
import com.startup.simpleapps.utils.ButtonState
import com.startup.simpleapps.utils.Constants.EMPTY_STRING
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class TransferActivity : BaseActivity<ActivityTransferBinding>() {

    private val vm: TransferViewModel by inject()
    private val listPayees = arrayListOf<Data>()
    private var payees = EMPTY_STRING

    override fun onInit() {
        CoroutineScope(Dispatchers.Main).launch {
            vm.getPayees(dataStore.getToken.first())
        }
        vm.payeesResponse.observe(this, { state ->
            when (state) {
                is PayeesData -> {
                    state.data.data?.let { listPayees.addAll(it) }
                    bind?.payees?.adapter = SpinnerAdapterPayees(this, listPayees)
                }

                is FailedGetDataPayees -> Toast.makeText(this, state.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
        vm.transferResponse.observe(this, { state ->
            when (state) {
                is Loading -> bind?.transferBtn?.setState(ButtonState.LOADING)
                is Success -> {
                    bind?.transferBtn?.setState(ButtonState.ENABLED)
                    Toast.makeText(this, state.data.status, Toast.LENGTH_SHORT).show()
                }
                is Failed -> Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
        })
        onAction()
    }

    private fun onAction() {
        bind?.back?.setOnClickListener {
            onBackPressed()
        }
        bind?.transferBtn?.setOnClickListener {
            if (!checkValidation()) return@setOnClickListener
            CoroutineScope(Dispatchers.Main).launch {
                vm.getTransfer(
                    dataStore.getToken.first(),
                    TransferRequest(
                        bind?.amount?.text.toString().toInt(),
                        bind?.desc?.text.toString(),
                        payees
                    )
                )
            }
        }
        bind?.payees?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                payees = listPayees[p2].accountNo ?: ""
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                return
            }
        }
    }

    private fun checkValidation(): Boolean {
        if (bind?.amount?.text.toString().isEmpty())
            return false

        return true
    }

    override fun getActivityBinding(layoutinflater: LayoutInflater): ActivityTransferBinding =
        ActivityTransferBinding.inflate(layoutInflater)

}
