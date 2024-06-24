package com.example.meuaumigo.ui.petdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.FragmentPetDetailsBinding

class PetDetailsFragment : Fragment() {

    private var color = 1

    private lateinit var binding : FragmentPetDetailsBinding

    private val args by navArgs<PetDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentPetDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init(){
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ibLike.setOnClickListener {
            if(color == 0) {
                it.setBackgroundResource(R.drawable.ic_like)
                Toast.makeText(requireContext(), "Removido dos favoritos!", Toast.LENGTH_SHORT).show()
                color = 1
            } else {
                it.setBackgroundResource(R.drawable.ic_like_clicked)
                Toast.makeText(requireContext(), "Adicionado aos favoritos!", Toast.LENGTH_SHORT).show()
                color = 0
            }
        }
        retrieveArgs()
    }

    private fun retrieveArgs() = with(binding){
        tvPetName.text = args.name
        tvPetDescription.text = args.description
        tvPetAge.text = args.age
        tvPetBreed.text = args.breed
        tvPetLocalization.text = args.localization
        Glide.with(requireContext()).load(args.img).into(ivPetImg)
    }
}