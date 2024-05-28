package com.example.calculadorainteresescompuestos

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var spinnerCalculationType: Spinner
    private lateinit var inputPrincipal: EditText
    private lateinit var inputRate: EditText
    private lateinit var inputTime: EditText
    private lateinit var inputAmount: EditText
    private lateinit var buttonCalculate: Button
    private lateinit var buttonClear: Button
    private lateinit var textResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerCalculationType = findViewById(R.id.spinnerCalculationType)
        inputPrincipal = findViewById(R.id.inputPrincipal)
        inputRate = findViewById(R.id.inputRate)
        inputTime = findViewById(R.id.inputTime)
        inputAmount = findViewById(R.id.inputAmount)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        buttonClear = findViewById(R.id.buttonClear)
        textResult = findViewById(R.id.textResult)

        spinnerCalculationType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                updateInputVisibility()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        buttonCalculate.setOnClickListener {
            calculateInterest()
        }

        buttonClear.setOnClickListener {
            clearInputs()
        }
    }

    private fun updateInputVisibility() {
        val calculationType = spinnerCalculationType.selectedItem.toString()

        inputPrincipal.visibility = View.VISIBLE
        inputRate.visibility = View.VISIBLE
        inputTime.visibility = View.VISIBLE
        inputAmount.visibility = View.VISIBLE

        when (calculationType) {
            "Calcular Monto de Interés (I)" -> {
                inputAmount.visibility = View.GONE
            }
            "Calcular Monto Total (A)" -> {
                inputAmount.visibility = View.GONE
            }
            "Calcular Capital Inicial (P)" -> {
                inputPrincipal.visibility = View.GONE
            }
            "Calcular Tasa de Interés (r)" -> {
                inputRate.visibility = View.GONE
            }
            "Calcular Tiempo (t)" -> {
                inputTime.visibility = View.GONE
            }
        }
    }

    private fun calculateInterest() {
        val calculationType = spinnerCalculationType.selectedItem.toString()
        val principal = inputPrincipal.text.toString().toDoubleOrNull()
        val rate = inputRate.text.toString().toDoubleOrNull()
        val time = inputTime.text.toString().toDoubleOrNull()
        val amount = inputAmount.text.toString().toDoubleOrNull()

        when (calculationType) {
            "Calcular Monto de Interés (I)" -> {
                if (principal != null && rate != null && time != null) {
                    val interest = principal * Math.pow(1 + rate / 100, time) - principal
                    textResult.text = "Monto de Interés (I): $interest"
                } else {
                    showError()
                }
            }
            "Calcular Monto Total (A)" -> {
                if (principal != null && rate != null && time != null) {
                    val totalAmount = principal * Math.pow(1 + rate / 100, time)
                    textResult.text = "Monto Total (A): $totalAmount"
                } else {
                    showError()
                }
            }
            "Calcular Capital Inicial (P)" -> {
                if (amount != null && rate != null && time != null) {
                    val initialPrincipal = amount / Math.pow(1 + rate / 100, time)
                    textResult.text = "Capital Inicial (P): $initialPrincipal"
                } else {
                    showError()
                }
            }
            "Calcular Tasa de Interés (r)" -> {
                if (principal != null && amount != null && time != null) {
                    val rateInterest = (Math.pow(amount / principal, 1 / time) - 1) * 100
                    textResult.text = "Tasa de Interés (r): $rateInterest %"
                } else {
                    showError()
                }
            }
            "Calcular Tiempo (t)" -> {
                if (principal != null && rate != null && amount != null) {
                    val timePeriod = Math.log(amount / principal) / Math.log(1 + rate / 100)
                    textResult.text = "Tiempo (t): $timePeriod"
                } else {
                    showError()
                }
            }
        }
    }

    private fun clearInputs() {
        inputPrincipal.text.clear()
        inputRate.text.clear()
        inputTime.text.clear()
        inputAmount.text.clear()
        textResult.text = "Resultado"
    }

    private fun showError() {
        Toast.makeText(this, "Por favor, ingrese todos los valores requeridos correctamente.", Toast.LENGTH_LONG).show()
    }
}
