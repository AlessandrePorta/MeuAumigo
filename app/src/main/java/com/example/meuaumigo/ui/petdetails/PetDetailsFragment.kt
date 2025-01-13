package com.example.meuaumigo.ui.petdetails

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.meuaumigo.R
import com.example.meuaumigo.databinding.FragmentPetDetailsBinding
import com.example.meuaumigo.ui.homemain.HomeActivity

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
        binding.ibShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            startActivity(shareIntent)
        }
        retrieveArgs()
        Handler().postDelayed({
            (activity as HomeActivity).showLoading(false)
        }, 1000)
    }

    private fun retrieveArgs() = with(binding){
        tvPetName.text = args.name
        tvPetDescription.text = args.description
        tvPetAge.text = if(args.age == "1"){"${args.age} ano"} else {"${args.age} anos"}
        tvPetBreed.text = args.breed
        tvPetLocalization.text = args.localization
        Glide.with(requireContext()).load(args.img).into(ivPetImg).request?.isComplete.apply {
            (activity as HomeActivity).showLoading(false)
        }
    }
}