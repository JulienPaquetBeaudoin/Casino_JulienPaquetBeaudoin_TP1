package com.example.tp1_julien_paquet_beaudoin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher

class MainActivity : AppCompatActivity() {

    //Variables
    private lateinit var btConnexion: Button
    private lateinit var txtNom: EditText
    private lateinit var prefs: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialisation des variables
        prefs = getSharedPreferences("MonFichierDeSauvegarde", MODE_PRIVATE)
        btConnexion = findViewById(R.id.btnConnexion)
        txtNom = findViewById(R.id.txtNom)

        //Désactiver le bouton de connexion si le nom est vide
        btConnexion.isEnabled = false
        txtNom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Regarder si le texte est vide
                val isNotBlank = s?.isNotBlank() == true

                // Gestion de l'état du bouton
                btConnexion.isEnabled = isNotBlank
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        //Bouton de connexion
        btConnexion.setOnClickListener {
            val nom = txtNom.text.toString()
            val jetons = prefs.getInt(nom, -1)
            val edit = prefs.edit()
            if (jetons < 0) {
                edit.putInt(nom, 15)

            }
            edit.putString("session", nom)
            edit.apply()

            val intent = Intent(this@MainActivity, AccueilActivity::class.java)
            startActivity(intent)
        }

    }


}