package com.example.tp1_julien_paquet_beaudoin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.SharedPreferences
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.widget.RadioButton
import android.widget.RadioGroup
import java.util.Random
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class RouletteActivity : AppCompatActivity() {

    //Variables
    private lateinit var btnJouer: Button
    private lateinit var txtMise: EditText
    private lateinit var txtPrediction: EditText
    private lateinit var lblNbrGagnant: TextView
    private lateinit var prefs: SharedPreferences
    private lateinit var radioEven: RadioButton
    private lateinit var radioOdd: RadioButton
    private lateinit var radioGroupJeux: RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_roulette)
        //Affichage du bouton de retour
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Initialisation des variables
        prefs = getSharedPreferences("MonFichierDeSauvegarde", MODE_PRIVATE)
        btnJouer = findViewById(R.id.btnJouer)
        txtMise = findViewById(R.id.txtMise)
        txtPrediction = findViewById(R.id.txtPrediction)
        radioEven = findViewById(R.id.radioEven)
        radioOdd = findViewById(R.id.radioOdd)
        radioGroupJeux = findViewById(R.id.radioGroupJeux)
        lblNbrGagnant = findViewById(R.id.lblNbrGagant)
        val nom = prefs.getString("session", "")


        //Gestion du radioGroup
        radioGroupJeux.setOnCheckedChangeListener() { group, checkedId ->
            if (checkedId == R.id.radioEven) {
                txtPrediction.setText(getString(R.string.radioEven))
                txtPrediction.isEnabled = false
            } else if (checkedId == R.id.radioOdd) {
                txtPrediction.setText(getString(R.string.radioOdd))
                txtPrediction.isEnabled = false
            } else {
                txtPrediction.setText("")
                txtPrediction.isEnabled = true
            }
        }

        //Bouton de jeu
        btnJouer.setOnClickListener() {
            //Les jetons de départ
            val jetonsDepart = prefs.getInt(nom, 0)
            //Le nombre aléatoire
            val nombreAleatoire = generateRandomNumber()
            //Test sur les jetons de départ
            if (jetonsDepart > 0) {
                //Test sur la mise
                if (txtMise.text.toString().toInt() in 1..jetonsDepart) {
                    //Calcul des jetons restants
                    val jetonResultMise = jetonsDepart - txtMise.text.toString().toInt()
                    //Si le nombre est pair ou impair et si un des radio bouton est coché
                    if (radioEven.isChecked || radioOdd.isChecked) {
                        //Affichage du nombre gagnant
                        lblNbrGagnant.text = getString(R.string.messageResultat, nombreAleatoire)
                        if (radioEven.isChecked && nombreAleatoire % 2 == 0) {
                            val jetonGagnes = txtMise.text.toString().toInt() * 2
                            val edit = prefs.edit()
                            edit.putInt(nom, jetonResultMise + jetonGagnes)
                            edit.apply()
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                getString(R.string.messageSnackBarGagne, jetonGagnes),
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else if (radioOdd.isChecked && nombreAleatoire % 2 != 0) {
                            val jetonGagnes = txtMise.text.toString().toInt() * 2
                            val edit = prefs.edit()
                            edit.putInt(nom, jetonResultMise + jetonGagnes)
                            edit.apply()
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                getString(R.string.messageSnackBarGagne, jetonGagnes),
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else {
                            val edit = prefs.edit()
                            edit.putInt(nom, jetonResultMise)
                            edit.apply()
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                getString(R.string.messageSnackBarPerdu),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        //Test sur le nombre prédit
                    } else if (txtPrediction.text.toString().toInt() in 0..36) {
                        //Affichage du nombre gagnant
                        lblNbrGagnant.text = getString(R.string.messageResultat, nombreAleatoire)
                        //Si le nombre prédit est égal au nombre aléatoire
                        if (txtPrediction.text.toString().toInt() == nombreAleatoire) {
                            val jetonGagnes = txtMise.text.toString().toInt() * 36
                            val edit = prefs.edit()
                            edit.putInt(nom, jetonResultMise + jetonGagnes)
                            edit.apply()
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                getString(R.string.messageSnackBarGagne, jetonGagnes),
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else {
                            val edit = prefs.edit()
                            edit.putInt(nom, jetonResultMise)
                            edit.apply()
                            Snackbar.make(
                                findViewById(android.R.id.content),
                                getString(R.string.messageSnackBarPerdu),
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            getString(R.string.messageErreurPred),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }

                } else {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        getString(R.string.messageErreurMise),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            } else {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    getString(R.string.messageErreurJetons),
                    Snackbar.LENGTH_LONG
                ).show()
                val intent = Intent(this@RouletteActivity, AccueilActivity::class.java)
                startActivity(intent)
            }
        }


    }

    //Fonction pour générer un nombre aléatoire
    private fun generateRandomNumber(): Int {
        val random = Random()
        return random.nextInt(37)
    }


}