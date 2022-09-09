package com.pigllet.hpuzzlegame.cool8puzzle.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.pigllet.hpuzzlegame.R
import com.pigllet.hpuzzlegame.cool8puzzle.iap.activities.IAPActivity
import com.pigllet.hpuzzlegame.cool8puzzle.ui.TilesAdapter
import com.pigllet.hpuzzlegame.cool8puzzle.ui.viewmodels.HomeViewModel
import com.pigllet.hpuzzlegame.databinding.FragmentHomeBinding
import viewBinding

class HomeFragment : Fragment(R.layout.fragment_home), OnItemClickListener, View.OnClickListener {

    private lateinit var tilesAdapter: TilesAdapter
    private val viewModel: HomeViewModel by viewModels()

    @VisibleForTesting
    val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tilesAdapter = TilesAdapter(view.context)

        with(binding) {
            gridview.adapter = tilesAdapter
            gridview.onItemClickListener = this@HomeFragment
            resetButton.setOnClickListener(this@HomeFragment)
            scrambleButton.setOnClickListener(this@HomeFragment)
        }

        collectStateFlow()
        binding.btnBuy.setOnClickListener {
            startActivity(Intent(activity, IAPActivity::class.java))
        }
    }

    private fun collectStateFlow() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            with(viewModel) {
                tiles.asLiveData().observe(viewLifecycleOwner) { newTiles ->
                    tilesAdapter.submitList(newTiles)
                }

                stepCount.asLiveData().observe(viewLifecycleOwner) { stepCount ->
                    binding.stepCounter.text = getString(R.string.steps, stepCount.toInt())
                }

                boardIsClickable.asLiveData().observe(viewLifecycleOwner) { isClickable ->
                    binding.gridview.isClickable = isClickable
                }

                boardIsSolved.asLiveData().observe(viewLifecycleOwner) { isSolved ->
                    binding.solvedText.isVisible = isSolved
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        with(binding) {
            gridview.adapter = null
            gridview.onItemClickListener = null
            resetButton.setOnClickListener(null)
            scrambleButton.setOnClickListener(null)
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        viewModel.onTileClicked(position)
        tilesAdapter.notifyDataSetChanged()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.reset_button -> {
                viewModel.resetBoard()
                tilesAdapter.notifyDataSetChanged()
            }
            R.id.scramble_button -> {
                viewModel.scrambleBoard()
                tilesAdapter.notifyDataSetChanged()
            }
        }
    }
}