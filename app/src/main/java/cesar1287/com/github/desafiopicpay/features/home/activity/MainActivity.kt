package cesar1287.com.github.desafiopicpay.features.home.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.api.Resource
import cesar1287.com.github.desafiopicpay.core.api.Status
import cesar1287.com.github.desafiopicpay.core.model.CreditCard
import cesar1287.com.github.desafiopicpay.core.model.TransactionResponse
import cesar1287.com.github.desafiopicpay.core.model.User
import cesar1287.com.github.desafiopicpay.core.util.Main.KEY_CODE_RECEIPT
import cesar1287.com.github.desafiopicpay.core.util.Payment.KEY_EXTRA_CREDIT_CARD
import cesar1287.com.github.desafiopicpay.core.util.Payment.KEY_EXTRA_TRANSACTION
import cesar1287.com.github.desafiopicpay.features.home.adapter.HomeAdapter
import cesar1287.com.github.desafiopicpay.features.home.viewmodel.MainViewModel
import cesar1287.com.github.desafiopicpay.features.payment.activity.ReceiptBottomSheetFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mainViewModel: MainViewModel? = null
    private lateinit var homeAdapter: HomeAdapter
    private val usersList: MutableList<User> = mutableListOf()
    private val originalUsersList: MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupObservables()
        loadContent()
    }

    private fun setupObservables() {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mainViewModel?.usersLiveData?.observe(this, Observer { resource ->
            processReturn(resource)
        })

        btMainRetry.setOnClickListener {
            doRetry()
        }

        etMainSearch.doOnTextChanged { text, _, _, after ->
            dataSetWasChanged(mainViewModel?.doSearch(originalUsersList, text, after) ?: listOf())
        }
    }

    private fun processReturn(resource: Resource?) {
        when (resource?.status) {
            Status.ERROR -> {
                processErrorReturn(resource)
            }
            Status.SUCCESS -> {
                processSuccessfulReturn(resource)
            }
        }
    }

    private fun doRetry() {
        loadContent()
        setVisibility(
            progress = View.VISIBLE,
            recycler = View.GONE,
            error = View.GONE
        )
    }

    private fun processSuccessfulReturn(resource: Resource) {
        setVisibility(
            progress = View.GONE,
            recycler = View.VISIBLE,
            error = View.GONE
        )
        pbMainLoading.visibility = View.GONE

        val usersList = resource.data as List<User>
        originalUsersList.clear()
        originalUsersList.addAll(usersList)
        dataSetWasChanged(usersList)
    }

    private fun dataSetWasChanged(list: List<User>) {
        usersList.clear()
        usersList.addAll(list)
        homeAdapter.notifyDataSetChanged()
    }

    private fun processErrorReturn(resource: Resource) {
        tvMainErrorMessage.text = resource.message

        setVisibility(
            progress = View.GONE,
            recycler = View.GONE,
            error = View.VISIBLE
        )
    }

    private fun setVisibility(progress: Int, recycler: Int, error: Int) {
        pbMainLoading.visibility = progress
        vgMainContainer.visibility = recycler
        vgMainErrorLayout.visibility = error
    }

    private fun loadContent() {
        mainViewModel?.fetchUsers()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this@MainActivity)
        rvMainList.layoutManager = layoutManager
        homeAdapter = HomeAdapter(this@MainActivity, usersList)
        rvMainList.adapter = homeAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == KEY_CODE_RECEIPT && resultCode == Activity.RESULT_OK) {
            val transactionResponse = data?.getParcelableExtra<TransactionResponse>(KEY_EXTRA_TRANSACTION)
            val creditCard = data?.getParcelableExtra<CreditCard>(KEY_EXTRA_CREDIT_CARD)

            val bundle = Bundle().apply {
                putParcelable(KEY_EXTRA_TRANSACTION, transactionResponse)
                putParcelable(KEY_EXTRA_CREDIT_CARD, creditCard)
            }
            val bottomSheetFragment = ReceiptBottomSheetFragment()
            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}
