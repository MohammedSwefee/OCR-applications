package dev.edmt.androidcamerarecognitiontext.car

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.*
import dev.edmt.androidcamerarecognitiontext.R
class caradmin : AppCompatActivity() {


    lateinit var refcar : DatabaseReference
    lateinit var listcar:MutableList<cars>
    lateinit var listview2:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caradmin)

        listcar = mutableListOf()
        listview2 = findViewById(R.id.listview2) as ListView
        refcar = FirebaseDatabase.getInstance().getReference("cars")

        refcar.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p1: DatabaseError?) {
            }

            override fun onDataChange(p1: DataSnapshot?) {
                if (p1!!.exists()){
                    listcar.clear()
                    for (e in p1.children){
                        val car = e.getValue(cars::class.java)
                        listcar.add(car!!)
                        // val car= p1.child("66").child("car_number").value
                    }

                    val adaptercar = carAdapter(this@caradmin,R.layout.cars,listcar)
                    listview2.adapter = adaptercar
                }
            }

        })
    }
}
