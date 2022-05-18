package com.example.transactiontracker.ui.main.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.transactiontracker.R
import com.example.transactiontracker.databinding.FragmentAddTransactionBinding
import com.example.transactiontracker.ui.base.ViewModelFactory
import com.example.transactiontracker.ui.main.viewmodel.MainViewModel
import com.example.transactiontracker.utils.Constant.Companion.SPINNER_DEFAULT
import com.example.transactiontracker.utils.Constant.Companion.SPINNER_EXPENSE
import com.example.transactiontracker.utils.Constant.Companion.SPINNER_INCOME

class TransactionDialog : DialogFragment(), AdapterView.OnItemSelectedListener {

    private lateinit var transactionBinding : FragmentAddTransactionBinding
    private lateinit var transactionType : String
    private val itemList = arrayOf(SPINNER_DEFAULT, SPINNER_EXPENSE, SPINNER_INCOME)

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(),
            ViewModelFactory(requireActivity().application)
        ).get(MainViewModel::class.java)
    }

    companion object {
        fun newInstance() : TransactionDialog {
            return TransactionDialog()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        transactionBinding = FragmentAddTransactionBinding.inflate(layoutInflater, container, false)
        return transactionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        populateTransactionTypeItems()
    }

    private fun populateTransactionTypeItems() {
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, itemList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        transactionBinding.spinner.adapter = arrayAdapter
    }

    private fun initClickListener() {
        transactionBinding.spinner.onItemSelectedListener = this
        transactionBinding.btnAdd.setOnClickListener {

            if(validateInfo()) {
                Toast.makeText(context, getString(R.string.toast_msg), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val transactionDescription = transactionBinding.etTranDesp.text.toString()
            val transactionValue = transactionBinding.etTranValue.text.toString().toLong()

            viewModel.addAndSaveTransaction(
                transactionType, transactionDescription, transactionValue)
            closeDialog()

        }

        transactionBinding.ivUp.setOnClickListener {
            val value = transactionBinding.etTranValue.text.toString().toLong() + 1
            transactionBinding.etTranValue.setText(value.toString())
        }

        transactionBinding.ivDown.setOnClickListener {
            if(transactionBinding.etTranValue.text.toString().toLong() > 0) {
                val value = transactionBinding.etTranValue.text.toString().toLong() - 1
                transactionBinding.etTranValue.setText(value.toString())
            }
        }
    }

    private fun validateInfo() : Boolean {
        return transactionType.equals(SPINNER_DEFAULT, true) ||
                transactionBinding.etTranDesp.text.toString().isEmpty() ||
                transactionBinding.etTranValue.text.toString().isEmpty() ||
                transactionBinding.etTranValue.text.toString().equals("0", true)

    }

    private fun closeDialog() {
        dismiss()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        transactionType = itemList[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}