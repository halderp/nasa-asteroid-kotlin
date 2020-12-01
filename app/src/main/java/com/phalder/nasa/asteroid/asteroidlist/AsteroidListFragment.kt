package com.phalder.nasa.asteroid.asteroidlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.phalder.nasa.asteroid.R
import com.phalder.nasa.asteroid.databinding.FragmentAsteroidListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AsteroidListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AsteroidListFragment : Fragment() {

    // Binding Objects
    private lateinit var binding: FragmentAsteroidListBinding

    //View Model


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_asteroid_list,container,false)
        return binding.root
    }


}