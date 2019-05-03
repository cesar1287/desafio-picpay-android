package cesar1287.com.github.desafiopicpay.features.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.model.User
import cesar1287.com.github.desafiopicpay.core.util.GlideApp
import cesar1287.com.github.desafiopicpay.core.util.Home.KEY_EXTRA_USER
import cesar1287.com.github.desafiopicpay.core.util.Main.KEY_CODE_RECEIPT
import cesar1287.com.github.desafiopicpay.features.home.activity.MainActivity
import cesar1287.com.github.desafiopicpay.features.payment.activity.PaymentActivity
import kotlinx.android.synthetic.main.user_item.view.*

class HomeAdapter(private var context: Context, private var list: List<User>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, list[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(context: Context, user: User) = with(itemView) {
            itemView.rvContactsItemName.text = user.name
            itemView.rvContactsItemUsername.text = user.username

            GlideApp
                .with(context)
                .load(user.img)
                .into(itemView.rvContactsItemAvatar)

            itemView.contentLayout.setOnClickListener {
                startPaymentActivityForResult(context, user)
            }
        }

        private fun startPaymentActivityForResult(
            context: Context,
            user: User
        ) {
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra(KEY_EXTRA_USER, user)
            (context as? MainActivity)?.startActivityForResult(intent, KEY_CODE_RECEIPT)
        }
    }
}