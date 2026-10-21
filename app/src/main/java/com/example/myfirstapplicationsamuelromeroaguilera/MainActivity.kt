package com.example.myfirstapplicationsamuelromeroaguilera

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var entradaNombre: EditText
    private lateinit var buttonEnviar: Button
    private lateinit var buttonReset: Button
    private lateinit var barraEdad: SeekBar
    private lateinit var textoEdad: TextView
    private lateinit var spinnerIdiomas: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        entradaNombre = findViewById(R.id.entradaNombre)
        buttonEnviar = findViewById(R.id.buttonEnviar)
        buttonReset = findViewById(R.id.buttonReset)
        barraEdad = findViewById(R.id.barraEdad)
        textoEdad = findViewById(R.id.textoEdad)
        spinnerIdiomas = findViewById(R.id.spinnerIdiomas)

        buttonEnviar.setOnClickListener {
            if (nombreValido() && idiomaValido()) {
                irASegundaPantalla()
            }
        }

        buttonReset.setOnClickListener {
            reset()
        }

        textoEdad.text = getString(R.string.barra_edad, 0)

        barraEdad.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textoEdad.text = getString(R.string.barra_edad, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        val idiomas: Array<String> = arrayOf("--- Elige un Idioma ---", "Español", "Inglés", "Aleman", "Chino", "Italiano", "Francés")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, idiomas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerIdiomas.adapter = adapter

    }

    private fun nombreValido(): Boolean {
        val nombre = entradaNombre.text.toString().trim()

        if (nombre.length < 3) {
            entradaNombre.error = getString(R.string.error_name)
            return false
        }
        return true
    }

    private fun idiomaValido(): Boolean {
        val posicionSeleccionada = spinnerIdiomas.selectedItemPosition
        if (posicionSeleccionada == 0) {
            Toast.makeText(this, "Debes seleccionar un idioma!!!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun irASegundaPantalla() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra(SecondActivity.EXTRA_NOMBRE, entradaNombre.text.toString().trim())
        intent.putExtra(SecondActivity.EXTRA_EDAD, barraEdad.progress)
        intent.putExtra(SecondActivity.EXTRA_IDIOMA, spinnerIdiomas.selectedItemPosition)
        startActivity(intent)
    }

    private fun reset() {
        entradaNombre.text.clear()
        entradaNombre.error = null
        barraEdad.progress = 0
        spinnerIdiomas.setSelection(0)
    }
}