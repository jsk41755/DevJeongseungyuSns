package com.jeongseunggyu.devjeongseungyusns


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.jeongseunggyu.devjeongseungyusns.routes.MainRoute
import com.jeongseunggyu.devjeongseungyusns.ui.screens.main.AddPostScreen
import com.jeongseunggyu.devjeongseungyusns.ui.screens.main.EditPostScreen
import com.jeongseunggyu.devjeongseungyusns.ui.theme.DevJeongseungyuSnsTheme
import com.jeongseunggyu.devjeongseungyusns.viewmodels.EditPostViewModel

class EditPostActivity: ComponentActivity(){

    lateinit var editPostViewModel: EditPostViewModel

    companion object{
        private const val POST_ID = "post_id"
        fun newIntent(context: Context, postId: String)=
            Intent(context, EditPostActivity::class.java).apply {
                putExtra(POST_ID, postId)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val postId: String = intent.getStringExtra(POST_ID)?: ""

        editPostViewModel = EditPostViewModel(postId)

        setContent {
            DevJeongseungyuSnsTheme {
                EditPostScreen(editPostViewModel)
            }
        }
    }

}