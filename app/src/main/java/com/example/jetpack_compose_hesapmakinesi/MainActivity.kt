package com.example.jetpack_compose_hesapmakinesi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpack_compose_hesapmakinesi.ui.theme.Jetpack_Compose_HesapMakinesiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Jetpack_Compose_HesapMakinesiTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorApp(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CalculatorApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Sayı girdileri ve sonuç için State tanımlamaları
    var firstNumber by remember { mutableStateOf("") }
    var secondNumber by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("Result = ") }

    // Matematiksel işlemleri yapan ortak fonksiyon
    val calculate = { operation: String ->
        val num1 = firstNumber.toDoubleOrNull()
        val num2 = secondNumber.toDoubleOrNull()

        if (num1 != null && num2 != null) {
            when (operation) {
                "+" -> resultText = "Result = ${num1 + num2}"
                "-" -> resultText = "Result = ${num1 - num2}"
                "*" -> resultText = "Result = ${num1 * num2}"
                "/" -> {
                    if (num2 != 0.0) {
                        resultText = "Result = ${num1 / num2}"
                    } else {
                        resultText = "Result = Sıfıra bölünemez!"
                        Toast.makeText(context, "Hata: Sıfıra bölme!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            resultText = "Result = Lütfen geçerli sayılar girin!"
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 1. Sayı Giriş Alanı
        OutlinedTextField(
            value = firstNumber,
            onValueChange = { firstNumber = it },
            label = { Text("First Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Sayı Giriş Alanı
        OutlinedTextField(
            value = secondNumber,
            onValueChange = { secondNumber = it },
            label = { Text("Second Number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // İşlem Butonlarının Yan Yana Dizildiği Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val buttonColor = ButtonDefaults.buttonColors(containerColor = Color(0xFF6750A4))

            Button(onClick = { calculate("+") }, colors = buttonColor) {
                Text("+", fontSize = 18.sp, color = Color.White)
            }

            Button(onClick = { calculate("-") }, colors = buttonColor) {
                Text("-", fontSize = 18.sp, color = Color.White)
            }

            Button(onClick = { calculate("*") }, colors = buttonColor) {
                Text("*", fontSize = 18.sp, color = Color.White)
            }

            Button(onClick = { calculate("/") }, colors = buttonColor) {
                Text("/", fontSize = 18.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Sonucun Gösterileceği Text Alanı
        Text(
            text = resultText,
            fontSize = 20.sp,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    Jetpack_Compose_HesapMakinesiTheme {
        CalculatorApp()
    }
}