package com.phalder.nasa.asteroid.asteroiddetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.phalder.nasa.asteroid.R
import com.phalder.nasa.asteroid.databinding.FragmentAsteroidDetailBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AsteroidDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AsteroidDetailFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // bindings
        val binding: FragmentAsteroidDetailBinding =DataBindingUtil.inflate(inflater,R.layout.fragment_asteroid_detail,container,false)
        binding.lifecycleOwner =  this
        // Inflate the layout for this fragment
        val arguments = AsteroidDetailFragmentArgs.fromBundle(requireArguments())

        val viewModelFactory = AsteroidDetailViewModelFactory(arguments.asteroidArg)
        val asteroidDetailViewModel = ViewModelProvider(this,viewModelFactory).get(AsteroidDetailViewModel::class.java)
        binding.asteroiddetailviewmodel = asteroidDetailViewModel
        return binding.root
    }

}