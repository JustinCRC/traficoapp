package com.adisr.trafico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash_screen.*

class Splash_screen : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val topAnimation=AnimationUtils.loadAnimation(this,R.anim.top_animation)
        val bottomAnimation=AnimationUtils.loadAnimation(this,R.anim.bottom_anim)
        apptext.startAnimation(topAnimation)
        adi.startAnimation(bottomAnimation)
        handler =   Handler()
        handler.postDelayed({

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000)



    }
}