package dev.edmt.androidcamerarecognitiontext.zain

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import dev.edmt.androidcamerarecognitiontext.R
import dev.edmt.androidcamerarecognitiontext.Reader.admin
import dev.edmt.androidcamerarecognitiontext.Reader.login
import dev.edmt.androidcamerarecognitiontext.car.carreader
import dev.edmt.androidcamerarecognitiontext.car.newcar

class ser : AppCompatActivity() {


    lateinit var app4:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ser)



        app4=findViewById(R.id.app4) as Button


        app4.setOnClickListener{
            val myIntent = Intent(this, zain::class.java)
            startActivity(myIntent)
        }
    }
}
