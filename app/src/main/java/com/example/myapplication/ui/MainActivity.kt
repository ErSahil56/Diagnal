package com.example.myapplication.ui

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.response.home.Content
import com.example.myapplication.ui.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.et_search
import kotlinx.android.synthetic.main.activity_main.header_main
import kotlinx.android.synthetic.main.activity_main.header_search
import kotlinx.android.synthetic.main.activity_main.img_back
import kotlinx.android.synthetic.main.activity_main.img_cross
import kotlinx.android.synthetic.main.activity_main.img_search
import kotlinx.android.synthetic.main.activity_main.recycler_view
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class MainActivity : AppCompatActivity(),
    KodeinAware {


    override val kodein by kodein()
    private val factory by instance<MainActivityViewModelFactory>()


    val viewModel: MainActivityViewModel by viewModels {
        factory
    }

    private lateinit var mainAdapter: MainAdapter

    var responseList: ArrayList<Content> = arrayListOf<Content>()

    var pageCount = 1


    private var visibleThreshold = 1
    private var isUpcomingOrderLoading = false
    private var lastVisibleItem = 0
    private var totalItemCount = 0
    private var isSearch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.viewmodel = viewModel
        init()

        viewModel.getData(pageCount)

        viewModel.mainResponse.observe(this, Observer { response ->

            responseList.addAll(response)
            mainAdapter.update(responseList)

        })

    }


    private fun init() {
        img_back.setOnClickListener {
            finish()
        }

        img_search.setOnClickListener {
            isSearch = true
            header_main.visibility = View.GONE
            header_search.visibility = View.VISIBLE

            mainAdapter.filter.filter("foo")

            et_search.requestFocus()
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(et_search, InputMethodManager.SHOW_IMPLICIT)
        }
        img_cross.setOnClickListener {
            isSearch = false
            header_main.visibility = View.VISIBLE
            header_search.visibility = View.GONE

            et_search.setText("")
            mainAdapter.filter.filter("")


            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }

        mainAdapter = MainAdapter(this)

        recycler_view.also {
            it.layoutManager = LinearLayoutManager(this)
            it.setHasFixedSize(true)
            it.adapter = mainAdapter
        }

        val layoutManager: RecyclerView.LayoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        ) {
            GridLayoutManager(this, 3)
        } else {
            GridLayoutManager(this, 7)
        }


        recycler_view.layoutManager = layoutManager


        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager =
                    recyclerView.layoutManager as LinearLayoutManager?
                totalItemCount = linearLayoutManager!!.itemCount
                lastVisibleItem = linearLayoutManager!!.findLastVisibleItemPosition()
                if (!isUpcomingOrderLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {

                    if (!isSearch) {
                        if (pageCount < 3) {
                            pageCount += 1
                            viewModel.getData(pageCount)
                        }
                    }


                }
            }
        })


        et_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
               if(s.toString().length > 2){
                    mainAdapter.filter.filter(s.toString())
                }else if(isSearch){
                    mainAdapter.filter.filter("foo")
                }

            }
        })

    }


}