package com.chslcompany.legacyproject.ui.activity

import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chslcompany.legacyproject.R
import com.chslcompany.legacyproject.data.model.Contact
import com.chslcompany.legacyproject.data.remote.ContactService
import com.chslcompany.legacyproject.ui.adapter.UserListAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var contactAdapter: UserListAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    private var contacts = mutableListOf<Contact>()

    private fun initRecyclerView() {

        val linearLayoutManager = LinearLayoutManager(this)
        contactAdapter = UserListAdapter(contacts)
        recyclerView = findViewById(R.id.rv_contacts)
        progressBar = findViewById(R.id.pb_loading)
        recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = contactAdapter
        }
    }

    private val url = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    private val gson: Gson by lazy { GsonBuilder().create() }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val service: ContactService by lazy {
        retrofit.create(ContactService::class.java)
    }

    override fun onResume() {
        super.onResume()

        initRecyclerView()

        progressBar.visibility = View.VISIBLE
        service.getUsers()
            .enqueue(object : Callback<List<Contact>> {
                override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
                    val message = getString(R.string.error)

                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE

                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<List<Contact>>,
                    response: Response<List<Contact>>
                ) {
                    progressBar.visibility = View.GONE
                    contactAdapter.getContacts(response.body()!!)
                }
            })
    }

}