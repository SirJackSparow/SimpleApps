package com.startup.simpleapps.view.transaction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.startup.simpleapps.data.model.DataX
import com.startup.simpleapps.databinding.ItemHistoryTransactionBinding
import com.startup.simpleapps.utils.Constants.datFormat

class TransactionAdapter :
    ListAdapter<DataX, TransactionAdapter.TransactionsViewHolder>(diffCallBack) {


    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<DataX>() {
            override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                return oldItem.transactionId == newItem.transactionId
            }

            override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class TransactionsViewHolder(val view: ItemHistoryTransactionBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(model: DataX) {
            view.name.text = model.receipient?.accountHolder
            view.accountNo.text = model.receipient?.accountNo
            view.date.text = model.transactionDate?.datFormat()
            view.typeTransaction.text = model.transactionType
            view.amount.text = model.amount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val itemTransactions = ItemHistoryTransactionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionsViewHolder(itemTransactions)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }
}
