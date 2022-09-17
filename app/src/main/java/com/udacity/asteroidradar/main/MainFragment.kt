package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.db.AsteroidDataBase
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.MainViewModelFactory
import com.udacity.asteroidradar.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        val binding = FragmentMainBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        viewModel = ViewModelProviders.of(
            this, MainViewModelFactory(AsteroidDataBase.getInstance(application),
                application)).get(MainViewModel::class.java)

        val adapter = Adapter(ItemClkListener { asteroid ->
            viewModel.onAsteroidClicked(asteroid)
        })
        binding.mainViewModel = viewModel


        with(binding) {
            lifecycleOwner = this@MainFragment
            root.asteroid_recycler.adapter = adapter
        }

        viewModel.asteroidsList.observe(viewLifecycleOwner) {
            it?.let {
                adapter.asteroidsList = it
            }
        }
        viewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    MainFragmentDirections.actionShowDetail(
                        viewModel.asteroidClicked
                    )
                )
                viewModel.toAsteroidDetailsNavigated()
            }
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_saved_menu -> {
                viewModel.getSavedAsteroids()
            }
            R.id.show_week_menu -> {
                viewModel.getWeekAsteroids()
            }
            R.id.show_today_menu -> {
                viewModel.getTodayAsteroids()
            }
        }
        return true
    }
}
