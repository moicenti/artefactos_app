package com.example.artefactos

import android.app.Application

class artefactosAPP: Application() {
    lateinit var token: String;
    lateinit var user: String;

    fun changeUser(user: String) {
        this.user = user;
    }
    fun obtainUser(): String {
        return user;
    }


    fun changeToken(token: String) {
        this.token = token
    }
    fun obtainToken(): String {
        return token;
    }

    override fun onCreate() {
        super.onCreate()
        changeToken("");
    }


}