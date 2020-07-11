package com.platzi.conf.model

import java.io.Serializable
import java.util.*

//Serializable: permite pasar objeto entre activities, funciona con las clases.
class Conference: Serializable {
    lateinit var title: String
    lateinit var description: String
    lateinit var tag: String
    lateinit var datetime: Date
    lateinit var speaker: String
}