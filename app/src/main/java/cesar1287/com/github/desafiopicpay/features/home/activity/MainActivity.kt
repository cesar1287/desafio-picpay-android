package cesar1287.com.github.desafiopicpay.features.home.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cesar1287.com.github.desafiopicpay.R
import cesar1287.com.github.desafiopicpay.core.api.Status
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
        setupObservables()
        loadContent()
    }

    private fun setupObservables() {
        mainViewModel.usersLiveData.observe(this, Observer { resource ->
            when (resource?.status) {
                Status.ERROR -> {
                    tvMainErrorMessage.text = resource.message

                    setVisibility(
                        progress = View.GONE,
                        recycler = View.GONE,
                        error = View.VISIBLE
                    )
                }
                Status.SUCCESS -> {
                    setVisibility(
                        progress = View.GONE,
                        recycler = View.VISIBLE,
                        error = View.GONE
                    )
                    pbMainLoading.visibility = View.GONE
                    usersList.addAll(resource.data as Collection<User>)
                    homeAdapter.notifyDataSetChanged()
                }
            }
        })

        btMainRetry.setOnClickListener {
            loadContent()
            setVisibility(
                progress = View.VISIBLE,
                recycler = View.GONE,
                error = View.GONE
            )
        }
    }

    private fun setVisibility(progress: Int, recycler: Int, error: Int) {
        pbMainLoading.visibility = progress
        vgMainContainer.visibility = recycler
        vgMainErrorLayout.visibility = error
    }

    private fun loadContent() {
        mainViewModel.fetchUsers()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this@MainActivity)
        rvMainList.layoutManager = layoutManager
        homeAdapter = HomeAdapter(this@MainActivity, usersList)
        rvMainList.adapter = homeAdapter
    }
}
