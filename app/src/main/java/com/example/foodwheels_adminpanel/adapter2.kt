package com.example.foodwheels_adminpanel

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwheels_adminpanel.databinding.DeliveryitemsBinding
import com.example.foodwheels_adminpanel.databinding.RecycleitemlistBinding

class adapter2(private val cust:ArrayList<String>,private val money:ArrayList<String>):RecyclerView.Adapter<adapter2.ViewHolderforadapt>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderforadapt {
        val binding=DeliveryitemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolderforadapt(binding)
    }

    override fun getItemCount(): Int =cust.size


    override fun onBindViewHolder(holder: ViewHolderforadapt, position: Int) {
       holder.bind(position)
    }
    inner class ViewHolderforadapt(private val binding:DeliveryitemsBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
           binding.customerName.text=cust[position]
            binding.paymentStatus.text=money[position]
            val colormap= mapOf("recieved" to Color.GREEN,"nottrecieved" to Color.RED,"pending" to Color.YELLOW)
            binding.paymentStatus.setTextColor(colormap[money[position]]?:Color.BLACK)
            binding.deliveryStatus.setTextColor(colormap[money[position]]?:Color.BLACK)
            binding.itemIcon.backgroundTintList= ColorStateList.valueOf(colormap[money[position]]?:Color.BLACK)
        }

    }
}