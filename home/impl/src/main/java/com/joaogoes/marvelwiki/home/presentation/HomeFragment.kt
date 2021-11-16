package com.joaogoes.marvelwiki.home.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.joaogoes.marvelwiki.home.R
import com.joaogoes.marvelwiki.home.databinding.HomeFragmentBinding
import com.joaogoes.marvelwiki.utils.navigation.Navigator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private val binding: HomeFragmentBinding by viewBinding(HomeFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
//        binding.homeBottomNavigation.setupWithNavController(findNavController())
//        findNavController().addOnDestinationChangedListener { _, destination, _ ->
//            if(destination.id == R.id.home_fragment) {
//                binding.homeBottomNavigation.visibility = View.GONE
//            } else {
//                binding.homeBottomNavigation.visibility = View.VISIBLE
//            }
//        }
    }
}
