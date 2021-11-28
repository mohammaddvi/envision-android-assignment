package com.envision.assignment

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.envision.assignment.view.screen.MainScreen
import com.envision.core.theme.EnvisionTheme
import com.google.accompanist.insets.statusBarsPadding

class MainActivity : AppCompatActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnvisionTheme {
                Surface(Modifier.fillMaxSize(), color = EnvisionTheme.colors.background) {
                    Column(Modifier.fillMaxSize()) {
                        MainScreen(modifier = Modifier
                            .fillMaxSize()
                            .statusBarsPadding())
                    }
                }
            }
        }
    }
}