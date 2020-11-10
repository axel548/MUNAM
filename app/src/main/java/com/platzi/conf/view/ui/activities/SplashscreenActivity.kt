package com.platzi.conf.view.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.google.firebase.auth.FirebaseAuth
import com.platzi.conf.R
import kotlinx.android.synthetic.main.activity_splashscreen.*

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        firebaseAuth = FirebaseAuth.getInstance()

        val usr = firebaseAuth.currentUser
        val animacion = AnimationUtils.loadAnimation(this, R.anim.animacion)

        ivLogoPlatziConf.startAnimation(animacion)

        //val intent = Intent(this, LoginActivity::class.java)
        val menu = Intent(this, MainActivity::class.java)
        val signIn = Intent(this, LoginActivity::class.java)

        animacion.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }
            override fun onAnimationEnd(animation: Animation?) {
               if (usr != null){
                    startActivity(menu)
                    finish()
                }else{
                    startActivity(signIn)
                    finish()
                }
                /*startActivity(signIn)
                finish()*/
            }
            override fun onAnimationStart(animation: Animation?) {
            }
        })
    }

    /*private fun Activi(){
        val usr = firebaseAuth.currentUser
        Handler().postDelayed({
            if (usr != null){
                val menu = Intent(this, MainActivity::class.java)
                startActivity(menu)
                finish()
            }else{
                val signIn = Intent(this, LoginActivity::class.java)
                startActivity(signIn)
                finish()
            }
        }, 2000)
    }*/
}
