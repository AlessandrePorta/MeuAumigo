package com.example.meuaumigo.ui.needahome

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.meuaumigo.R
import com.example.meuaumigo.model.HomePetVO

class NeedAHomeAdapter(
    val listPet: List<HomePetVO>,
    val context: Context
) : RecyclerView.Adapter<NeedAHomeAdapter.PetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_pet_need_a_home, parent, false)
        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pets = listPet[position]
        holder.bind(pets)
    }

    override fun getItemCount(): Int {
        return listPet.size
    }

    inner class PetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tvPetName)
//        val img = itemView.findViewById<ImageView>(R.id.imgPet)

        fun bind(item: HomePetVO) {
            name.text = item.petName
//            Glide.with(itemView.context).load(item.petImg).into(img)
        }
    }
}