package com.platzi.conf.view.adapter

import com.platzi.conf.model.Speaker

interface SpeakersListener {
    fun onSpeakersClicked(speaker: Speaker, position: Int)
}