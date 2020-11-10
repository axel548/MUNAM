package com.platzi.conf.view.ui.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.platzi.conf.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_speakers_detail_dialog.*

enum class ProviderType {
    GOOGLE,
    FACEBOOK
}

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        //Configuración de la Toolbar
        configNav()
        updateNavHeader()
        toolbar()
    }

    fun configNav(){
        NavigationUI.setupWithNavController(bnvMenu, Navigation.findNavController(this, R.id.fragContent))
    }

   /* private fun setup(provider:String)    {
            //Borrado de datos
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            if (provider == ProviderType.FACEBOOK.name){
                LoginManager.getInstance().logOut()
            }
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
    }*/

   private fun toolbar(){
       val toolbar: Toolbar = findViewById(R.id.improvisado)
       setSupportActionBar(toolbar)


       val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.fragContent)

        appBarConfiguration = AppBarConfiguration(setOf(R.id.navHomFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragContent)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_logout -> showAlert()
        }
        return true
    }

    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar")
            .setIcon(R.drawable.ic_round_warning)
            .setMessage("¿Desea Cerrar Sesión?")
            .setPositiveButton("Cerrar Sesión", { dialogInterface: DialogInterface, i: Int ->  Logout() })
            .setNegativeButton("Cancelar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun updateNavHeader(){
        val navView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navView.getHeaderView(0)

        val tvCorreo: TextView = headerView.findViewById(R.id.txtCorreo)
        val tvUser: TextView = headerView.findViewById(R.id.txtusuario)
        val ivFotou: ImageView = headerView.findViewById(R.id.ivfotoUsuario)
        val user = FirebaseAuth.getInstance().currentUser


        tvUser.text = user?.displayName
        tvCorreo.text = user?.email
        Glide.with(this)
            .load(user?.photoUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(ivFotou)
    }

    private fun Logout(){
        val signIn = Intent(this, LoginActivity::class.java)
        //Borrado de datos
        /*val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()*/
        FirebaseAuth.getInstance().signOut()
        startActivity(signIn)
        finish()
        //onBackPressed()
    }
}
