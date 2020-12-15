package com.phalder.nasa.asteroid.asteroidlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.phalder.nasa.asteroid.R
import com.phalder.nasa.asteroid.databinding.FragmentAsteroidListBinding


/**
 * A simple [Fragment] subclass.
 * Use the [AsteroidListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AsteroidListFragment : Fragment() {

    // Binding Objects
    private lateinit var binding: FragmentAsteroidListBinding

    //View Model
    private val viewModel: AsteroidListViewModel by lazy {
        ViewModelProvider(this).get(AsteroidListViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAsteroidListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        // bind the viewmodel to databinding which will initiate network requests
        binding.viewModel = viewModel

        val adapter = NeoFeedListAdapter(AsteroidItemClickListener { asteroid ->
            // Navigate to details fragment . Parameter is the Clicked Asteroid
            findNavController().navigate(AsteroidListFragmentDirections.actionAsteroidListFragmentToAsteroidDetailFragment(asteroid))

        })
        binding.neofeedRecylerlist.adapter = adapter

        // observe the neo feed list
        viewModel.neoFeedList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}