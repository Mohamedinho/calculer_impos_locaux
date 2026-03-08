package com.example.empos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.empos.ui.theme.EmposTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmposTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    TaxCalculatorScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaxCalculatorScreen() {
    var nom by remember { mutableStateOf("") }
    var adresse by remember { mutableStateOf("") }
    var surface by remember { mutableStateOf("") }
    var pieces by remember { mutableStateOf("") }
    var hasPiscine by remember { mutableStateOf(false) }
    
    var resultBase by remember { mutableStateOf("0.0") }
    var resultSupp by remember { mutableStateOf("0.0") }
    var resultTotal by remember { mutableStateOf("0.0") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Calcul des impôts locaux",
            fontSize = 22.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Champs de saisie
        LabelAndInput("Nom", nom) { nom = it }
        LabelAndInput("Adresse", adresse) { adresse = it }
        LabelAndInput("Surface", surface, KeyboardType.Number) { surface = it }
        LabelAndInput("Nombre de pièces", pieces, KeyboardType.Number) { pieces = it }

        Spacer(modifier = Modifier.height(16.dp))

        // Checkbox et Bouton sur la même ligne
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 32.dp)
            ) {
                Checkbox(
                    checked = hasPiscine,
                    onCheckedChange = { hasPiscine = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF4CAF50))
                )
                Text("Piscine", fontSize = 18.sp)
            }

            Button(
                onClick = {
                    val s = surface.toDoubleOrNull() ?: 0.0
                    val p = pieces.toIntOrNull() ?: 0
                    
                    val b = s * 2.0
                    val sup = (p * 50.0) + (if (hasPiscine) 100.0 else 0.0)
                    
                    resultBase = b.toString()
                    resultSupp = sup.toString()
                    resultTotal = (b + sup).toString()
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE0E0E0),
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                modifier = Modifier.height(50.dp).width(100.dp)
            ) {
                Text("Calcul", fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Affichage des résultats
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Impôt de base : $resultBase", fontSize = 18.sp)
            Text(text = "impôt supplémentaire : $resultSupp", fontSize = 18.sp)
            Text(text = "impôt Total : $resultTotal", fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelAndInput(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 16.sp, color = Color.DarkGray)
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFFFF9800), // Orange pour correspondre à l'image
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color(0xFFF0F0F0),
                unfocusedContainerColor = Color(0xFFE0E0E0)
            ),
            singleLine = true
        )
    }
}
