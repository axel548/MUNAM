package com.platzi.conf.view.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.platzi.conf.R
import com.platzi.conf.model.Speaker
import com.platzi.conf.view.adapter.SpeakersAdapter
import com.platzi.conf.view.adapter.SpeakersListener
import com.platzi.conf.viewmodel.SpeakerViewModel
import kotlinx.android.synthetic.main.fragment_speakers.*

/**
 * A simple [Fragment] subclass.
 */
class SpeakersFragment : Fragment(), SpeakersListener {

    private lateinit var speakerAdapter: SpeakersAdapter
    private lateinit var viewModel: SpeakerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_speakers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SpeakerViewModel::class.java)
        viewModel.refresh()

        speakerAdapter = SpeakersAdapter(this)

        rvSpeakers.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = speakerAdapter
        }
        observeViewModel()
    }

    fun observeViewModel(){
        viewModel.listSpeaker.observe(viewLifecycleOwner, Observer<List<Speaker>>{ speakers ->
            speakers.let{
                speakerAdapter.updateData(speakers)
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean>{
            if (it != null)
                rlBase.visibility = View.INVISIBLE
        })
    }

    override fun onSpeakersClicked(speaker: Speaker, position: Int) {
        var bundle = bundleOf("speaker" to speaker)
        findNavController().navigate(R.id.speakersDetailFragmentDialog, bundle)
    }

}
