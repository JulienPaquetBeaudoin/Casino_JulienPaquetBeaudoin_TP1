package com.example.tp1_julien_paquet_beaudoin

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.content.Intent
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class AccueilActivity : AppCompatActivity() {

    //Variables
    private var activiteResultat: ActivityResultLauncher<Intent>? = null
    private lateinit var prefs: SharedPreferences
    private lateinit var labelNom: TextView
    private lateinit var labelJeton: TextView
    private lateinit var btRoulette: Button
    private lateinit var btBank: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accueil)
        //Affichage du bouton de retour
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Initialisation des variables
        labelJeton = findViewById(R.id.lblNbrJetons)
        labelNom = findViewById(R.id.lblBienvenue)
        btRoulette = findViewById(R.id.btnRoulette)
        btBank = findViewById(R.id.btnBank)
        prefs = getSharedPreferences("MonFichierDeSauvegarde", MODE_PRIVATE)
        val nom = prefs.getString("session", "")
        val jetons = prefs.getInt(nom, 0)
        labelNom.text = getString(R.string.messageBienvenue) + " " + nom
        labelJeton.text = getString(R.string.nbrChips) + " " + jetons.toString()

        //Initialisation de l'activité de retour
        activiteResultat = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val chips = data!!.getIntExtra("chips", 0)
                val jetonDepart = prefs.getInt(nom, 0)
                val edit = prefs.edit()
                edit.putInt(nom, jetonDepart + chips)
                edit.apply()
                val jetonsAchat = prefs.getInt(nom, 0)
                labelJeton.text = getString(R.string.nbrChips) + " " + jetonsAchat.toString()
            } else {
                Toast.makeText(this, getString(R.string.messageErreurBank), Toast.LENGTH_LONG)
                    .show()
            }
        }

        //Bouton roulette
        btRoulette.setOnClickListener() {
            val jetonsClick = prefs.getInt(nom, 0)
            if (jetonsClick == 0) {
                val intent = Intent(this@AccueilActivity, BankActivity::class.java)
                activiteResultat!!.launch(intent)
            } else {
                val intent = Intent(this@AccueilActivity, RouletteActivity::class.java)
                startActivity(intent)
            }
        }

        //Bouton banque
        btBank.setOnClickListener() {
            val intent = Intent(this@AccueilActivity, BankActivity::class.java)
            activiteResultat!!.launch(intent)
        }
    }
}