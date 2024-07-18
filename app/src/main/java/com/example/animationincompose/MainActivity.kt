package com.example.animationincompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import com.example.animationincompose.ui.theme.AnimationInComposeTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimationInComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   Clock()
                }

            }
        }
    }
}
@Composable
fun Clock(){
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val seconds by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing)
        ), label = ""
    )

    val minutes by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(3600000, easing = LinearEasing)
        ), label = ""
    )

    val hours by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(43200000, easing = LinearEasing)
        ), label = ""
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.size(300.dp)) {
            drawClockFace()
            drawHand(hours, length = size.minDimension / 4, color = Color.Black, thickness = 8f)
            drawHand(minutes, length = size.minDimension / 3, color = Color.Gray, thickness = 6f)
            drawHand(seconds, length = size.minDimension / 2.5f, color = Color.Red, thickness = 4f)
        }
    }
}
fun DrawScope.drawClockFace() {
    drawCircle(color = Color.LightGray, radius = size.minDimension / 2)
    drawCircle(color = Color.White, radius = size.minDimension / 2 - 4.dp.toPx(), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 4.dp.toPx()))

    for (i in 0..11) {
        val angle = i * 30 * (PI / 180).toFloat()
        val lineLength = if (i % 3 == 0) 20.dp.toPx() else 10.dp.toPx()
        val start = Offset(
            x = size.center.x + cos(angle) * (size.minDimension / 2 - lineLength),
            y = size.center.y + sin(angle) * (size.minDimension / 2 - lineLength)
        )
        val end = Offset(
            x = size.center.x + cos(angle) * (size.minDimension / 2 - 4.dp.toPx()),
            y = size.center.y + sin(angle) * (size.minDimension / 2 - 4.dp.toPx())
        )
        drawLine(Color.Black, start, end, strokeWidth = 4.dp.toPx(), cap = StrokeCap.Round)
    }
}

fun DrawScope.drawHand(angle: Float, length: Float, color: Color, thickness: Float) {
    rotate(degrees = angle) {
        drawLine(
            color = color,
            start = size.center,
            end = Offset(
                x = size.center.x,
                y = size.center.y - length
            ),
            strokeWidth = thickness,
            cap = StrokeCap.Round
        )
    }
}
