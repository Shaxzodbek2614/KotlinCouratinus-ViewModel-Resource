package com.example.kotlincouratinusviewmodelresource.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.kotlincouratinusviewmodelresource.databinding.ItemRvBinding
import com.example.kotlincouratinusviewmodelresource.models.MyTodo

class TodoAdapter(var list: List<MyTodo> = emptyList(),val rvAction: RvAction): RecyclerView.Adapter<TodoAdapter.Vh>() {
    inner class Vh(private val item:ItemRvBinding):ViewHolder(item.root){
        fun onBind(myTodo: MyTodo){
            item.sarlavha.text = myTodo.sarlavha
            item.batafsil.text = myTodo.batafsil
            item.muddati.text = myTodo.sana
            item.more.setOnClickListener {
                rvAction.moreClick(myTodo,item.more)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    override fun getItemCount(): Int =list.size


    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    interface RvAction{
        fun moreClick(myTodo: MyTodo,imageView: ImageView)
    }
}