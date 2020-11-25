package com.platzi.conf.view.ui.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.util.Log.e
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.platzi.conf.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.handleCoroutineException
import java.util.*
import java.util.logging.Logger


class LoginActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 100
    private val callbackManager = CallbackManager.Factory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //setup
        setup()
        session()
    }
    override fun onStart() {
        super.onStart()
        authLayout.visibility = View.VISIBLE
    }

    private fun session(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email != null && provider != null){
            authLayout.visibility = View.INVISIBLE
           // showHome(email, ProviderType.valueOf(provider))
            showHome()
        }
    }

    private fun setup(){
        title="Autenticación"

        btnSigInGoogle.setOnClickListener {
            val googlecof = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestProfile()
                .build()
            val googleClient = GoogleSignIn.getClient(this, googlecof)
            googleClient.signOut()

            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }

        btnSigInFacebook.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))

            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult>{
                    override fun onSuccess(result: LoginResult?) {
                        result?.let {
                            val token = it.accessToken
                            val credential = FacebookAuthProvider.getCredential(token.token)
                            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                                if(it.isSuccessful){
                                    //showHome(it.result?.user?.email?:"", ProviderType.FACEBOOK)
                                    showHome()
                                }else{
                                    showAlert()
                                }
                            }
                        }
                    }

                    override fun onError(error: FacebookException?) {
                        showAlert()
                    }

                    override fun onCancel() {

                    }
                })
        }
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog:AlertDialog = builder.create()
        dialog.show()
    }

    private fun showHome(){
        //email:String, provider: ProviderType
        val homeIntent = Intent(this, MainActivity::class.java)
            /*.apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
            }*/
        startActivity(homeIntent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if(it.isSuccessful){
                            //showHome(account.email?:"", ProviderType.GOOGLE)
                            showHome()
                        }else{
                            showAlert()
                        }
                    }
                }
            }
            catch (e: ApiException){
                showAlert()
            }
        }
    }

}
