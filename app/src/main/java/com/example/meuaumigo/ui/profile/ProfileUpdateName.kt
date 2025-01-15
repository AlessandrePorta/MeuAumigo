package com.example.meuaumigo.ui.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.meuaumigo.databinding.FragmentChangeNameBinding
import com.example.meuaumigo.ui.homemain.HomeActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProfileUpdateName : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentChangeNameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentChangeNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnUpdateName.setOnClickListener {
            (activity as HomeActivity).showLoading(true)
            (activity as HomeActivity).updateUserName(binding.etName.text.toString())
            dialog?.dismiss()
        }
    }
}