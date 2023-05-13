package com.chslcompany.legacyproject.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chslcompany.legacyproject.databinding.ActivityMainBinding
import com.chslcompany.legacyproject.ui.adapter.ContactAdapter
import com.chslcompany.legacyproject.ui.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel : ContactViewModel by viewModels()
    private val contactAdapter : ContactAdapter by lazy { ContactAdapter()  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initRecyclerView()
        initObservables()
        setContentView(binding.root)
    }

    private fun initRecyclerView() {
        binding.rvContacts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = contactAdapter
        }
    }

    private fun initObservables() = binding.apply{
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collect { event ->
                    when (event) {
                        is ContactViewModel.StateUi.Success -> {
                            pbLoading.visibility = View.GONE
                            rvContacts.visibility = View.VISIBLE
                            contactAdapter.submitList(event.data)
                        }
                        is ContactViewModel.StateUi.Loading -> {
                            rvContacts.visibility = View.GONE
                            pbLoading.visibility = View.VISIBLE
                        }
                        is ContactViewModel.StateUi.Error -> {
                            pbLoading.visibility = View.GONE
                            rvContacts.visibility = View.GONE
                            Toast.makeText(this@MainActivity, event.errorText, Toast.LENGTH_SHORT)
                                .show()
                        }
                        else -> Unit
                    }
                }
            }
        }

    }

}