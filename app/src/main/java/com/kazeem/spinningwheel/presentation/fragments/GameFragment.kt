package com.kazeem.spinningwheel.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kazeem.spinningwheel.R
import com.kazeem.spinningwheel.presentation.spinner.OnLuckyWheelReachTheTarget
import com.kazeem.spinningwheel.presentation.spinner.SpinWheel
import com.kazeem.spinningwheel.presentation.spinner.SpinnerItem
import com.kazeem.spinningwheel.presentation.viewModel.WheelViewModel
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class GameFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: WheelViewModel by activityViewModels { viewModelFactory }

    private val colors: List<Int> = listOf(R.color.wheel_lighter_background, R.color.wheel_darker_background)
    private var rnds: Int = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wheel = view.findViewById<SpinWheel>(R.id.lwv)
        val button = view.findViewById<Button>(R.id.spin_the_wheel)

        viewModel.getWheelData().observe(viewLifecycleOwner, Observer {
            Timber.d(it.toString())
            val wheelItems: MutableList<SpinnerItem> = ArrayList()
            for ((index, value) in it.withIndex()){
                wheelItems.add(SpinnerItem(colors[index%2], value.displayText))
            }
            wheel.addWheelItems(wheelItems)
            wheel.setLuckyWheelReachTheTarget(object : OnLuckyWheelReachTheTarget {
                override fun onReachTarget() {
                    Toast.makeText(context, it[rnds -1].displayText, Toast.LENGTH_LONG).show()
                    rnds = (1..wheelItems.size).random()
                }
            })
            rnds = (1..wheelItems.size).random()
            Timber.d(rnds.toString())
            wheel.setTarget(rnds)
            button.isEnabled = true
        })

        button.setOnClickListener {
            wheel.rotateWheelTo(rnds)
        }
    }
}