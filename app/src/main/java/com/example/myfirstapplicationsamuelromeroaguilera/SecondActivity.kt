package com.example.myfirstapplicationsamuelromeroaguilera

import android.content.Intent
import android.os.Bundle
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

class SecondActivity : AppCompatActivity() {
    private lateinit var entradaNombre: EditText
    private lateinit var buttonEditar: Button
    private lateinit var buttonReset: Button
    private lateinit var barraEdad: SeekBar
    private lateinit var textoEdad: TextView
    private lateinit var spinnerIdiomas: Spinner

    private var name: String? = ""
    private var age: String? = ""
    private var language: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        entradaNombre = findViewById(R.id.entradaNombre)
        buttonEditar = findViewById(R.id.buttonEnviar)
        buttonReset = findViewById(R.id.buttonReset)
        barraEdad = findViewById(R.id.barraEdad)
        textoEdad = findViewById(R.id.textoEdad)
        spinnerIdiomas = findViewById(R.id.spinnerIdiomas)

        buttonEditar.text = "EDITAR"

        name = intent.extras?.getString("DATA_NAME")
        age = intent.extras?.getString("DATA_AGE")
        language = intent.extras?.getString("DATA_LANGUAGE")

        entradaNombre.setText(name)
        barraEdad.progress = age?.toIntOrNull() ?: 0
        textoEdad.text = getString(R.string.barra_edad, barraEdad.progress)
        spinnerIdiomas.setSelection(language?.toIntOrNull() ?: 0)

        buttonEditar.setOnClickListener {
            if (nombreValido() && idiomaValido()) {
                val resultado = Intent()
                resultado.putExtra("DATA_NAME", entradaNombre.text.toString())
                resultado.putExtra("DATA_AGE", barraEdad.progress.toString())
                resultado.putExtra("DATA_LANGUAGE", spinnerIdiomas.selectedItemPosition.toString())
                setResult(RESULT_OK, resultado)
                finish()
            }
        }

        buttonReset.setOnClickListener {
            reset()
        }

        textoEdad.text = getString(R.string.barra_edad, barraEdad.progress)

        barraEdad.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textoEdad.text = getString(R.string.barra_edad, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

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

    private fun reset() {
        entradaNombre.text.clear()
        entradaNombre.error = null
        barraEdad.progress = 0
        spinnerIdiomas.setSelection(0)
    }
}