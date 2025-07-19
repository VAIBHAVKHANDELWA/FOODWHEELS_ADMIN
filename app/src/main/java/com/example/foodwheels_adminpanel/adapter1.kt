package com.example.foodwheels.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodwheels_adminpanel.R
import com.example.foodwheels_adminpanel.databinding.RecycleitemlistBinding
import com.example.foodwheels_adminpanel.model.additemclass
import com.google.firebase.database.DatabaseReference

class adapter1(
    private val context:Context,
    private val item:ArrayList<additemclass>,

    private val onCartEmpty: DatabaseReference // Callback to notify when cart is empty
) : RecyclerView.Adapter<adapter1.CartItemViewHolder>() {

    private val itemQuantity = MutableList(item.size) { 1 }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val binding =
            RecycleitemlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        Log.d("RecyclerView", "Total items: ${item.size}")
        return item.size
    }

    inner class CartItemViewHolder(private val binding:  RecycleitemlistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val quantity = itemQuantity[position]
                val menuname = item[position]
                val uriimage=menuname.foodimage
                if (!uriimage.isNullOrEmpty()) {
                    val uri = Uri.parse(uriimage)
                    Glide.with(context).load(uri).into(imgv)
                } else {
                    // Load a placeholder or default image if URL is null/empty
                    imgv.setImageResource(R.drawable.ice)
                }

                foodname.text = menuname.foodname
                price.text = menuname.foodprice
                tvQuantity.text = quantity.toString()



                btnIncrease.setOnClickListener {
                    itemQuantity[position]++
                    tvQuantity.text = itemQuantity[position].toString()
                }

                btnDecrease.setOnClickListener {
                    if (itemQuantity[position] > 1) {
                        itemQuantity[position]--
                        tvQuantity.text = itemQuantity[position].toString()
                    }
                }

                btnDelete.setOnClickListener {
                    item.removeAt(position)
                    item.removeAt(position)
                    item.removeAt(position)
                    itemQuantity.removeAt(position)

                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, item.size)

                    if (item.isEmpty()) {
                        onCartEmpty
                    }
                }
            }
        }
    }
}
