package com.eight.collection.ui.writing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.FragmentTopBinding

class TopFragment : Fragment(){
    lateinit var binding : FragmentTopBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTopBinding.inflate(inflater,container,false)

        return binding.root
    }

}