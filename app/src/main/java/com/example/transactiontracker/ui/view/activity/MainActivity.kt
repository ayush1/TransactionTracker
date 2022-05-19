package com.example.transactiontracker.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.transactiontracker.data.model.BalanceModel
import com.example.transactiontracker.databinding.ActivityMainBinding
import com.example.transactiontracker.ui.view.adapter.TransactionAdapter
import com.example.transactiontracker.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val TAG = "Transaction_Fragment"

    private var transactionAdapter = TransactionAdapter()

    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initClickListener()
        setupObserver()
        fetchTransaction()
        fetchBalance()
    }

    private fun initView() {
        with(binding.rvTransaction) {
            adapter = transactionAdapter
            val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            layoutManager = mLayoutManager
            ItemTouchHelper(itemTouchHelper).attachToRecyclerView(this)
        }
    }

    private fun initClickListener() {
        binding.flAdd.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val transactionFragment = TransactionDialog.newInstance()
            transactionFragment.show(fragmentManager, TAG)
        }
    }

    private fun setupObserver() {
        viewModel.transactionLiveData.observe(this, { transactionList ->
            transactionAdapter.updateData(transactionList)
        })

        viewModel.balanceLiveData.observe(this, { balanceModel ->
            updateBalance(balanceModel)
            updateExpenseRatio(balanceModel)
        })
    }

    private fun updateExpenseRatio(balanceModel: BalanceModel) {
        val expenseRatio = ((balanceModel.expense.toDouble() / balanceModel.income.toDouble()) * 100).toInt()
        binding.card.pb.progress = expenseRatio
    }

    private fun updateBalance(balanceModel: BalanceModel) {
        binding.card.tvExpenseValue.text = "$" + balanceModel.expense
        binding.card.tvIncomeValue.text = "$" + balanceModel.income
        binding.card.tvBalanceValue.text = "$" + balanceModel.balance
    }

    private fun fetchBalance() {
        viewModel.getBalanceInfo()
    }

    private fun fetchTransaction() {
        viewModel.getTransaction()
    }

    private val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or
            ItemTouchHelper.RIGHT) {

        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            viewModel.removeTransaction(viewHolder.adapterPosition)
        }
    }
}