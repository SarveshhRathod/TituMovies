package com.titu.movies

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

// Explicitly import R to resolve resources from the core package
import com.titu.movies.R

// Firebase Auth Import kiya gaya hai Auto-Login check ke liye
import com.google.firebase.auth.FirebaseAuth

// Font setup (Corrected syntax for Compose Font loader)
val CustomPoppins = FontFamily(
    Font(resId = R.font.poppins_regular, weight = FontWeight.Normal),
    Font(resId = R.font.poppins_medium, weight = FontWeight.Medium),
    Font(resId = R.font.poppins_bold, weight = FontWeight.Bold),
    Font(resId = R.font.poppins_black, weight = FontWeight.Black)
)

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TituMoviesAwesomeSplash(onTimeout = {
                // --- FIREBASE AUTO-LOGIN LOGIC ---
                val currentUser = FirebaseAuth.getInstance().currentUser
                
                if (currentUser != null) {
                    // Agar user pehle se login hai -> Direct Home Screen
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    // Agar user naya hai ya login nahi hai -> Login Screen
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                }
                finish()
            })
        }
    }
}

// Gorgeous High-Fidelity Floating Bokeh System
data class AwesomeParticle(
    val id: Int,
    val initialX: Float,
    val initialY: Float,
    val driftAmplitude: Float,
    val size: Float,
    val speed: Float,
    val color: Color
)

