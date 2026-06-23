package com.titu.movies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- COLOR PALETTE (Matched from the Video) ---
val AppBackground = Color(0xFF111111)
val SurfaceDark = Color(0xFF1C1C1E)
val SurfaceLighter = Color(0xFF2C2C2E)
val PrimaryGreen = Color(0xFF00E676)
val PrimaryRed = Color(0xFFE50914)
val TextGray = Color(0xFFAAAAAA)
val GoldIcon = Color(0xFFFFD700)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TituMoviesHome()
        }
    }
}

@Composable
fun TituMoviesHome() {
    Scaffold(
        bottomBar = { CustomBottomNavigation() },
        containerColor = AppBackground
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppBackground)
        ) {
            // 1. Custom Top Search Bar
            item { TopSearchBar() }
            
            // 2. Top Navigation Tabs
            item { TopNavigationTabs() }
            
            // 3. Hero Banner (Carousel)
            item { HeroBannerSection() }
            
            // 4. FIFA World Cup Live Section
            item { WorldCupSection() }
            
            // 5. Category Filter Pills
            item { CategoryPillsSection() }
            
            // 6. Rankings Row
            item { RankingsSection() }
            
            // 7. Cinema Row (Fixed Icon)
            item { StandardMovieRow(title = "Cinema", icon = Icons.Default.List) }
            
            // 8. Hot Short TV Row (Fixed Icon)
            item { StandardMovieRow(title = "Hot Short TV", icon = Icons.Default.Star, isPortrait = false) }
            
            // 9. Must Watch Row
            item { StandardMovieRow(title = "2025 Must-Watch", icon = Icons.Default.Star) }
            
            // 10. WWE Section (Fixed Icon)
            item { StandardMovieRow(title = "Netflix WWE Live & Replay", icon = Icons.Default.PlayArrow) }
            
            // 11. Bollywood Section (Fixed Icon)
            item { StandardMovieRow(title = "Bollywood", icon = Icons.Default.PlayArrow) }
            
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

// ==========================================
// 1. TOP SEARCH BAR
// ==========================================
@Composable
fun TopSearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // App Logo Icon
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Brush.linearGradient(listOf(Color(0xFF00B4DB), Color(0xFF0083B0)))),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Logo", tint = Color.White, modifier = Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Search Input Box
        var searchText by remember { mutableStateOf("Jolly LLB 3") }
        Row(
            modifier = Modifier
                .weight(1f)
                .height(38.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(SurfaceDark),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(12.dp))
            Icon(Icons.Default.Search, contentDescription = "Search", tint = TextGray, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = searchText,
                onValueChange = { searchText = it },
                textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            // Red Search Button
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(2.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(PrimaryRed)
                    .clickable {  }
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Search", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Upload/Arrow Icon (Fixed Icon)
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(SurfaceDark)
                .border(1.dp, PrimaryRed, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Upload", tint = PrimaryRed, modifier = Modifier.size(20.dp))
        }
    }
}

// ==========================================
// 2. TOP NAVIGATION TABS
// ==========================================
@Composable
fun TopNavigationTabs() {
    val tabs = listOf("Trending", "World Cup", "Movie", "TV", "Anime", "ShortTV", "Kids", "Education")
    var selectedTabIndex by remember { mutableStateOf(1) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tabs.size) { index ->
            val isSelected = index == selectedTabIndex
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { selectedTabIndex = index }
            ) {
                Text(
                    text = tabs[index],
                    color = if (isSelected) PrimaryGreen else Color.White,
                    fontSize = 15.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
                if (isSelected) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(20.dp)
                            .height(3.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(PrimaryGreen)
                    )
                }
            }
        }
    }
}

// ==========================================
// 3. HERO BANNER
// ==========================================
@Composable
fun HeroBannerSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(listOf(Color(0xFF2C3E50), Color(0xFF000000))))
    ) {
        // Overlay Gradients & Content
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xCC000000)))),
            contentAlignment = Alignment.BottomStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Michael [Hindi]", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Fixed Icon
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = TextGray, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("2026 | Biography", color = TextGray, fontSize = 12.sp)
                    }
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(PrimaryGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.Black, modifier = Modifier.size(28.dp))
                }
            }
        }
    }
}

// ==========================================
// 4. FIFA WORLD CUP SECTION
// ==========================================
@Composable
fun WorldCupSection() {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        SectionHeader(title = "FIFA World Cup", actionText = "All >")
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(4) { index ->
                WorldCupMatchCard(isLive = index == 0)
            }
        }
    }
}

