package com.example.meuaumigo.ui.needahome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meuaumigo.databinding.ItemPetNeedAHomeBinding
import com.example.meuaumigo.model.HomePetVO

class NeedAHomeAdapter(
    val listPet: List<HomePetVO>,
    private val onPetClicked : (HomePetVO) -> Unit
) : RecyclerView.Adapter<NeedAHomeAdapter.PetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = ItemPetNeedAHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pets = listPet[position]
        holder.apply {
            binding.apply{
                tvPetName.text = pets.petName
                Glide.with(itemView.context).load(pets.petImg).into(img)
                root.setOnClickListener { onPetClicked(pets) }
            }
        }
    }

    override fun getItemCount(): Int {
        return listPet.size
    }

    inner class PetViewHolder(val binding: ItemPetNeedAHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        var img = binding.imgPet
    }
}