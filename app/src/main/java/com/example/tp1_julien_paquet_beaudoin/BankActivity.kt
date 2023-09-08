package com.example.tp1_julien_paquet_beaudoin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.app.Activity
import android.content.Intent

class BankActivity : AppCompatActivity() {

    //Variables
    private lateinit var btAchat: Button
    private lateinit var txtChips: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank)
        //Affichage du bouton de retour
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Initialisation des variables
        btAchat = findViewById(R.id.btnAchatJeton)
        txtChips = findViewById(R.id.txtChips)

        //Bouton d'achat de jetons
        btAchat.setOnClickListener(){
            val intent = Intent()
            intent.putExtra("chips", txtChips.text.toString().toInt())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}