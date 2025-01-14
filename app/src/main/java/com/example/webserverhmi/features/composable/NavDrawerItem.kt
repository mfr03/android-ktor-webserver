package com.example.webserverhmi.features.composable


import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.webserverhmi.core.navigation.NavigationItem


@Composable
fun NavDrawerItem(
    navigationItem: NavigationItem,
    itemIndexState: Int,
    navItemIndex: Int,
    onClick: () -> Unit
)
{
    NavigationDrawerItem(
        label = { Text(navigationItem.title) },
        selected = navItemIndex == itemIndexState,
        onClick = onClick,
        icon = {
            Icon(
               imageVector = if (itemIndexState == navItemIndex) {
                   navigationItem.selectedIcon
               } else navigationItem.unselectedIcon,
                contentDescription = navigationItem.title
            )
        },
        badge = {
            navigationItem.badgeCount?.let {
                Text(navigationItem.badgeCount.toString())
            }
        }
    )
}