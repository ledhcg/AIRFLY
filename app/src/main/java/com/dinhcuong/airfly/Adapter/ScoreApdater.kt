package com.dinhcuong.airfly.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dinhcuong.airfly.Model.Users
import com.dinhcuong.airfly.R

class ScoreApdater(private val mList: List<Users>) : RecyclerView.Adapter<ScoreApdater.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.score_item, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        holder.IDNumber.text = (position + 1).toString()
        holder.accountName.text = ItemsViewModel.name
        holder.accountScore.text = "Score: " + ItemsViewModel.score.toString() + " | Bird killed: " + ItemsViewModel.birdsKilled.toString()
        holder.accountTime.text = ItemsViewModel.time

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val IDNumber: TextView = ItemView.findViewById(R.id.id_number)
        val accountName: TextView = ItemView.findViewById(R.id.account_name)
        val accountScore: TextView = ItemView.findViewById(R.id.account_score)
        val accountTime: TextView = ItemView.findViewById(R.id.account_time)
    }
}