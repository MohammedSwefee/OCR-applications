package dev.edmt.androidcamerarecognitiontext.Reader

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*
import dev.edmt.androidcamerarecognitiontext.R
import java.text.SimpleDateFormat
import java.util.*


class userinterface : AppCompatActivity() {

    lateinit var calendar :Calendar
    lateinit var simpleDate:SimpleDateFormat
    lateinit var ddate:String
    lateinit var account_numberr:TextView
    lateinit var last_readingg: TextView
    lateinit var datee: TextView
    lateinit var current_readingg:TextView
    lateinit var current_ratee:TextView
    lateinit var arreass:TextView
    lateinit var total_ratee:TextView
    lateinit var veiw: TextView
    lateinit var mo:Button
    lateinit var ref : DatabaseReference
    lateinit var list1:MutableList<users>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userinterface)



        account_numberr = findViewById(R.id.account_number) as TextView
        last_readingg= findViewById(R.id.last_reading) as TextView
        datee= findViewById(R.id.date) as TextView
        current_readingg= findViewById(R.id.current_reading) as TextView
        current_ratee= findViewById(R.id.current_rate) as TextView
        arreass= findViewById(R.id.arrears) as TextView
        total_ratee= findViewById(R.id.total_rate) as TextView
        var mo=findViewById(R.id.mo) as Button


        val sp=getSharedPreferences("sp", Context.MODE_PRIVATE)
        val last=sp.getString("current","not found")
        val mail=sp.getString("email","not found")






        calendar=Calendar.getInstance()
        simpleDate= SimpleDateFormat("dd-MM-yy HH:mm:ss")
        ddate=simpleDate.format(calendar.time)
        //datee.text = ddate

        var xname=""
        var xaddress=""
        var xcurrent_readingg=""
        var xaccount_numberr=""
        var xlast_readingg=""
        var xarreass=""
        var xcurrent_ratee=""
        var xtotal_ratee=""
        var loop=1

                ref = FirebaseDatabase.getInstance().getReference("users")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()&&loop==1){

                    for (e in p0.children){

                        if (e.child("emailll").value.toString()==mail&&loop==1){



                           xname=e.child("nameee").value.toString()
                           xaddress=e.child("aaaddress").value.toString()
                           xcurrent_readingg= last
                           xaccount_numberr= e.key.toString()
                           xlast_readingg=e.child("lastreadinggg").value.toString()
                           datee.text= e.child("dateee").value.toString()
                           xarreass=e.child("arreasss").value.toString()
                           xcurrent_ratee=(last.toInt()*0.01).toString()
                           xtotal_ratee=(e.child("arreasss")
                                    .value.toString()
                                    .toDouble()+(last.toInt()*0.01)
                                    .toString().toDouble())
                                    .toString()

                            current_readingg.text=xcurrent_readingg
                            account_numberr.text=xaccount_numberr
                            last_readingg.text=xlast_readingg
                            arreass.text=xarreass
                            current_ratee.text=xcurrent_ratee
                            total_ratee.text=xtotal_ratee



                            val current_time = Calendar.getInstance().time.toString()

                            val userId =  ref.push().key
                            val user= users(userId, xaccount_numberr, xname, xcurrent_readingg, current_time, xaddress, xcurrent_ratee, xtotal_ratee, mail)
                            ref.child(xaccount_numberr).setValue(user).addOnCompleteListener{

                            }
                            loop++
                            return
                        }


                    }


                    return

                }


            }


        })


        mo.setOnClickListener{ val myIntent = Intent(this, admin::class.java)
            startActivity(myIntent)}
    }




}
