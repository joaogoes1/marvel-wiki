package com.joaogoes.marvelwiki.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.databinding.CharactersFragmentBinding

class CharactersFragment : Fragment() {

    private val binding: CharactersFragmentBinding by viewBinding(CharactersFragmentBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}