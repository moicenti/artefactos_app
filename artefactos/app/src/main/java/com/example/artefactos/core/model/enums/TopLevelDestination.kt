package com.example.artefactos.core.model.enums

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.artefactos.R
import com.example.artefactos.ui.theme.colorCardGreen
import com.example.artefactos.ui.theme.colorCardYellow
import com.example.artefactos.ui.theme.primaryLight
import com.example.artefactos.ui.theme.tertiaryLight

enum class TopLevelDestination(
    val labelName: String,
    val icon: Int,
    val shouldShowOption: Boolean = false,
    val color: Color,
    val onColor: Color,
    val extraHeight: Dp = 0.dp
) {
    REMOVE_FINGER(
        "Eliminar dedo",
        R.drawable.delete,
        true,
        color = colorCardGreen,
        onColor = primaryLight,
        extraHeight = 20.dp
    ),
    ADD_FINGER(
        "Añadir dedo",
        R.drawable.fingers_id,
        true,
        color = colorCardYellow,
        onColor = tertiaryLight,
        extraHeight = 40.dp
    ),
    SUCCESS("", 0, false, color = Color.White, onColor = Color.Black, extraHeight = 50.dp)
}