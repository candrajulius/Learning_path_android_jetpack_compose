package com.candra.jetreward.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.candra.jetreward.R
import com.candra.jetreward.ui.theme.JetRewardTheme
import com.candra.jetreward.ui.theme.Shapes

@Composable
fun RewardItem(
    image: Int,
    title: String,
    requirePoint: Int,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Image(painter = painterResource(id = image), contentDescription = null,
        contentScale = ContentScale.Crop, modifier = Modifier
                .size(170.dp)
                .clip(Shapes.medium))
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(text = stringResource(id = R.string.required_point,requirePoint),
        style = MaterialTheme.typography.subtitle2,
        color = MaterialTheme.colors.secondary)
    }
}

@Composable
@Preview(showBackground = true)
fun RewardItemPreview(){
    JetRewardTheme {
        RewardItem(image = R.drawable.reward_4, title = "Jaket Hoodie Dicoding", requirePoint = 4000 )
    }
}