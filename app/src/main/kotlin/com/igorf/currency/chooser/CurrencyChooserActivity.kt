package com.igorf.currency.chooser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.igorf.currency.R
import com.igorf.currency.adapter.CurrencyChooserAdapter
import kotlinx.android.synthetic.main.activity_currency_chooser.currencyChooserList
import kotlinx.android.synthetic.main.activity_currency_chooser.searchEditText

class CurrencyChooserActivity : AppCompatActivity(R.layout.activity_currency_chooser) {

    private val adapter = CurrencyChooserAdapter(arrayListOf(), ::returnValue)

    private val currencyChooserViewModel: CurrencyChooserViewModel by lazy {
        ViewModelProvider(this, CurrencyChooserViewModelFactory(CurrencyRepository()))
            .get(CurrencyChooserViewModel::class.java)
    }

    companion object {
        operator fun invoke(context: Context) = Intent(context, CurrencyChooserActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupView()
        setupListeners()
        fetchData()
    }

    private fun setupView() {
        currencyChooserList.adapter = adapter
        currencyChooserList.layoutManager = LinearLayoutManager(this)
    }

    private fun setupListeners() {
        currencyChooserViewModel.getCurrencies().observe(
            this,
            Observer {
                adapter.updateList(it)
            }
        )

        searchEditText.addTextChangedListener(currencyChooserViewModel.listenerSearch())
    }

    private fun fetchData() {
        currencyChooserViewModel.fetchData()
    }

    private fun returnValue(resultCurrency: Pair<String, String>) {
        val intent = Intent().putExtra(Intent.EXTRA_INTENT, resultCurrency)
        setResult(RESULT_OK, intent)
        finish()
    }
}