package com.example.transactiontracker.ui.view.adapter

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.transactiontracker.data.model.TransactionModel
import com.example.transactiontracker.databinding.TransactionItemLayoutBinding
import com.example.transactiontracker.utils.Constant.Companion.SPINNER_EXPENSE

class TransactionAdapter : RecyclerView.Adapter<ItemViewHolder>() {
    private var items = ArrayList<TransactionModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = TransactionItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.onBind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(list : ArrayList<TransactionModel>) {
        val transactionDiffCallback = TransactionDiffCallback(items, list)
        val diffResult = DiffUtil.calculateDiff(transactionDiffCallback)

        items.clear()
        items.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}

class ItemViewHolder(private val binding : TransactionItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(transactionModel: TransactionModel) {
        if(transactionModel.date != null) {
            binding.tvTransDesp.text = transactionModel.date
            binding.tvTransDesp.typeface = Typeface.DEFAULT_BOLD
            binding.tvTransAmount.visibility = GONE
        } else {
            binding.tvTransDesp.text = transactionModel.transactionDescription
            var amount = "$" + transactionModel.transactionValue

            if (transactionModel.transactionType.equals(SPINNER_EXPENSE))
                amount = "- $" + transactionModel.transactionValue

            binding.tvTransAmount.visibility = VISIBLE
            binding.tvTransAmount.text = amount

        }
    }
}