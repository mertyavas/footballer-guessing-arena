package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Screen
import com.example.ui.components.AvatarHelper
import com.example.ui.components.PlayerAvatarView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.ui.theme.CardBorder
import com.example.ui.theme.DeepBlueBg
import com.example.ui.theme.DuelRed
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.FootballPitchGreen
import com.example.ui.theme.GoldenTrophy
import com.example.ui.theme.SurfaceNavy
import com.example.ui.theme.SurfaceNavyVariant
import com.example.ui.theme.TextMuted
import com.example.viewmodel.GameViewModel

@Composable
fun DuelLobbyScreen(
    viewModel: GameViewModel,
    onNavigate: (Screen) -> Unit,
    onNavigateBack: () -> Unit
) {
    BackHandler {
        onNavigateBack()
    }

    val profile by viewModel.profile.collectAsStateWithLifecycle()
    val roomCode by viewModel.roomCode.collectAsStateWithLifecycle()
    val joinCodeInput by viewModel.joinCodeInput.collectAsStateWithLifecycle()
    val friendsList by viewModel.friendsList.collectAsStateWithLifecycle()
    val pendingRequests by viewModel.pendingRequests.collectAsStateWithLifecycle()
    val friendAddError by viewModel.friendAddError.collectAsStateWithLifecycle()
    val friendAddSuccess by viewModel.friendAddSuccess.collectAsStateWithLifecycle()
    val registeredAccounts by viewModel.registeredAccounts.collectAsStateWithLifecycle()

    var showAddFriendDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepBlueBg)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onNavigateBack,
                        modifier = Modifier.testTag("duel_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Geri",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "ARKADAŞLA OYNA & DÜELLO",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Kullanıcı adıyla istek gönder, onayla ve online oyna!",
                            color = TextMuted,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            // USER STATUS BADGE & GUEST WARNING
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (profile.isGuest) GoldenTrophy else FootballPitchGreen),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            PlayerAvatarView(avatarId = profile.avatarId, size = 42.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = profile.username,
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(if (profile.isGuest) GoldenTrophy else FootballPitchGreen)
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = if (profile.isGuest) "MİSAFİR" else "KAYITLI",
                                            color = Color.Black,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black
                                        )
                                    }
                                }
                                Text(
                                    text = "Oyuncu Kodu: ${profile.friendCode}",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        if (profile.isGuest) {
                            Button(
                                onClick = { onNavigate(Screen.Auth) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A60)),
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text("Giriş Yap / Kaydol", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            // PASS & PLAY / YAN YANA 2 KİŞİ (Herkes kullanabilir)
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfaceNavyVariant),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldenTrophy),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.PhoneAndroid,
                                contentDescription = null,
                                tint = GoldenTrophy,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Aynı Telefonda 2 Kişi (Yan Yana Kapış)",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Kayıt olmadan da arkadaşınla telefonu aranıza alıp 15 saniyelik tahminde veya Futbol XOX'te yarışabilirsiniz.",
                            color = Color(0xFFB0BEC5),
                            fontSize = 12.sp
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onNavigate(Screen.Mode1Guess(isPassAndPlay = true, opponentName = "2. Oyuncu")) },
                                colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "2 Kişilik 15s Tahmin",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Button(
                                onClick = { onNavigate(Screen.Mode2Xox(isPassAndPlay = true)) },
                                colors = ButtonDefaults.buttonColors(containerColor = GoldenTrophy),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "2 Kişilik XOX",
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                    }
                }
            }

            // HOST & JOIN ROOM
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Host Room
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "ODA KODUN",
                                color = TextMuted,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = roomCode,
                                    color = ElectricBlue,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black
                                )
                                IconButton(onClick = { viewModel.generateNewRoomCode() }, modifier = Modifier.size(24.dp)) {
                                    Icon(Icons.Default.Refresh, contentDescription = "Yenile", tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }

                    // Join Room
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                        modifier = Modifier.weight(1f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "ODAYA KATIL",
                                color = TextMuted,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                OutlinedTextField(
                                    value = joinCodeInput,
                                    onValueChange = { viewModel.onJoinCodeChanged(it) },
                                    placeholder = { Text("FUT-...") },
                                    singleLine = true,
                                    shape = RoundedCornerShape(8.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedContainerColor = Color(0xFF0A121E),
                                        unfocusedContainerColor = Color(0xFF0A121E),
                                        focusedTextColor = Color.White,
                                        unfocusedTextColor = Color.White
                                    ),
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Button(
                                    onClick = {
                                        if (joinCodeInput.isNotBlank()) {
                                            onNavigate(Screen.Mode1Guess(isDuel = true, opponentName = "Arkadaş ($joinCodeInput)"))
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = FootballPitchGreen),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text("Gir", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            // PENDING FRIEND REQUESTS SECTION (Bekleyen İstekler)
            if (pendingRequests.isNotEmpty()) {
                item {
                    Text(
                        text = "ONAY BEKLEYEN İSTEKLER (${pendingRequests.size})",
                        color = GoldenTrophy,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                }

                items(pendingRequests) { req ->
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E2638)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldenTrophy),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            PlayerAvatarView(avatarId = req.avatarId, size = 40.dp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = req.name,
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Arkadaşlık isteği gönderdi",
                                    color = Color(0xFFCFD8DC),
                                    fontSize = 11.sp
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Button(
                                    onClick = { viewModel.acceptFriendRequest(req.id) },
                                    colors = ButtonDefaults.buttonColors(containerColor = FootballPitchGreen),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Onayla", color = Color.Black, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                                IconButton(
                                    onClick = { viewModel.rejectFriendRequest(req.id) },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Reddet", tint = DuelRed, modifier = Modifier.size(18.dp))
                                }
                            }
                        }
                    }
                }
            }

            // MUTUAL ACCEPTED FRIENDS SECTION (Gerçek Arkadaşlar Listesi)
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "ARKADAŞLARIM (${friendsList.size})",
                        color = TextMuted,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )
                    Button(
                        onClick = {
                            viewModel.clearFriendMessages()
                            showAddFriendDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A60)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null,
                            tint = FootballPitchGreen,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Kullanıcı Adıyla Ekle",
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // EMPTY STATE (Henüz onaylı arkadaş yok)
            if (friendsList.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF1E293B))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PersonAdd,
                                    contentDescription = null,
                                    tint = TextMuted,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Henüz arkadaşın yok",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Kullanıcı adını bildiğin bir kişiye istek gönder.\nKarşı taraf onayladığında burada Çevrimiçi/Çevrimdışı olarak listelenecek.",
                                color = TextMuted,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Button(
                                onClick = { showAddFriendDialog = true },
                                colors = ButtonDefaults.buttonColors(containerColor = FootballPitchGreen),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "+ Kullanıcı Adıyla İstek Gönder",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            } else {
                items(friendsList) { friend ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = SurfaceNavy),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            PlayerAvatarView(
                                avatarId = friend.avatarId,
                                size = 44.dp,
                                borderColor = if (friend.isOnline) FootballPitchGreen else Color(0xFF546E7A)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = friend.name,
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(if (friend.isOnline) FootballPitchGreen else Color(0xFF78909C))
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (friend.isOnline) "Çevrimiçi" else "Çevrimdışı",
                                        color = if (friend.isOnline) FootballPitchGreen else TextMuted,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = " • ${friend.favoriteClub}",
                                        color = TextMuted,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            // Challenge & Remove Buttons
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = {
                                        viewModel.sendChallengeToFriend(
                                            friend,
                                            Screen.Mode1Guess(isDuel = true, opponentName = friend.name)
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Timer,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text("15s", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }

                                Button(
                                    onClick = {
                                        viewModel.sendChallengeToFriend(
                                            friend,
                                            Screen.Mode2Xox(isPassAndPlay = false, opponentName = friend.name)
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = FootballPitchGreen),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.SportsSoccer,
                                        contentDescription = null,
                                        tint = Color.Black,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text("XOX", color = Color.Black, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }

                                IconButton(
                                    onClick = { viewModel.removeFriend(friend.id) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Sil",
                                        tint = Color(0xFF78909C),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }

    // SEND FRIEND REQUEST DIALOG
    if (showAddFriendDialog) {
        var friendUsernameInput by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()

        Dialog(onDismissRequest = {
            viewModel.clearFriendMessages()
            showAddFriendDialog = false
        }) {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF101726)),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, FootballPitchGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ARKADAŞLIK İSTEĞİ GÖNDER",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Yalnızca sistemde kayıtlı olan oyuncular arkadaş eklenebilir. Kullanıcı adını girerek istek gönderebilirsin.",
                        color = Color(0xFFB0BEC5),
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    // ERROR BANNER
                    if (friendAddError != null) {
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0x33FF1744)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFF1744)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("⚠️", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = friendAddError ?: "",
                                    color = Color(0xFFFF8A80),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }

                    // SUCCESS BANNER
                    if (friendAddSuccess != null) {
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0x3300E676)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, FootballPitchGreen),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("✅", fontSize = 18.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = friendAddSuccess ?: "",
                                    color = FootballPitchGreen,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = friendUsernameInput,
                        onValueChange = {
                            friendUsernameInput = it
                            if (friendAddError != null) {
                                viewModel.clearFriendMessages()
                            }
                        },
                        label = { Text("Kayıtlı Kullanıcı Adı") },
                        placeholder = { Text("Örn: @Icardi_Wanda99, @Arda_Guler_Fan") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = if (friendAddError != null) Color(0xFFFF1744) else FootballPitchGreen,
                            unfocusedBorderColor = if (friendAddError != null) Color(0xFFFF1744) else Color(0xFF334155)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // REGISTERED USERS SUGGESTIONS CHIPS
                    Text(
                        text = "Oyunda Kayıtlı Örnek Kullanıcılar (Dokunarak Seç):",
                        color = TextMuted,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    val sampleAccounts = registeredAccounts.filter {
                        !it.username.equals(profile.username, ignoreCase = true)
                    }.take(10)

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(sampleAccounts) { acc ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color(0xFF1E293B))
                                    .border(1.dp, Color(0xFF334155), RoundedCornerShape(8.dp))
                                    .clickable {
                                        friendUsernameInput = acc.username
                                        viewModel.clearFriendMessages()
                                    }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${acc.username} (${acc.favoriteClub})",
                                        color = GoldenTrophy,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                viewModel.clearFriendMessages()
                                showAddFriendDialog = false
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF263238))
                        ) {
                            Text("Kapat", color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                val target = friendUsernameInput.trim()
                                if (target.isNotBlank()) {
                                    viewModel.sendFriendRequest(target) { success ->
                                        if (success) {
                                            coroutineScope.launch {
                                                kotlinx.coroutines.delay(1200)
                                                showAddFriendDialog = false
                                            }
                                        }
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = FootballPitchGreen)
                        ) {
                            Text("İstek Gönder", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
