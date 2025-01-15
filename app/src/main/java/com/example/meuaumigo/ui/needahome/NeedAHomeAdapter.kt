package com.example.meuaumigo.ui.needahome

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.ItemPetNeedAHomeBinding
import com.example.meuaumigo.model.HomePetVO
import com.example.meuaumigo.ui.homemain.HomeActivity
import kotlin.coroutines.coroutineContext

class NeedAHomeAdapter(
    val listPet: List<HomePetVO>,
    private val onPetClicked : (HomePetVO) -> Unit,
    private val onLikeClicked : (btn : ImageButton) -> Unit
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
            binding.ibLike.setOnClickListener {
                onLikeClicked(binding.ibLike)
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