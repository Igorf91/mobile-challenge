package com.igorf.currency

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.igorf.currency.chooser.CurrencyChooserActivity
import com.igorf.currency.quotes.QuotesRepository
import kotlinx.android.synthetic.main.activity_main.destinyCurrencyBtn
import kotlinx.android.synthetic.main.activity_main.initialCurrencyBtn
import kotlinx.android.synthetic.main.activity_main.inputValue
import kotlinx.android.synthetic.main.activity_main.resultValue

class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this, HomeViewModelFactory(QuotesRepository()))
            .get(HomeViewModel::class.java)
    }

    companion object {
        private const val INITIAL_CURRENCY_CODE = 123
        private const val CHANGE_CURRENCY_CODE = 321
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
        setupListeners()
    }

    private fun setupView() {
        initialCurrencyBtn.setOnClickListener {
            startActivityForResult(CurrencyChooserActivity(this), INITIAL_CURRENCY_CODE)
        }

        destinyCurrencyBtn.setOnClickListener {
            startActivityForResult(CurrencyChooserActivity(this), CHANGE_CURRENCY_CODE)
        }
    }

    private fun setupListeners() {
        inputValue.addTextChangedListener(homeViewModel.listenerValue())

        homeViewModel.result.observe(
            this,
            {
                resultValue.setText(it)
            }
        )
    }

    private fun setInitialValue(value: Pair<String, String>) {

    }

    @Suppress("UNCHECKED_CAST")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            val result = data.getSerializableExtra(Intent.EXTRA_INTENT) as Pair<String, String>
            if (requestCode == INITIAL_CURRENCY_CODE) {
                initialCurrencyBtn.text = result.second
                homeViewModel.setInitialCurrency(result)
            } else {
                destinyCurrencyBtn.text = result.second
                homeViewModel.setDestinyCurrency(result)
            }
        }
    }
}