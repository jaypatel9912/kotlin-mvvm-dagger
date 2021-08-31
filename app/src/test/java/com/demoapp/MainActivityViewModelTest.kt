package com.demoapp

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.demoapp.database.AppDatabase
import com.demoapp.viewmodel.MainActivityViewModel
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityViewModelTest : TestCase() {

    private lateinit var viewModel: MainActivityViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
         viewModel = MainActivityViewModel(context.applicationContext as Application)
    }

    @Test
    fun testViewModel(){
        viewModel.populateData()
        var result = viewModel.getDataObserver()
        assertTrue(result != null)
    }
}