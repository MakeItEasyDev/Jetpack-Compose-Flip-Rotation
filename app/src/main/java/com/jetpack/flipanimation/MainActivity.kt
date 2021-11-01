package com.jetpack.flipanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jetpack.flipanimation.ui.theme.FlipAnimationTheme
import com.jetpack.flipanimation.ui.theme.ForwardColor
import com.jetpack.flipanimation.ui.theme.PreviousColor
import com.jetpack.flipanimation.utils.FlipCard

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlipAnimationTheme {
                Surface(color = MaterialTheme.colors.background) {
                    FlipAnim()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun FlipAnim() {
    var flipCard by remember { mutableStateOf(FlipCard.Forward) }

    FlipRotate(
        flipCard = flipCard,
        onClick = { flipCard = flipCard.next },
        modifier = Modifier
            .fillMaxWidth(.6f)
            .aspectRatio(1f),
        forward = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ForwardColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_fast_forward_24),
                    contentDescription = "Forward",
                    modifier = Modifier.size(120.dp)
                )
            }
        },
        previous = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(PreviousColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_baseline_fast_rewind_24),
                    contentDescription = "Previous",
                    modifier = Modifier.size(120.dp)
                )
            }
        }
    )
}

@ExperimentalMaterialApi
@Composable
fun FlipRotate(
    flipCard: FlipCard,
    onClick: (FlipCard) -> Unit,
    modifier: Modifier = Modifier,
    previous: @Composable () -> Unit = {},
    forward: @Composable () -> Unit = {}
) {
    val rotation = animateFloatAsState(
        targetValue = flipCard.angle,
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutSlowInEasing
        )
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .background(MaterialTheme.colors.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Flip Rotation",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                onClick = { onClick(flipCard) },
                modifier = modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = rotation.value
                        cameraDistance = 12f * density
                    },
                elevation = 10.dp,
                shape = RoundedCornerShape(10.dp)
            ) {
                if (rotation.value <= 90f) {
                    Box(
                        Modifier.fillMaxSize()
                    ) {
                        forward()
                    }
                } else {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                rotationY = 180f
                            }
                    ) {
                        previous()
                    }
                }
            }
        }
    }
}























