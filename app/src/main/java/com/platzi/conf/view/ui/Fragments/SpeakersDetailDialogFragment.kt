package com.platzi.conf.view.ui.Fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

import com.platzi.conf.R
import com.platzi.conf.model.Speaker
import kotlinx.android.synthetic.main.fragment_speakers_detail_dialog.*

/**
 * A simple [Fragment] subclass.
 */
    class  SpeakersDetailDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_speakers_detail_dialog, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_close)
        toolbar.setNavigationOnClickListener {
            dismiss()
        }

        val speaker = arguments?.getSerializable("speaker") as Speaker
        toolbar.title = speaker.name
        toolbar.setTitleTextColor(Color.WHITE)


        tvDetailPictureName.text = speaker.name
        tvDetailPictureDate.text = "Detalles: " + speaker.fech_pintura
        tvDetailAutorName.text = "Autor: " + speaker.autor
        tvDetailAutorDate.text = speaker.fech_autor
        tvDetailPictureBiography.text = "Biografía: " + speaker.biography

        Glide.with(this)
            .load(speaker.image)
            .into(ivDetailPictureImage)

        btnJugar.setOnClickListener {
            var bundle = bundleOf("speaker" to speaker)
            findNavController().navigate(R.id.action_speakersDetailFragmentDialog_to_gameFragment, bundle)
        }
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

}
