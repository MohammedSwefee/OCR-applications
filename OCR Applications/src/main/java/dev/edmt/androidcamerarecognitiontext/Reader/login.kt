package dev.edmt.androidcamerarecognitiontext.Reader

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import dev.edmt.androidcamerarecognitiontext.R
import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {
    var myauth= FirebaseAuth.getInstance()
    lateinit var btn_login: Button
    lateinit var btn_offline: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btn_login = findViewById(R.id.btn_login) as Button
       // btn_offline = findViewById(R.id.btn_offline) as Button

        /*btn_offline.setOnClickListener {
            var intent=Intent(this,MainActivity::class.java)
            intent.putExtra("id",myauth.currentUser?.email)
            startActivity(intent)
        }*/
        btn_login.setOnClickListener {
            view ->
            val email=email.text.toString()
            val password=password.text.toString()
            signin(view,email,password)

            val sp=getSharedPreferences("sp", Context.MODE_PRIVATE)
            val editsp=sp.edit()
            editsp.putString("email",email)
            editsp.apply()
        }


        val signup = findViewById(R.id.link_signup) as TextView
        // Capture button clicks
        signup.setOnClickListener {
            // Start NewActivity.class
            val myIntent = Intent(this@login,
                    registration::class.java)
            startActivity(myIntent)
        }
    }
  private  fun signin(view:View,email:String,password:String){
      Toast.makeText(this,"Authenticating ...",Toast.LENGTH_LONG).show()
      myauth.signInWithEmailAndPassword(email,password)
              .addOnCompleteListener(this, OnCompleteListener {task ->
                  if(task.isSuccessful){
                      var intent=Intent(this, MainActivity::class.java)
                      intent.putExtra("id",myauth.currentUser?.email)
                      startActivity(intent)
                      }else{
                      Toast.makeText(this,"sorry !!" + task.exception?.message,Toast.LENGTH_LONG).show()

                  }
              })

  }


}
