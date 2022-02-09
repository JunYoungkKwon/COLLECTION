package com.eight.collection.ui.writing.first

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eight.collection.databinding.FragmentWritefirstBottomBinding

class WritefirstBottomFragment : Fragment(){
    lateinit var binding : FragmentWritefirstBottomBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWritefirstBottomBinding.inflate(inflater,container,false)

        return binding.root
    }

}