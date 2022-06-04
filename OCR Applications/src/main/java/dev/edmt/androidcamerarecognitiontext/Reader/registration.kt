package dev.edmt.androidcamerarecognitiontext.Reader

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dev.edmt.androidcamerarecognitiontext.R
import kotlinx.android.synthetic.main.activity_registration.*

class registration : AppCompatActivity() {
    var myauth=FirebaseAuth.getInstance()
    lateinit var email:EditText
    lateinit var password:EditText
    lateinit var signup:Button
   // lateinit var login:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)






           val login = findViewById(R.id.link_login) as TextView
        // Capture button clicks
        login.setOnClickListener {
            // Start NewActivity.class
            val myIntent = Intent(this@registration,
                    dev.edmt.androidcamerarecognitiontext.Reader.login::class.java)
            startActivity(myIntent)
        }



        email= findViewById(R.id.email) as EditText
        password= findViewById(R.id.password) as EditText
        signup= findViewById(R.id.signup) as Button

      //  val email=email.text.toString().trim()
    //    val password=password.text.toString().trim()

        signup.setOnClickListener {

            val email=email.text.toString().trim()
            val password=password.text.toString().trim()
            if(email.isEmpty()){Toast.makeText(this,"please inter your email first",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if(password.isEmpty()){Toast.makeText(this,"please inter your password first",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            register(email,password)
        }
    }





    private fun register(email:String,password:String){
        myauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, OnCompleteListener { task ->
            if (task.isSuccessful){

                val account_numberrr =phone_number.text.toString().trim()
                val nameee=name.text.toString().trim()
                val aaadress=address.text.toString().trim()
                val emailll=email.trim()
                val mydatabase= FirebaseDatabase.getInstance().getReference("users")
                val userId =  mydatabase.push().key
                val user= users(userId, account_numberrr, nameee, "0", "0", aaadress, "0", "0", emailll)
                mydatabase.child(account_numberrr).setValue(user)

                Toast.makeText(this,"Done Successfully !!",Toast.LENGTH_LONG).show()

                val myIntent = Intent(this@registration,
                        login::class.java)
                startActivity(myIntent)

            }
            else{Toast.makeText(this,"Sorry !!"+ task.exception?.message,Toast.LENGTH_LONG).show()}
        })








    }
}
