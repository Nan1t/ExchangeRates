package ua.nanit.extop.ui.rates

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.monitoring.data.Rate

class RateHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var rate: Rate? = null

    private val organization: TextView = view.findViewById(R.id.rate_organization)
    private val minAmount: TextView = view.findViewById(R.id.rate_min_amount)
    private val amountIn: TextView = view.findViewById(R.id.rate_amount_in)
    private val amountOut: TextView = view.findViewById(R.id.rate_amount_out)
    private val fund: TextView = view.findViewById(R.id.rate_fund)
    private val iconManual: ImageView = view.findViewById(R.id.rate_icon_manual)
    private val iconMediator: ImageView = view.findViewById(R.id.rate_icon_mediator)

    fun bind(rate: Rate) {
        this.rate = rate

        organization.text = rate.exchanger
        fund.text = rate.fund.toString()
        minAmount.text = rate.minAmount.toString()
        amountIn.text = rate.amountIn.toString()
        amountOut.text = rate.amountOut.toString()

        iconManual.visibility = if (rate.isManual) View.VISIBLE else View.GONE
        iconMediator.visibility = if (rate.isMediator) View.VISIBLE else View.GONE
    }

    fun setOnClick(listener: (Rate) -> Unit) {
        itemView.setOnClickListener {
            if (rate != null) {
                listener(rate!!)
            }
        }
    }

    fun colorizeBackground(pos: Int) {
        val bgColor = if (pos %2 == 0) R.color.light else R.color.lighter
        itemView.setBackgroundResource(bgColor)
    }

}