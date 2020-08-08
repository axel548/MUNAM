package com.platzi.conf.view.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import com.platzi.conf.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

   /* private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var auth: FirebaseAuth.AuthStateListener
    val etUser: TextView = findViewById(R.id.tvUser)
    val btnLogout:Button = findViewById(R.id.btnLogOut)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Configuración de la Toolbar
        setActionBar(findViewById(R.id.tollbarMain))
        configNav()
/*
        btnLogout.setOnClickListener {
            signOut()
        }*/
    }
    fun configNav(){
        NavigationUI.setupWithNavController(bnvMenu, Navigation.findNavController(this, R.id.fragContent))
    }

}
