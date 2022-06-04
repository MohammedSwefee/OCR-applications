package dev.edmt.androidcamerarecognitiontext

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import dev.edmt.androidcamerarecognitiontext.Reader.admin
import dev.edmt.androidcamerarecognitiontext.Reader.login
import dev.edmt.androidcamerarecognitiontext.car.carreader
import dev.edmt.androidcamerarecognitiontext.car.newcar

class Boss : AppCompatActivity() {



    lateinit var app1:Button
    lateinit var app2:Button
    lateinit var app3:Button
    lateinit var app4:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boss)

        app1=findViewById(R.id.app1) as Button
        app2=findViewById(R.id.app2) as Button
        app3=findViewById(R.id.app3) as Button
        app4=findViewById(R.id.app4) as Button

        app1.setOnClickListener{
            val myIntent = Intent(this,carreader::class.java)
            startActivity(myIntent)
        }

        app2.setOnClickListener{
            val myIntent = Intent(this, login::class.java)
            startActivity(myIntent)
        }

        app3.setOnClickListener{
            val myIntent = Intent(this, admin::class.java)
            startActivity(myIntent)
        }

        app4.setOnClickListener{
            val myIntent = Intent(this,newcar::class.java)
            startActivity(myIntent)
        }
    }
}
