package com.vinu.vinodassigment

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vinu.vinodassigment.ui.MainActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Test
    fun testMainActivity() {
        launchActivity<MainActivity>()
    }

}