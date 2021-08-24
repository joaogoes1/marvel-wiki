package com.joaogoes.marvelwiki.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.joaogoes.marvelwiki.R
import com.joaogoes.marvelwiki.characters.presentation.characterslist.CharactersFragment
import com.joaogoes.marvelwiki.databinding.HomeFragmentBinding
import com.joaogoes.marvelwiki.favorites.presentation.FavoritesFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private val binding: HomeFragmentBinding by viewBinding(HomeFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.homeFragmentViewPager.adapter = HomeFragmentPagerAdapter(this)
        TabLayoutMediator(
            binding.homeFragmentTabLayout,
            binding.homeFragmentViewPager
        ) { tab, position ->
            tab.text = if (position == 0) "Personagens" else "Favoritos"
        }.attach()
    }

    private class HomeFragmentPagerAdapter(
        fragment: Fragment
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment = when (position) {
            0 -> CharactersFragment()
            1 -> FavoritesFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
