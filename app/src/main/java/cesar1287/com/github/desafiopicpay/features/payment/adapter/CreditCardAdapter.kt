package cesar1287.com.github.desafiopicpay.features.payment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.model.CreditCard
import cesar1287.com.github.desafiopicpay.extensions.getLast4CreditCardNumbers
import cesar1287.com.github.desafiopicpay.features.payment.listener.CreditCardSelectionListener
import kotlinx.android.synthetic.main.credit_card_item.view.*

class CreditCardAdapter(
    private var list: Array<CreditCard>?,
    private var creditCardSelected: CreditCardSelectionListener
) : RecyclerView.Adapter<CreditCardAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.credit_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list?.get(position), creditCardSelected)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            creditCard: CreditCard?,
            creditCardSelected: CreditCardSelectionListener
        ) = with(itemView) {
            itemView.rvCreditCardsItemNumber.text = creditCard?.cardNumber?.getLast4CreditCardNumbers()

            itemView.contentLayout.setOnClickListener {
                creditCardSelected.onCreditCardSelected(creditCard)
            }
        }
    }
}