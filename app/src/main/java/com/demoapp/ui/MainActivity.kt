package com.demoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.demoapp.App
import com.demoapp.R
import com.demoapp.adapter.CategoryDataAdapter
import com.demoapp.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainActivityViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).getAppComponent().inject(this)

        setContentView(R.layout.activity_main)

        var categoryDataAdapter = CategoryDataAdapter()
        recycleList.adapter = categoryDataAdapter

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lifecycleScope.launchWhenCreated {
            viewModel.categoryList.collectLatest {
                if (!it.isNullOrEmpty()) {
                    categoryDataAdapter.setData(it)
                    recycleList.visibility = View.VISIBLE
                } else {
                    recycleList.visibility = View.GONE
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.showLoader.collectLatest {
                progress_circular.visibility = if (it) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.message.collectLatest {
                if (!it.isNullOrEmpty()) {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.setMessage("")
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()

        return true
    }
}