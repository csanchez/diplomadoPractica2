package com.csanchez.practica2.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.csanchez.practica2.R
import com.csanchez.practica2.databinding.MonsterItemBinding
import com.csanchez.practica2.model.Monster
import com.csanchez.practica2.views.MainActivity

class MonstersAdapter(private val context: Context, val monsters: ArrayList<Monster> ): RecyclerView.Adapter<MonstersAdapter.ViewHolder>(){

    private val layoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: MonsterItemBinding): RecyclerView.ViewHolder(view.root){
        val monsterName = view.monsterName
        val monsterImage = view.monsterImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MonsterItemBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        monsters[position].name.also {
            holder.monsterName.text = it
        }

        monsters[position].genrer.also {
            when(it){
                1 -> {
                    holder.monsterImage.setImageDrawable(context.getDrawable(R.drawable.icon_kaiju))
                }
                2 -> {
                    holder.monsterImage.setImageDrawable(context.getDrawable(R.drawable.icon_myth))
                }
                3 -> {
                    holder.monsterImage.setImageDrawable(context.getDrawable(R.drawable.icon_spacial))
                }
                4 -> {
                    holder.monsterImage.setImageDrawable(context.getDrawable(R.drawable.icon_paranormal))
                }
            }
        }

        holder.itemView.setOnClickListener {
           if(context is MainActivity) context.selectedGame(monsters[position])
        }

    }

    override fun getItemCount(): Int {
        return monsters.size
    }
}