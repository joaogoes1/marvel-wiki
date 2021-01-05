package com.joaogoes.marvelwiki.presentation.characters

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.databinding.CharactersFragmentBinding

class CharactersFragment : Fragment(R.layout.characters_fragment) {

    private val binding: CharactersFragmentBinding by viewBinding(CharactersFragmentBinding::bind)
}