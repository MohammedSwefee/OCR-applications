package dev.edmt.androidcamerarecognitiontext.car

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*
import dev.edmt.androidcamerarecognitiontext.R
import java.text.SimpleDateFormat
import java.util.*

class check : AppCompatActivity() {

    lateinit var calendar : Calendar
    lateinit var simpleDate: SimpleDateFormat
    lateinit var ddate:String
    lateinit var car_number: TextView
    lateinit var owner_name: TextView
    lateinit var check_date: TextView
    lateinit var car_color: TextView
    lateinit var car_model: TextView
    lateinit var wanted: TextView
    lateinit var car_case: TextView

    lateinit var addnew: Button
    lateinit var refcar : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)

        car_number = findViewById(R.id.car_number) as TextView
        owner_name= findViewById(R.id.owner_name) as TextView
        check_date= findViewById(R.id.check_date) as TextView
        car_color= findViewById(R.id.car_color) as TextView
        car_model= findViewById(R.id.car_model) as TextView
        wanted= findViewById(R.id.wanted) as TextView
        car_case= findViewById(R.id.car_case) as TextView


        val spcar=getSharedPreferences("sp", Context.MODE_PRIVATE)
        val last=spcar.getString("tt","not found")

        calendar=Calendar.getInstance()
        simpleDate= SimpleDateFormat("dd-MM-yy HH:mm:ss")
        ddate=simpleDate.format(calendar.time)
        check_date.text = ddate

        car_number.text=last.toString()

        refcar = FirebaseDatabase.getInstance().getReference("cars")
        refcar.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p1: DatabaseError?) {
            }

            override fun onDataChange(p1: DataSnapshot?) {
                if (p1!!.exists()){

                    owner_name.text= p1.child(last.toString().trim()).child("owner_name").value.toString()
                    car_color.text= p1.child(last.toString().trim()).child("car_color").value.toString()
                    car_model.text= p1.child(last.toString().trim()).child("car_model").value.toString()
                    wanted.text= p1.child(last.toString().trim()).child("wanted").value.toString()
                    car_case.text= p1.child(last.toString().trim()).child("car_case").value.toString()

                }
            }

        })



    }

}






/*

        val car_number =last.toString().trim()
        val owner_name =owner_name.text.toString().trim()
        val check_date =Calendar.getInstance().time.toString().trim()
        val car_color =car_color.text.toString().trim()
        var car_model=car_model.text.toString().trim()
        val wanted =wanted.text.toString().trim()
        val car_case =car_case.text.toString().trim()



        val carId =  refcar.push().key
        val car= cars(carId,car_number,owner_name,check_date,car_color,car_model,wanted,car_case,"")
        refcar.child(car_number).setValue(car).addOnCompleteListener{

             Toast.makeText(this,"checked :)",Toast.LENGTH_LONG).show()

        }
 */