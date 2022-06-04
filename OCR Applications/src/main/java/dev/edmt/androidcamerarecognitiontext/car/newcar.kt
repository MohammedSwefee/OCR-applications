package dev.edmt.androidcamerarecognitiontext.car

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dev.edmt.androidcamerarecognitiontext.R
import java.text.SimpleDateFormat
import java.util.*

class newcar : AppCompatActivity() {

    lateinit var calendar : Calendar
    lateinit var simpleDate: SimpleDateFormat
    lateinit var ddate:String
    lateinit var carr_number: EditText
    lateinit var owner_name: EditText
    lateinit var check_date: TextView
    lateinit var car_color: EditText
    lateinit var car_model: EditText
    lateinit var wanted: EditText
    lateinit var car_case: EditText
    lateinit var add: Button
    lateinit var uploadcardata: Button
    lateinit var refcarr : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_newcar)

        add=findViewById(R.id.addcar) as Button
        uploadcardata=findViewById(R.id.uploadcardata) as Button
        carr_number = findViewById(R.id.fff) as EditText
        owner_name= findViewById(R.id.owner_namee) as EditText
        check_date= findViewById(R.id.check_datee) as EditText
        car_color= findViewById(R.id.car_colorr) as EditText
        car_model= findViewById(R.id.car_modell) as EditText
        wanted= findViewById(R.id.wantedd) as EditText
        car_case= findViewById(R.id.car_casee) as EditText



        calendar=Calendar.getInstance()
        simpleDate= SimpleDateFormat("dd-MM-yy HH:mm:ss")
        ddate=simpleDate.format(calendar.time)
        check_date.text = ddate

        add.setOnClickListener {

        val carr_number =carr_number.text.toString().trim()
        val owner_name =owner_name.text.toString().trim()
        val check_date =Calendar.getInstance().time.toString().trim()
        val car_color =car_color.text.toString().trim()
        var car_model=car_model.text.toString().trim()
        val wanted =wanted.text.toString().trim()
        val car_case =car_case.text.toString().trim()


            refcarr = FirebaseDatabase.getInstance().getReference("cars")


        val carId =  refcarr.push().key
        val car= cars(carId,carr_number,owner_name,check_date,car_color,car_model,wanted,car_case,"")
        refcarr.child(carr_number).setValue(car).addOnCompleteListener{

            Toast.makeText(this,"added :)", Toast.LENGTH_LONG).show()

        }}

        uploadcardata.setOnClickListener {

            val myIntent = Intent(this, caradmin::class.java)
            startActivity(myIntent)
        }

    }




}
