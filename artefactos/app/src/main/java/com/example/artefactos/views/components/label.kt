package com.example.artefactos.views.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun H1Bold(label: String, color: Color) {
    Text(text = label, color = color, style = MaterialTheme.typography.headlineLarge)
}

@Composable
fun H1WithoutBold(label: String, color: Color, textAlign: TextAlign? = null) {
    Text(
        text = label,
        color = color,
        style = MaterialTheme.typography.headlineMedium,
        textAlign = textAlign
    )
}

@Composable
fun H2(label: String, color: Color) {
    Text(text = label, color = color, style = MaterialTheme.typography.headlineSmall)
}

@Composable
fun NormalLabel(label: String, color: Color, textAlign: TextAlign? = null) {
    Text(
        text = label,
        color = color,
        style = MaterialTheme.typography.labelMedium,
        textAlign = textAlign
    )
}
