package com.example.hallisanthedigital.ui.screens
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hallisanthedigital.navigation.Screen
import com.example.hallisanthedigital.utils.SessionManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material.icons.automirrored.filled.ExitToApp

@Composable
fun ProfileScreen(

    navController: NavController


) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF0F172A),   // dark navy
                        Color(0xFF1E3A8A),   // blue
                        Color(0xFF2563EB)    // lighter blue
                    )
                )
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 50.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                ),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            //-----------------------------------
            // Profile Image Section
            //-----------------------------------
            Card(
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFDFF5E1)
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = Color(0xFF1E3A8A),
                    modifier = Modifier
                        .size(140.dp)
                        .padding(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "My Profile",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(25.dp))

            //-----------------------------------
            // Profile Details Card
            //-----------------------------------
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(
                   containerColor =Color(0xFFDFF5E1)
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    ProfileItem(
                        icon = Icons.Default.Person,
                        title = "Username",
                        value = SessionManager.getUsername(context)
                    )

                    ProfileItem(
                        icon = Icons.Default.Phone,
                        title = "Mobile",
                        value = SessionManager.getMobile(context)
                    )

                    ProfileItem(
                        icon = Icons.Default.Home,
                        title = "Address",
                        value = SessionManager.getAddress(context)
                    )

                    ProfileItem(
                        icon = Icons.Default.Person,
                        title = "Role",
                        value = SessionManager.getRole(context)
                    )

                    ProfileItem(
                        icon = Icons.Default.Lock,
                        title = "Password",
                        value = "********"
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            //-----------------------------------
            // Logout Button
            //-----------------------------------
            Button(
                onClick = {

                    SessionManager.logout(context)

                    navController.navigate(
                        Screen.Login.route
                    ) {
                        popUpTo(0)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {

                Icon(
                    Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = null
                )

                Spacer(
                    modifier = Modifier.width(8.dp)
                )

                Text(
                    "Logout",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Composable
fun ProfileItem(
    icon: ImageVector,
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            icon,
            contentDescription = null,
            tint = Color(0xFF2563EB),
            modifier = Modifier.size(28.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {

            Text(
                text = title,
                color = Color.Gray
            )

            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}