@Composable
fun TituMoviesAwesomeSplash(onTimeout: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(4500) // Premium entrance, hold & transition (4.5s)
        onTimeout()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "AwesomeEngine")

    // Realistic Floating Physics (Using Lissajous Wave curves for smooth drift)
    val driftTime by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "DriftTime"
    )

    // Master camera dolly (slow zoom-in)
    val cameraDolly by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(4500, easing = EaseInOutQuart),
            repeatMode = RepeatMode.Reverse
        ), label = "Dolly"
    )

    // Continuous premium gold laser shimmer sweep
    val laserSweep by infiniteTransition.animateFloat(
        initialValue = -1.5f,
        targetValue = 2.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(3200, easing = CubicBezierEasing(0.25f, 1.0f, 0.5f, 1.0f)),
            repeatMode = RepeatMode.Restart
        ), label = "LaserSweep"
    )

    // Projector lens shutter rotation
    val shutterRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(22000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "ShutterRotation"
    )

    // Cosmic glow heartbeat
    val ambientPulse by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ), label = "Pulse"
    )

    // Intro Animations
    var renderTrigger by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { renderTrigger = true }

    val logoAlpha by animateFloatAsState(
        targetValue = if (renderTrigger) 1f else 0f,
        animationSpec = tween(1600, easing = EaseInOutQuart), label = "Alpha"
    )
    val logoScale by animateFloatAsState(
        targetValue = if (renderTrigger) 1f else 0.8f,
        animationSpec = tween(1800, easing = EaseOutBack), label = "Scale"
    )
    val logoBlur by animateFloatAsState(
        targetValue = if (renderTrigger) 0f else 28f,
        animationSpec = tween(1500, easing = EaseOutCubic), label = "Blur"
    )

    // Dynamic 3D tilt coordinates derived from waves
    val tiltX = sin(driftTime) * 9f
    val tiltY = cos(driftTime * 1.5f) * 6f

    // 40 Unique high-fidelity bokeh particles
    val particles = remember {
        List(40) { index ->
            AwesomeParticle(
                id = index,
                initialX = (0.05f + index * 0.024f) % 1.0f,
                initialY = (0.1f + index * 0.031f) % 1.0f,
                driftAmplitude = 15f + (index % 4) * 10f,
                size = 3f + (index % 5) * 2.5f,
                speed = 0.4f + (index % 3) * 0.2f,
                color = when (index % 4) {
                    0 -> Color(0x22FFB300) // Deep Amber
                    1 -> Color(0x1F8A2BE2) // Velvet Violet
                    2 -> Color(0x2600E5FF) // Light Cyan
                    else -> Color(0x11FFFFFF) // Pure Platinum
                }
            )
        }
    }

    // Color definitions for "Cinema Prestige" Theme
    val obsidianBlack = Color(0xFF040407)
    val goldBright = Color(0xFFFFD700)
    val goldDark = Color(0xFF996515)
    val luxuryPurple = Color(0xFF8A2BE2)
    val glassBorderColor = Color(0x33FFD700)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(obsidianBlack),
        contentAlignment = Alignment.Center
    ) {
        // LAYER I: Ambient Projector & Particle Universe
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(scaleX = cameraDolly, scaleY = cameraDolly)
        ) {
            val width = size.width
            val height = size.height
            val center = Offset(width / 2f, height / 2f)

            // 1. Cosmic Background Radial Glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x1D8A2BE2), Color(0x0A00E5FF), Color(0x00000000)),
                    center = center,
                    radius = width * 0.9f
                ),
                radius = width * 0.9f,
                center = center
            )

            // 2. Cinematic Projector Cone (Diagonal Light Beam Simulation)
            val beamPath = Path().apply {
                moveTo(-100f, -100f)
                lineTo(width * 0.8f, height + 100f)
                lineTo(width * 1.2f, height + 100f)
                lineTo(200f, -100f)
                close()
            }
            drawPath(
                path = beamPath,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0x00FFFFFF),
                        Color(0x0BFFD700),
                        Color(0x028A2BE2),
                        Color(0x00000000)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(width * 0.7f, height)
                )
            )

            // 3. High Precision Rotating Calibration Ring
            rotate(shutterRotation * 0.4f) {
                drawCircle(
                    color = Color(0x0FFFFFDF00),
                    radius = width * 0.38f,
                    center = center,
                    style = Stroke(
                        width = 1f,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(30f, 70f), 0f)
                    )
                )
            }

            // 4. Floating Bokeh rendering with wave motion
            particles.forEach { particle ->
                val calculatedX = (particle.initialX * width + sin(driftTime + particle.id) * particle.driftAmplitude) % width
                val calculatedY = (particle.initialY * height - (shutterRotation * particle.speed) % height + height) % height

                drawCircle(
                    color = particle.color,
                    radius = particle.size,
                    center = Offset(calculatedX, calculatedY)
                )
            }
        }

        // LAYER II: Backing Shadow Layer (For absolute 3D Stereoscopic Separation)
        val density = LocalDensity.current.density
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(0.9f)
                .height(380.dp)
                .graphicsLayer {
                    cameraDistance = 14f * density
                    rotationX = tiltY * 1.3f // Over-rotated for offset illusion
                    rotationY = tiltX * 1.3f
                    translationY = 20f
                    translationX = 10f
                    scaleX = logoScale * 0.98f
                    scaleY = logoScale * 0.98f
                    alpha = logoAlpha * 0.4f
                }
                .blur(20.dp)
                .background(Color(0xFF000000), shape = RoundedCornerShape(28.dp))
        )

        // LAYER III: Main 3D Glassmorphic Container Card
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(0.9f)
                .height(380.dp)
                .graphicsLayer {
                    cameraDistance = 14f * density
                    rotationX = tiltY
                    rotationY = tiltX
                    scaleX = logoScale
                    scaleY = logoScale
                    alpha = logoAlpha
                }
                .blur(logoBlur.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0x18101016), Color(0x03030305))
                    )
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            glassBorderColor,
                            Color(0x00000000),
                            Color(0x228A2BE2),
                            glassBorderColor
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1f, 1f)
                    ),
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Stunning Custom Canvas Camera Shutter & Play Button
                Canvas(
                    modifier = Modifier
                        .size(130.dp)
                        .graphicsLayer {
                            cameraDistance = 10f * density
                            rotationX = -tiltY * 0.6f
                            rotationY = tiltX * 0.6f
                        }
                ) {
                    val sizePx = size.width
                    val center = Offset(sizePx / 2f, sizePx / 2f)
                    val r = sizePx * 0.45f

                    // 1. Interactive 3D Outer Glowing Blade Rings
                    rotate(-shutterRotation) {
                        for (i in 0 until 6) {
                            val angleRad = Math.toRadians((i * 60).toDouble()).toFloat()
                            val bladeStart = Offset(
                                center.x + cos(angleRad) * r * 0.7f,
                                center.y + sin(angleRad) * r * 0.7f
                            )
                            val bladeEnd = Offset(
                                center.x + cos(angleRad + 0.6f) * r,
                                center.y + sin(angleRad + 0.6f) * r
                            )
                            drawLine(
                                brush = Brush.linearGradient(
                                    colors = listOf(goldBright, Color.Transparent)
                                ),
                                start = bladeStart,
                                end = bladeEnd,
                                strokeWidth = 3f,
                                cap = StrokeCap.Round
                            )
                        }
                    }

                    // 2. Beautiful Central Play Emblem
                    val path = Path().apply {
                        moveTo(sizePx * 0.38f, sizePx * 0.32f)
                        lineTo(sizePx * 0.72f, sizePx * 0.50f)
                        lineTo(sizePx * 0.38f, sizePx * 0.68f)
                        close()
                    }

                    // Dynamic Metallic-Gold Reflection Gradient
                    val dynamicGoldBrush = Brush.linearGradient(
                        colors = listOf(goldDark, goldBright, goldDark),
                        start = Offset(laserSweep * sizePx, 0f),
                        end = Offset((laserSweep + 0.7f) * sizePx, sizePx)
                    )

                    withTransform({
                        translate(left = 6f, top = 8f)
                    }) {
                        drawPath(path = path, color = Color(0x99000000))
                    }

                    drawPath(path = path, brush = dynamicGoldBrush)

                    // Sharp neon edge highlight
                    drawPath(
                        path = path,
                        color = Color(0xFFFFE082),
                        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                    )

                    // Central glowing velvet core
                    drawCircle(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0x3D8A2BE2), Color.Transparent),
                            center = center,
                            radius = sizePx * 0.25f
                        ),
                        radius = sizePx * 0.25f,
                        center = center
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // Primary Brand Text: TITU MOVIES (with Poppins.Black and Gold-Laser-Sweep)
                val textGradient = Brush.linearGradient(
                    colors = listOf(Color.White, goldBright, Color.White),
                    start = Offset(laserSweep * 400f, 0f),
                    end = Offset((laserSweep + 1f) * 400f, 0f)
                )

                Text(
                    text = "TITU MOVIES",
                    style = androidx.compose.ui.text.TextStyle(
                        brush = textGradient,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 6.sp,
                        fontFamily = CustomPoppins,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.graphicsLayer {
                        translationX = tiltX * 1.1f
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Elegant Subtitle: गाव पासून शहरापर्यंत (with Poppins.Medium and Ambient Light-Pulse)
                Text(
                    text = "गाव पासून शहरापर्यंत",
                    color = Color(0xCCFFD700),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 4.sp,
                    fontFamily = CustomPoppins,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.graphicsLayer {
                        translationX = tiltX * 0.5f
                        alpha = ambientPulse
                    }
                )
            }
        }

        // LAYER IV: Bottom Circular Progress Loader
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Segmented Heartbeat dots with gold and luxury-purple colors
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) { index ->
                        val activeScale = if (index == 1) ambientPulse else 0.5f
                        val dotColor = when (index) {
                            0 -> goldBright
                            1 -> luxuryPurple
                            else -> goldDark
                        }
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .graphicsLayer {
                                    scaleX = activeScale + 0.5f
                                    scaleY = activeScale + 0.5f
                                    alpha = activeScale
                                }
                                .background(dotColor, shape = RoundedCornerShape(50))
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "CINEMATIC EXPERIENCE",
                    color = Color(0x4DFFFFFF),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = CustomPoppins,
                    letterSpacing = 3.sp
                )
            }
        }
    }
}