@Composable
fun WorldCupMatchCard(isLive: Boolean) {
    Box(
        modifier = Modifier
            .width(180.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceDark)
            .border(1.dp, if (isLive) PrimaryRed.copy(alpha = 0.5f) else Color.Transparent, RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (isLive) {
                    // Fixed: Using Shape instead of missing FiberManualRecord icon
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(PrimaryRed)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("LIVE", color = PrimaryRed, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                } else {
                    Text("Upcoming • 03:04:28", color = TextGray, fontSize = 10.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TeamLogo("AR")
                Text("VS", color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                TeamLogo("FR")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Argentina", color = Color.White, fontSize = 11.sp)
                Text("France", color = Color.White, fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun TeamLogo(name: String) {
    Box(
        modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(SurfaceLighter),
        contentAlignment = Alignment.Center
    ) {
        Text(name, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

// ==========================================
// 5. CATEGORY PILLS
// ==========================================
@Composable
fun CategoryPillsSection() {
    val categories = listOf("Wester...", "Anime", "K-Drama", "C-Dra...", "Indian...", "Turkish...")
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        SectionHeader(title = "Categories", actionText = "All >")
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories.size) { index ->
                val isSelected = index == 4 // Simulating "Indian..." selected
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isSelected) Brush.horizontalGradient(listOf(Color(0xFFD35400), Color(0xFFE67E22)))
                            else Brush.horizontalGradient(listOf(SurfaceDark, SurfaceDark))
                        )
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        categories[index],
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

// ==========================================
// 6. RANKINGS SECTION (With Numbers)
// ==========================================
@Composable
fun RankingsSection() {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        SectionHeader(title = "Rankings", actionText = "All >")
        
        // Ranking Sub-tabs
        val subTabs = listOf("Trending now", "Cinema", "Bollywood", "South Indian", "Hollywood")
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(subTabs.size) { i ->
                Text(
                    text = subTabs[i],
                    color = if (i == 0) Color.White else TextGray,
                    fontSize = 13.sp,
                    fontWeight = if (i == 0) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(5) { index ->
                RankingMovieCard(rank = index + 1)
            }
        }
    }
}

@Composable
fun RankingMovieCard(rank: Int) {
    val rankColor = when (rank) {
        1 -> Color(0xFFFFD700) // Gold
        2 -> Color(0xFFC0C0C0) // Silver
        3 -> Color(0xFFCD7F32) // Bronze
        else -> SurfaceLighter
    }

    Column(modifier = Modifier.width(110.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Brush.verticalGradient(listOf(Color(0xFF1E3C72), Color(0xFF2A5298))))
        ) {
            // Language Badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clip(RoundedCornerShape(bottomStart = 8.dp))
                    .background(Color.White)
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text("Hindi", color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
            
            // Rank Number Badge
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .clip(RoundedCornerShape(bottomEnd = 8.dp))
                    .background(rankColor)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(rank.toString(), color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Black)
            }
            
            // Fixed Icon
            Icon(
                Icons.Default.PlayArrow,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.align(Alignment.Center).size(36.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "Movie Title $rank",
            color = Color.White,
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// ==========================================
// 7. STANDARD MOVIE ROWS
// ==========================================
@Composable
fun StandardMovieRow(title: String, icon: ImageVector, isPortrait: Boolean = true) {
    Column(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = GoldIcon, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Text("All >", color = TextGray, fontSize = 12.sp)
        }
        
        Spacer(modifier = Modifier.height(10.dp))
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(6) {
                Box(
                    modifier = Modifier
                        .width(if (isPortrait) 110.dp else 160.dp)
                        .height(if (isPortrait) 160.dp else 100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Brush.linearGradient(listOf(Color(0xFF333333), Color(0xFF111111))))
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clip(RoundedCornerShape(bottomStart = 8.dp))
                            .background(Color.White)
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("Hindi", color = Color.Black, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        "Poster", 
                        color = TextGray, 
                        fontSize = 12.sp, 
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, actionText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(actionText, color = TextGray, fontSize = 12.sp)
    }
}

// ==========================================
// 8. CUSTOM BOTTOM NAVIGATION BAR
// ==========================================
@Composable
fun CustomBottomNavigation() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(65.dp)
            .background(SurfaceDark)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(icon = Icons.Default.Home, label = "Home", isSelected = true)
        
        // Fixed Icon
        BottomNavItem(icon = Icons.Default.List, label = "NoveHub", isSelected = false)
        
        // Center Floating Action "Fight Zone" Lookalike
        Box(
            modifier = Modifier
                .offset(y = (-10).dp)
                .size(56.dp)
                .clip(CircleShape)
                .background(Brush.radialGradient(listOf(Color(0xFFFF512F), Color(0xFFDD2476))))
                .border(2.dp, Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // Fixed Icon
            Icon(Icons.Default.PlayArrow, contentDescription = "Fight Zone", tint = Color.White, modifier = Modifier.size(28.dp))
        }

        // Fixed Icon
        BottomNavItem(icon = Icons.Default.KeyboardArrowDown, label = "Downloads", isSelected = false)
        BottomNavItem(icon = Icons.Default.Person, label = "Me", isSelected = false)
    }
}

@Composable
fun BottomNavItem(icon: ImageVector, label: String, isSelected: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {  }
    ) {
        Icon(
            icon, 
            contentDescription = label, 
            tint = if (isSelected) PrimaryGreen else TextGray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            label, 
            color = if (isSelected) PrimaryGreen else TextGray, 
            fontSize = 10.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
