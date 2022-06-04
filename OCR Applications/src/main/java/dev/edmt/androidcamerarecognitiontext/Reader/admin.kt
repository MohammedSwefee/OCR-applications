package dev.edmt.androidcamerarecognitiontext.Reader

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.muhammed.myfirebasedatabase.userAdapter
import com.google.firebase.database.*
import dev.edmt.androidcamerarecognitiontext.R


class admin : AppCompatActivity() {

    lateinit var ref : DatabaseReference
    lateinit var list:MutableList<users>
    lateinit var listview:ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        list = mutableListOf()
        listview = findViewById(R.id.listview1) as ListView
        ref = FirebaseDatabase.getInstance().getReference("users")

        ref.addValueEventListener(object :ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()){
                    list.clear()
                    for (e in p0.children){
                        val user = e.getValue(users::class.java)
                        list.add(user!!)
                    }
                    val adapter = userAdapter(this@admin, R.layout.users,list)
                    listview.adapter = adapter
                }
            }

        })
    }
}
