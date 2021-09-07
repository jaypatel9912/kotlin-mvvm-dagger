package com.demoapp.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.demoapp.App
import com.demoapp.R
import com.demoapp.adapter.CategoryViewDA
import com.demoapp.adapter.DataViewDA
import com.demoapp.adapterutils.AdapterConstants
import com.demoapp.adapterutils.BaseAdapter
import com.demoapp.adapterutils.DelegateAdapter
import com.demoapp.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainActivityViewModel by viewModels { viewModelFactory }

    private lateinit var categoryDataAdapter: BaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).getAppComponent().inject(this)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initAdapter()
        initDataObserver()
        initProgressObserver()
        initMessageObserver()
    }

    private fun initAdapter() {
        val map = HashMap<Int, DelegateAdapter<*, *>>()
        map[AdapterConstants.LIST_ITEM_CATEGORY] = CategoryViewDA()
        map[AdapterConstants.LIST_ITEM_DATA] = DataViewDA()
        categoryDataAdapter = BaseAdapter(map, this@MainActivity)
        recycleList.adapter = categoryDataAdapter
    }

    private fun initDataObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.categoryList.collect {
                if (!it.isNullOrEmpty()) {
                    categoryDataAdapter.setItemsAndNotify(it)
                    recycleList.visibility = View.VISIBLE
                } else {
                    recycleList.visibility = View.GONE
                }
            }
        }
    }

    private fun initMessageObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.message.collectLatest {
                if (!TextUtils.isEmpty(it)) {
                    Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                    viewModel.setMessage("")
                }
            }
        }
    }

    private fun initProgressObserver() {
        lifecycleScope.launchWhenCreated {
            viewModel.showLoader.collectLatest {
                progress_circular.visibility = if (it) View.VISIBLE else View.GONE
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()

        return true
    }

}
