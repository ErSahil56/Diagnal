package com.example.myapplication.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.response.home.Content
import com.example.myapplication.response.home.MainResponse
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class MainActivityViewModel (
    private val context: Context
        ) : ViewModel() {


    private val _mainResponse = MutableLiveData<ArrayList<Content>>()
    val mainResponse: LiveData<ArrayList<Content>>
        get() = _mainResponse

    var responseList: ArrayList<Content> = arrayListOf<Content>()

    fun getData(pageCount : Int) {

        try {
            val main_obj = JSONObject(getJson(pageCount))
            val page_obj = main_obj.getJSONObject("page")
            val content_items_obj = page_obj.getJSONObject("content-items")
            val content = content_items_obj.getJSONArray("content")
            responseList.clear()

            for (i in 0 until content.length()) {
                val content_inside = content.getJSONObject(i)

                val name = content_inside.getString("name")
                val poster_image = content_inside.getString("poster-image")

                var cont = Content(name,poster_image)
                responseList.add(i,cont)

            }

            _mainResponse.value = responseList

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }


    private fun getJson(pageCount : Int): String? {
        var json: String? = null
        json = try {

            if(pageCount == 1){
                val `is`: InputStream = context.assets.open("apis/page_one.json")
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                String(buffer)
            }
            else if(pageCount == 2){
                val `is`: InputStream = context.assets.open("apis/page_two.json")
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                String(buffer)
            }
            else if(pageCount == 3){
                val `is`: InputStream = context.assets.open("apis/page_three.json")
                val size = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                String(buffer)
            }else{
                 null
            }


        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}