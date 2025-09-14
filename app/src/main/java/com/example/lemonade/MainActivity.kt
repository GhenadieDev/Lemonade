package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			LemonadeTheme {
				LemonApp()
			}
		}
	}
}

@Composable
fun AppContent(innerPadding: PaddingValues, modifier: Modifier = Modifier) {

	var imageIndex by remember { mutableIntStateOf(0) }
	var lemonTapCount by remember { mutableIntStateOf(0) }
	var randomTaps by remember { mutableIntStateOf((2..4).random()) }

	var imageContent by remember { mutableStateOf( object{
		var image = R.drawable.lemon_tree
		var description = R.string.lemon_tree
		var contentDescription = R.string.content_lemon_tree
	}) }

	when(imageIndex) {
		0 -> {
			imageContent.image = R.drawable.lemon_tree
			imageContent.contentDescription = R.string.content_lemon_tree
			imageContent.description = R.string.lemon_tree
		}
		1 -> {
			imageContent.image = R.drawable.lemon_squeeze
			imageContent.contentDescription = R.string.content_lemon
			imageContent.description = R.string.lemon_squeeze
		}
		2 -> {
			imageContent.image = R.drawable.lemon_drink
			imageContent.contentDescription = R.string.content_glass_of_lemonade
			imageContent.description = R.string.lemon_drink
		}
		else -> {
			imageContent.image = R.drawable.lemon_restart
			imageContent.contentDescription = R.string.content_empty_glass
			imageContent.description = R.string.lemon_restart
		}
	}

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = modifier
			.padding(innerPadding)
			.fillMaxSize()
	) {
		Image(
			painter = painterResource(imageContent.image),
			contentDescription = stringResource(imageContent.contentDescription),
			modifier = modifier
				.clip(RoundedCornerShape(35.dp))
				.background(color = Color.Magenta)
				.padding(top = 8.dp, end = 20.dp, bottom = 8.dp, start = 20.dp)
				.clickable {
					if(imageIndex < 3) {
						if(imageIndex == 1) {
							lemonTapCount++
							if(lemonTapCount == randomTaps) {
								imageIndex++
								lemonTapCount = 0
								randomTaps = (2..4).random()
							} else imageIndex = 1
						} else imageIndex++
					} else {
						imageIndex = 0
					}
				}
		)
		Spacer(modifier = modifier.height(24.dp))
		Text(stringResource(imageContent.description))
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonApp(modifier: Modifier = Modifier) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				colors = topAppBarColors(
					containerColor = Color.Yellow
				),
				title = {
					Text(
						stringResource(R.string.app_name),
						fontWeight = FontWeight.Bold
					)
				}
			)
		}
	) { innerPadding ->
		AppContent(innerPadding, modifier)
	}
}

@Preview(showBackground = true)
@Composable
fun LemonadePreview() {
	LemonadeTheme {
		LemonApp()
	}
}