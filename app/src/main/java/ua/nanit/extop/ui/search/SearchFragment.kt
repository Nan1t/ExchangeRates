package ua.nanit.extop.ui.search

import android.os.Bundle
import android.view.View
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.Direction
import ua.nanit.extop.ui.base.BaseFragment
import ua.nanit.extop.ui.shared.SharedViewModel

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private lateinit var viewModel: SearchViewModel
    private lateinit var sharedViewModel: SharedViewModel

    private lateinit var layoutCurrencyIn: TextInputLayout
    private lateinit var layoutCurrencyOut: TextInputLayout
    private lateinit var fieldCurrencyIn: TextInputEditText
    private lateinit var fieldCurrencyOut: TextInputEditText
    private lateinit var btnSwap: FloatingActionButton
    private lateinit var btnConfirm: MaterialButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), SearchVmFactory(requireContext()))
            .get(SearchViewModel::class.java)
        sharedViewModel = sharedViewModel()

        layoutCurrencyIn = view.findViewById(R.id.rates_currency_in_layout)
        layoutCurrencyOut = view.findViewById(R.id.rates_currency_out_layout)
        fieldCurrencyIn = view.findViewById(R.id.rates_currency_in)
        fieldCurrencyOut = view.findViewById(R.id.rates_currency_out)
        btnSwap = view.findViewById(R.id.search_btn_swap)
        btnConfirm = view.findViewById(R.id.search_btn_confirm)

        fieldCurrencyIn.setOnClickListener {
            navigation.navToCurrencies()
            viewModel.loadCurrencies(Direction.IN)
        }

        fieldCurrencyOut.setOnClickListener {
            navigation.navToCurrencies()
            viewModel.loadCurrencies(Direction.OUT)
        }

        btnSwap.setOnClickListener(this::onSwapClicked)
        btnConfirm.setOnClickListener { viewModel.applySearchParams() }

        viewModel.currencyIn.observe(viewLifecycleOwner) {
            fieldCurrencyIn.setText(it.name)
        }

        viewModel.currencyOut.observe(viewLifecycleOwner) {
            fieldCurrencyOut.setText(it.name)
        }

        viewModel.enableConfirmBtn.observe(viewLifecycleOwner, this::observeEnableConfirmBtn)
        viewModel.search.observe(viewLifecycleOwner) { observeSearch() }
    }

    override fun onResume() {
        super.onResume()
        navigation.hide()
    }

    override fun onStop() {
        super.onStop()
        navigation.show()
    }

    private fun observeEnableConfirmBtn(value: Boolean) {
        btnConfirm.visibility = if (value) View.VISIBLE else View.GONE
    }

    private fun observeSearch() {
        sharedViewModel.signalRefreshRates()
        navigation.navigateUp()
    }

    private fun onSwapClicked(view: View) {
        btnSwap.animate()
            .setDuration(300)
            .rotation(180f)
            .withEndAction { view.rotation = 0.0F }
            .setInterpolator(FastOutSlowInInterpolator())
            .start()

        viewModel.swapCurrencies()
    }

}