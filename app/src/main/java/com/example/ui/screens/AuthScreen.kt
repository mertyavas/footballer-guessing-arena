package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.ui.components.AvatarHelper
import com.example.ui.theme.CardBorder
import com.example.ui.theme.DeepBlueBg
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.FootballPitchGreen
import com.example.ui.theme.GoldenTrophy
import com.example.ui.theme.SurfaceNavy
import com.example.ui.theme.SurfaceNavyVariant
import com.example.ui.theme.TextMuted
import com.example.util.AppLanguage
import com.example.util.Strings
import com.example.viewmodel.GameViewModel

@Composable
fun AuthScreen(
    viewModel: GameViewModel,
    onSuccess: () -> Unit
) {
    BackHandler {
        onSuccess()
    }

    val lang by viewModel.appLanguage.collectAsStateWithLifecycle()
    var selectedTab by remember { mutableIntStateOf(0) } // 0 = Kayıt Ol, 1 = Giriş Yap

    val authError by viewModel.authError.collectAsStateWithLifecycle()
    val authSuccess by viewModel.authSuccess.collectAsStateWithLifecycle()

    var usernameInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var registerPasswordInput by remember { mutableStateOf("") }
    var favoriteClubInput by remember { mutableStateOf("Galatasaray") }
    var avatarIdInput by remember { mutableIntStateOf(1) }
    var jerseyNumberInput by remember { mutableIntStateOf(10) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlueBg)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Top Language Toggle
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = { viewModel.toggleLanguage() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldenTrophy),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(34.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (lang == AppLanguage.TR) "🇹🇷 Türkçe" else "🇬🇧 English",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldenTrophy
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Language,
                                contentDescription = "Dil",
                                tint = GoldenTrophy,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }

            // Logo & Title
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(FootballPitchGreen, ElectricBlue))
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.SportsSoccer,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "FOOTBALLER GUESSING ARENA",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = Strings.get("auth_subtitle", lang),
                        color = Color(0xFFCFD8DC),
                        fontSize = 12.sp
                    )
                }
            }

            // Quick Actions: Google & Guest
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Google One-Tap Sign In
                    Button(
                        onClick = {
                            viewModel.signInWithGoogle()
                            onSuccess()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("google_signin_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "G",
                                color = Color(0xFF4285F4),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Google ile Hızlı Giriş Yap",
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Play as Guest (Misafir Girişi)
                    Button(
                        onClick = {
                            viewModel.playAsGuest()
                            onSuccess()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E293B)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("guest_signin_button")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = GoldenTrophy,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Misafir Olarak Devam Et (Kayıtsız Oyna)",
                                color = GoldenTrophy,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Divider Text
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(CardBorder))
                    Text(
                        text = "  VEYA HESAP OLUŞTUR  ",
                        color = TextMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(CardBorder))
                }
            }

            // Tabs: Kayıt Ol / Giriş Yap
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = SurfaceNavy,
                    contentColor = FootballPitchGreen,
                    indicator = {},
                    divider = {},
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, CardBorder, RoundedCornerShape(14.dp))
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = {
                            selectedTab = 0
                            viewModel.clearAuthMessages()
                        },
                        text = {
                            Text(
                                text = "Kayıt Ol",
                                fontWeight = if (selectedTab == 0) FontWeight.Black else FontWeight.Normal,
                                color = if (selectedTab == 0) FootballPitchGreen else TextMuted
                            )
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = {
                            selectedTab = 1
                            viewModel.clearAuthMessages()
                        },
                        text = {
                            Text(
                                text = "Giriş Yap",
                                fontWeight = if (selectedTab == 1) FontWeight.Black else FontWeight.Normal,
                                color = if (selectedTab == 1) FootballPitchGreen else TextMuted
                            )
                        }
                    )
                }
            }

            // Error or Success Banner
            authError?.let { errText ->
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0x33FF1744)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF1744)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("⚠️", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = errText,
                                color = Color(0xFFFF8A80),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            authSuccess?.let { successText ->
                item {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0x3300E676)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, FootballPitchGreen),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("✅", fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = successText,
                                color = FootballPitchGreen,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Form Content
            if (selectedTab == 0) {
                // REGISTRATION FORM
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Kullanıcı Adı Seç (Arkadaş eklemede kullanılır):",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            OutlinedTextField(
                                value = usernameInput,
                                onValueChange = { usernameInput = it },
                                placeholder = { Text("Örn: mert94, fatih_terim, cr7_fan") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = FootballPitchGreen
                                    )
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = FootballPitchGreen,
                                    unfocusedBorderColor = CardBorder
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text(
                                text = "Giriş Şifresi Belirle (En az 4 karakter):",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            OutlinedTextField(
                                value = registerPasswordInput,
                                onValueChange = { registerPasswordInput = it },
                                placeholder = { Text("Şifreniz") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = GoldenTrophy
                                    )
                                },
                                visualTransformation = PasswordVisualTransformation(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = GoldenTrophy,
                                    unfocusedBorderColor = CardBorder
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text(
                                text = "E-Posta (İsteğe bağlı):",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            OutlinedTextField(
                                value = emailInput,
                                onValueChange = { emailInput = it },
                                placeholder = { Text("ornek@futbol.com") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Email,
                                        contentDescription = null,
                                        tint = ElectricBlue
                                    )
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = ElectricBlue,
                                    unfocusedBorderColor = CardBorder
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text(
                                text = "Avatar Seçimi:",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                itemsIndexed(AvatarHelper.avatarEmojis.drop(1)) { idx, emoji ->
                                    val realId = idx + 1
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(42.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (avatarIdInput == realId) FootballPitchGreen.copy(alpha = 0.3f)
                                                else Color(0xFF1E293B)
                                            )
                                            .border(
                                                2.dp,
                                                if (avatarIdInput == realId) FootballPitchGreen else Color.Transparent,
                                                CircleShape
                                            )
                                            .clickable { avatarIdInput = realId }
                                    ) {
                                        Text(emoji, fontSize = 20.sp)
                                    }
                                }
                            }

                            Text(
                                text = "Favori Takımın:",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                items(AvatarHelper.popularClubs) { club ->
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                if (favoriteClubInput == club) ElectricBlue else Color(0xFF1E293B)
                                            )
                                            .clickable { favoriteClubInput = club }
                                            .padding(horizontal = 10.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = club,
                                            color = Color.White,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Button(
                                onClick = {
                                    val name = usernameInput.trim().ifBlank { "Futbolcu_${(10..99).random()}" }
                                    val pass = registerPasswordInput.trim().ifBlank { "1234" }
                                    viewModel.registerWithPassword(
                                        username = name,
                                        email = emailInput,
                                        passwordInput = pass,
                                        favoriteClub = favoriteClubInput,
                                        avatarId = avatarIdInput,
                                        jerseyNumber = jerseyNumberInput,
                                        onSuccess = onSuccess
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = FootballPitchGreen),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                            ) {
                                Text(
                                    text = "Şifreli Profilimi Oluştur ve Başla",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            } else {
                // LOGIN FORM
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "Kullanıcı Adı veya E-Posta:",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            OutlinedTextField(
                                value = usernameInput,
                                onValueChange = { usernameInput = it },
                                placeholder = { Text("Kullanıcı adınız") },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = FootballPitchGreen,
                                    unfocusedBorderColor = CardBorder
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Text(
                                text = "Şifre:",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            OutlinedTextField(
                                value = passwordInput,
                                onValueChange = { passwordInput = it },
                                placeholder = { Text("••••••••") },
                                visualTransformation = PasswordVisualTransformation(),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Lock,
                                        contentDescription = null,
                                        tint = GoldenTrophy
                                    )
                                },
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedTextColor = Color.White,
                                    unfocusedTextColor = Color.White,
                                    focusedBorderColor = FootballPitchGreen,
                                    unfocusedBorderColor = CardBorder
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Button(
                                onClick = {
                                    viewModel.loginWithPassword(
                                        usernameOrEmail = usernameInput.trim(),
                                        passwordInput = passwordInput.trim(),
                                        onSuccess = onSuccess
                                    )
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = FootballPitchGreen),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                            ) {
                                Text(
                                    text = "Şifreyle Giriş Yap",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}
