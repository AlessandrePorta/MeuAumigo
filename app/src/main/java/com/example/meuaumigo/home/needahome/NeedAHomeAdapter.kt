package com.example.meuaumigo.home.needahome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.meuaumigo.R
import com.example.meuaumigo.home.needahome.model.PetVO

class NeedAHomeAdapter(
    val listPet : MutableList<PetVO>,
    val context : Context
) : Adapter<NeedAHomeAdapter.PetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pet_need_a_home, parent, false)
        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pets = listPet[position]
        holder.bind(pets)
    }

    override fun getItemCount(): Int {
        return listPet.size
    }

    inner class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.tvPetName)
        val img = itemView.findViewById<ImageView>(R.id.imgPet)

        fun bind(item: PetVO) {
            name.text = item.petName
            Glide.with(itemView.context).load(item.petImg).into(img)
        }
    }
}