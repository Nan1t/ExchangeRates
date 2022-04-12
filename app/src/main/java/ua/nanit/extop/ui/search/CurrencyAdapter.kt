package ua.nanit.extop.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.nanit.extop.R
import ua.nanit.extop.log.Logger
import ua.nanit.extop.monitoring.data.Currency

class CurrencyAdapter(
    private val clickListener: () -> Unit
) : RecyclerView.Adapter<CurrencyHolder>() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sourceList: List<Currency>

    private var filteredList = ArrayList<Currency>()
    private var selectedIndex: Int = -1

    var sourceListInit = false
    var selected: Currency? = null

    @SuppressLint("NotifyDataSetChanged")
    fun filter(pattern: String) {
        if (sourceListInit) {
            selectedIndex = -1
            selected = null

            val filtered = sourceList.filter {
                it.name.startsWith(pattern, true)
            }

            filteredList.clear()
            filteredList.addAll(filtered)
            notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(currencies: List<Currency>) {
        sourceList = currencies
        filteredList.clear()
        filteredList.addAll(currencies)
        sourceListInit = true
        notifyDataSetChanged()
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_currency, parent, false)

        return CurrencyHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        val currency = filteredList[position]

        holder.currencyId.text = currency.id
        holder.currencyName.text = currency.name

        if (holder.adapterPosition == selectedIndex) {
            holder.setSelectedColor()
        } else {
            holder.clearColor(position)
        }

        holder.itemView.setOnClickListener {
            if (selectedIndex != -1) {
                findHolder(selectedIndex)?.clearColor(selectedIndex)
            }

            selectedIndex = holder.adapterPosition
            selected = currency
            holder.setSelectedColor()
            clickListener()
        }
    }

    private fun findHolder(pos: Int): CurrencyHolder? {
        return recyclerView.findViewHolderForAdapterPosition(pos)
                as? CurrencyHolder
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }
}