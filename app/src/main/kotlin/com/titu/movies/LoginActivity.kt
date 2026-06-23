package com.titu.movies

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.cos
import kotlin.math.sin

class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            CinemaAuthScreen(
                auth = auth,
                onAuthSuccess = {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// FIXED TYPO: Colon (:) instead of equals (=) for the parameter definition
fun CinemaAuthScreen(auth: FirebaseAuth, onAuthSuccess: () -> Unit) { 
    val context = LocalContext.current
    var isLoginMode by remember { mutableStateOf(true) }

    // Form States
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // Cinematic Color Schemes
    val obsidianBlack = Color(0xFF040407)
    val goldBright = Color(0xFFFFD700)
    val goldDark = Color(0xFF996515)
    val luxuryPurple = Color(0xFF8A2BE2)
    val glassBorderColor = Color(0x33FFD700)

    // Dynamic 3D Animation Vectors
    val infiniteTransition = rememberInfiniteTransition(label = "AuthEngine")
    val driftTime by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 2f * Math.PI.toFloat(),
        animationSpec = infiniteRepeatable(tween(8000, easing = LinearEasing), RepeatMode.Restart),
        label = "Drift"
    )
    val laserSweep by infiniteTransition.animateFloat(
        initialValue = -1.5f, targetValue = 2.5f,
        animationSpec = infiniteRepeatable(tween(3500, easing = EaseInOutSine), RepeatMode.Restart),
        label = "Laser"
    )

    val tiltX = sin(driftTime) * 6f
    val tiltY = cos(driftTime * 1.5f) * 4f
    val density = LocalDensity.current.density

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(obsidianBlack),
        contentAlignment = Alignment.Center
    ) {
        // BACKGROUND LAYER: Ambient Lighting Environment
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x1F8A2BE2), Color(0x0A00E5FF), Color(0x00000000)),
                    center = Offset(size.width * 0.2f, size.height * 0.2f),
                    radius = size.width * 0.8f
                ),
                radius = size.width * 0.8f,
                center = Offset(size.width * 0.2f, size.height * 0.2f)
            )
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0x16FFD700), Color(0x00000000)),
                    center = Offset(size.width * 0.8f, size.height * 0.8f),
                    radius = size.width * 0.7f
                ),
                radius = size.width * 0.7f,
                center = Offset(size.width * 0.8f, size.height * 0.8f)
            )
        }

        // 3D GLASSMORPHIC AUTH CONTAINER CARD
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .graphicsLayer {
                    cameraDistance = 16f * density
                    rotationX = tiltY
                    rotationY = tiltX
                }
                .clip(RoundedCornerShape(28.dp))
                .background(Brush.verticalGradient(listOf(Color(0x1A101016), Color(0x05030305))))
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        listOf(glassBorderColor, Color.Transparent, luxuryPurple, glassBorderColor)
                    ),
                    shape = RoundedCornerShape(28.dp)
                )
                .padding(28.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Header Laser Text
                val titleGradient = Brush.linearGradient(
                    colors = listOf(Color.White, goldBright, Color.White),
                    start = Offset(laserSweep * 300f, 0f),
                    end = Offset((laserSweep + 1f) * 300f, 0f)
                )

                Text(
                    text = if (isLoginMode) "WELCOME BACK" else "CREATE ACCOUNT",
                    style = androidx.compose.ui.text.TextStyle(
                        brush = titleGradient,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 3.sp,
                        fontFamily = CustomPoppins
                    )
                )

                Text(
                    text = if (isLoginMode) "Login to stream endless movies" else "Join the ultimate cinema experience",
                    color = Color(0x99FFFFFF),
                    fontSize = 12.sp,
                    fontFamily = CustomPoppins,
                    modifier = Modifier.padding(top = 4.dp, bottom = 28.dp),
                    textAlign = TextAlign.Center
                )

                // Input Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address", fontFamily = CustomPoppins) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = goldBright) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = goldBright,
                        unfocusedBorderColor = Color(0x33FFFFFF),
                        focusedLabelColor = goldBright,
                        unfocusedLabelColor = Color(0x66FFFFFF),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Input Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", fontFamily = CustomPoppins) },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = goldBright) },
                    trailingIcon = {
                        // FIXED: Replaced missing icon package with a clean text toggle
                        Text(
                            text = if (isPasswordVisible) "HIDE" else "SHOW",
                            color = goldBright,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = CustomPoppins,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable { isPasswordVisible = !isPasswordVisible }
                        )
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = goldBright,
                        unfocusedBorderColor = Color(0x33FFFFFF),
                        focusedLabelColor = goldBright,
                        unfocusedLabelColor = Color(0x66FFFFFF),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Action Premium Button
                Button(
                    onClick = {
                        if (email.isEmpty() || password.isEmpty()) {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        isLoading = true
                        if (isLoginMode) {
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    isLoading = false
                                    if (task.isSuccessful) {
                                        onAuthSuccess()
                                    } else {
                                        Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                        } else {
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    isLoading = false
                                    if (task.isSuccessful) {
                                        Toast.makeText(context, "Registration Successful!", Toast.LENGTH_SHORT).show()
                                        onAuthSuccess()
                                    } else {
                                        Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                    }
                                }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.linearGradient(listOf(goldDark, goldBright, goldDark))),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = obsidianBlack, modifier = Modifier.size(24.dp))
                        } else {
                            Text(
                                text = if (isLoginMode) "LOGIN TO CINEMA" else "REGISTER NOW",
                                color = obsidianBlack,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                fontFamily = CustomPoppins
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Toggle Between Login & Register Mode
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = if (isLoginMode) "New to Titu Movies? " else "Already have an account? ",
                        color = Color(0x66FFFFFF),
                        fontSize = 13.sp,
                        fontFamily = CustomPoppins
                    )
                    Text(
                        text = if (isLoginMode) "Register" else "Login",
                        color = goldBright,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = CustomPoppins,
                        modifier = Modifier.clickable { 
                            isLoginMode = !isLoginMode
                            email = ""
                            password = ""
                        }
                    )
                }
            }
        }
    }
}
