package com.demoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.demoapp.App
import com.demoapp.R
import com.demoapp.adapter.CategoryDataAdapter
import com.demoapp.extension.getViewModel
import com.demoapp.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainActivityViewModel by lazy {
        getViewModel(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var categoryDataAdapter = CategoryDataAdapter()
        recycleList.adapter = categoryDataAdapter

        supportActionBar?.title= ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        (application as App).getAppComponent().inject(this)

        viewModel.getDataObserver().observe(this, {
            if (!it.isNullOrEmpty()) {
                categoryDataAdapter.setData(it)
                recycleList.visibility = View.VISIBLE
            } else {
                recycleList.visibility = View.GONE
            }
        })

        viewModel.getLoaderObserver().observe(this, {
            progress_circular.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.getMessageObserver().observe(this, {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(this@MainActivity, it, Toast.LENGTH_SHORT).show()
                viewModel.setMessage("")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home)
            finish()

        return true
    }
}