package com.example.myapplication.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.RecyclerviewMainBinding
import com.example.myapplication.response.home.Content
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainAdapter  (
    var context : Context
) : RecyclerView.Adapter<MainAdapter.ViewHolder>(), Filterable {

    private var dataList: List<Content> =  arrayListOf<Content>()
    override fun getItemCount() =  categoryFilterList.size

    var charSearch=""

    var categoryFilterList: List<Content> = arrayListOf<Content>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recyclerview_main,
                parent,
                false
            )
        )

    @SuppressLint("DiscouragedApi", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val text = categoryFilterList[position].name.lowercase()
        val wordToFind = charSearch.lowercase()
        val word: Pattern = Pattern.compile(wordToFind)
        val match: Matcher = word.matcher(text)

        if (match.find()) {

            val subStringOne: String = categoryFilterList[position].name.substring(0, match.start())
            val subStringTwo: String = categoryFilterList[position].name.substring(match.start(), (match.end() ))
            val subStringThree: String = categoryFilterList[position].name.substring((match.end() ), categoryFilterList[position].name.length)

            holder.recyclerviewMainBinding.tvName.text = (Html.fromHtml(subStringOne+"<font color='#FFD700'>"+subStringTwo+"</font>"+subStringThree))

        }else{
            holder.recyclerviewMainBinding.tvName.text =categoryFilterList[position].name
        }



        try {
            val pic = context.resources.getDrawable(context.resources
                .getIdentifier(categoryFilterList[position].posterImage.replace(".jpg",""), "drawable", context.packageName))


            Glide.with(holder.recyclerviewMainBinding.imagePoster)
                .load(pic)
                .placeholder(R.drawable.placeholder_for_missing_posters)
                .into(holder.recyclerviewMainBinding.imagePoster)
        }
        catch (e: Exception){
            Glide.with(holder.recyclerviewMainBinding.imagePoster)
                .load(R.drawable.placeholder_for_missing_posters)
                .into(holder.recyclerviewMainBinding.imagePoster)
        }
    }

    inner class ViewHolder(
        val recyclerviewMainBinding: RecyclerviewMainBinding
    ) : RecyclerView.ViewHolder(recyclerviewMainBinding.root){}


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                charSearch = constraint.toString()
                categoryFilterList = if (charSearch.isEmpty()) {
                    dataList

                } else {
                    val resultList = ArrayList<Content>()
                    for (row in dataList) {
                        if (row.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = categoryFilterList
                return filterResults
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                categoryFilterList = results?.values as ArrayList<Content>
                notifyDataSetChanged()
            }

        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun update(updatedList: List<Content>){
        dataList = updatedList
        categoryFilterList = updatedList
        notifyDataSetChanged()
    }

}