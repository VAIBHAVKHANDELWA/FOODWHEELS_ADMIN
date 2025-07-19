package com.example.foodwheels_adminpanel

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.foodwheels_adminpanel.databinding.ListBinding


class adapter3(private val customer:ArrayList<String>,private val quantity:ArrayList<String>,private val image:ArrayList<Int>, private val context: Context):RecyclerView.Adapter<adapter3.adaptclass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptclass {
        val binding=ListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return adaptclass(binding)
    }

    override fun getItemCount(): Int =customer.size

    override fun onBindViewHolder(holder: adaptclass, position: Int) {
       holder.bind(position)
    }
    inner class adaptclass(private val binding:ListBinding):RecyclerView.ViewHolder(binding.root){
        private var isaccepted=false
        fun bind(position: Int) {
            binding.customer.text=customer[position]
            binding.quantity.text=quantity[position]
            binding.image.setImageResource(image[position])
            binding.acceptButton.apply{
                if(!isaccepted)
                {
                    text="Accept"

                }
                else{
                    text="Dispatch"
                }
                setOnClickListener {
                    if(!isaccepted)
                    {
                        text="Dispatch"
                        isaccepted=true
                       deletefun("ORDER IS ACCEPTED")
                    }
                    else{
                        customer.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position,customer.size)
                        deletefun("ORDER IS DISPATCHED")

                    }
                }
            }
        }

        private fun deletefun(message:String) {
             Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
        }


    }

}