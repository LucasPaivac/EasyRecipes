package com.example.easyrecipes.detail.presentation.ui

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TopConcaveShape(
    private val curveHeight: Float = 150f
): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {

            // topo esquerdo
            moveTo(0f, 0f)

            // linha até o início da curva
            lineTo(0f, curveHeight)

            // curva suave (para cima)
            quadraticTo(
                size.width / 2f,
                curveHeight * 2f,
                size.width,
                curveHeight
            )

            // resto do container
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        return Outline.Generic(path)
    }

}

fun bottomCurvePath(size: Size, curveHeight: Float): Path {
    return Path().apply {
        moveTo(0f, curveHeight)
        quadraticBezierTo(
            size.width / 2f,
            curveHeight * 2f,
            size.width,
            curveHeight
        )
    }
}