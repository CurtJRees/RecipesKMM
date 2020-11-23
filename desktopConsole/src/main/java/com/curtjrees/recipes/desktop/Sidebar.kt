package com.curtjrees.recipes.desktop

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PartyMode
import androidx.compose.material.ripple.rememberRippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val darkBlueColor = Color(8, 13, 42)

data class SidebarItem(
    val title: String,
    val icon: ImageVector
)

@Composable
fun Sidebar(
    items: List<SidebarItem>,
    selectedIndex: Int,
    onItemClick: (SidebarItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .preferredWidthIn(min = 240.dp, max = 300.dp)
            .background(darkBlueColor)
    ) {

        Text(
            text = "RecipesKMM",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(16.dp)
        )

        ScrollableColumn(contentPadding = PaddingValues(16.dp)) {
            items.mapIndexed { index, it ->
                SidebarItemLayout(it, index == selectedIndex, onClick = { onItemClick.invoke(it) })
                if (index != items.size -1) Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun SidebarItemLayout(item: SidebarItem, selected: Boolean, onClick: () -> Unit) {
    val cardColor = if (selected) Color.White else Color.Transparent
    val contentColor = if (selected) darkBlueColor else Color.White.copy(alpha = 0.8f)

    val shape = RoundedCornerShape(4.dp)

    Card(
        shape = shape,
        backgroundColor = cardColor,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape)
            .clickable(
                indication = rememberRippleIndication(color = contentColor),
                onClick = onClick
            ),
        content = {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(16.dp)) {
                Icon(imageVector = item.icon, tint = contentColor)
                Text(item.title, color = contentColor, modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 2.dp))
            }
        }
    )

}