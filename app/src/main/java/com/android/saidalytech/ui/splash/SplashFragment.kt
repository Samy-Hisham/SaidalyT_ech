package com.android.saidalytech.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.saidalytech.R
import com.android.saidalytech.uitls.MySharedPreference

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({

//            whichDestination()
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)


        }, 3000)
    }

    private fun whichDestination() {

        if (MySharedPreference.getUserToken().equals(null)) {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
        }
    }
}