package com.jeongseunggyu.devjeongseungyusns

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.jeongseunggyu.devjeongseungyusns.ui.screens.main.AddPostScreen
import com.jeongseunggyu.devjeongseungyusns.ui.theme.DevJeongseungyuSnsTheme
import com.jeongseunggyu.devjeongseungyusns.viewmodels.AddPostViewModel

class AddPostActivity: ComponentActivity(){

    private val addPostViewModel : AddPostViewModel by viewModels()

    companion object {
        fun newIntent(context: Context) = Intent(context, AddPostActivity::class.java)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setSoftInputMode(
            WindowManager
                .LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )

        setContent {
            DevJeongseungyuSnsTheme {
                AddPostScreen(addPostViewModel = addPostViewModel)
            }
        }
    }

}