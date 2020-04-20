package com.vinu.vinodassigment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vinu.vinodassigment.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            initializeNewsFragment()
        }
    }

    private fun initializeNewsFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, NewsFragment())
        transaction.commit()
    }
}
