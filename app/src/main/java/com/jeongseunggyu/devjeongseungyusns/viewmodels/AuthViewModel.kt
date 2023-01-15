package com.jeongseunggyu.devjeongseungyusns.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class AuthViewModel : ViewModel(){

    var isLoggedIn = MutableStateFlow<Boolean>(false) //로그인이 안되어 있다.
}