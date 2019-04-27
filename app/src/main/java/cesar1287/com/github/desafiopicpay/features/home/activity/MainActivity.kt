package cesar1287.com.github.desafiopicpay.features.home.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.model.User
import cesar1287.com.github.desafiopicpay.features.home.adapter.HomeAdapter
import cesar1287.com.github.desafiopicpay.features.home.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainViewModel by lazy { MainViewModel() }
    private lateinit var homeAdapter: HomeAdapter
    private val usersList: MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        mainViewModel.fetchUsers()

        mainViewModel.usersLiveData.observe(this, Observer {
            pbMainLoading.visibility = View.GONE
            usersList.addAll(it)
            homeAdapter.notifyDataSetChanged()
        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this@MainActivity)
        rvMainList.layoutManager = layoutManager
        homeAdapter = HomeAdapter(this@MainActivity, usersList)
        rvMainList.adapter = homeAdapter
    }
}
