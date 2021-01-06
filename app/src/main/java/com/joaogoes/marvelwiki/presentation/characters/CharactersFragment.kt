package com.joaogoes.marvelwiki.presentation.characters

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.databinding.CharactersFragmentBinding
import com.joaogoes.marvelwiki.di.Injectable
import javax.inject.Inject

class CharactersFragment : Fragment(R.layout.characters_fragment), Injectable {

    @Inject
    lateinit var viewModel: CharactersViewModel
    private val binding: CharactersFragmentBinding by viewBinding(CharactersFragmentBinding::bind)
    private val adapter = CharactersAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.charactersList.adapter = adapter
        binding.swipeToRefresh.setOnRefreshListener {
            binding.swipeToRefresh.isRefreshing = true
            viewModel.loadCharacters()
        }
        viewModel.viewState.characters.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        viewModel.viewState.state.observe(viewLifecycleOwner, {
            binding.swipeToRefresh.isRefreshing = false
            // TODO: handle state
        })
        viewModel.loadCharacters()
    }
}