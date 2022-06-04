package com.example.muhammed.myfirebasedatabase

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import dev.edmt.androidcamerarecognitiontext.R
import dev.edmt.androidcamerarecognitiontext.Reader.users

/**
 * Created by muhammed on 11/11/17.
 */
class userAdapter(val mCtx : Context , val layoutId:Int , val userList:List<users>)
    :ArrayAdapter<users>(mCtx,layoutId,userList){
    lateinit var ref : DatabaseReference

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view:View = layoutInflater.inflate(layoutId,null)

        val account_number = view.findViewById(R.id.account_number) as TextView
        val arrears = view.findViewById(R.id.arrears)as TextView

        val updateBtn = view.findViewById(R.id.update)
        val deleteBtn = view.findViewById(R.id.deletecar)

        val user = userList[position]

        account_number.text = user.accountnumberrr
        val x=user.accountnumberrr
        arrears.text=user.arreasss


        updateBtn.setOnClickListener {
            updateInfo(user,x)
        }

        deleteBtn.setOnClickListener {
            deleteInfo(user)
        }

        return view
    }

    private  fun updateInfo(user: users, x:String){

        val builder = AlertDialog.Builder(mCtx)
        builder.setTitle("Update Info")
        val inflater = LayoutInflater.from(mCtx)
        val view = inflater.inflate(R.layout.user_update,null)

        val account_numberup = view.findViewById(R.id.accountnumberup) as TextView
        val arrearsup = view.findViewById(R.id.arearsup)as TextView

        account_numberup.text = user.accountnumberrr
        arrearsup.text = user.arreasss

        builder.setView(view)

        builder.setPositiveButton("Update",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

              val  myDatabase = FirebaseDatabase.getInstance().getReference("users")

                val account_number    = account_numberup.text.toString().trim()
                val arrearss     = arrearsup.text.toString().trim()


                if (account_number.isEmpty()){
                    account_numberup.error = "Please enter  account number"
                    return
                }
                if (arrearss.isEmpty()){
                    arrearsup.error = "Please enter  total rate"
                    return
                }


                val user = users(user.id, account_number, user.nameee, user.lastreadinggg, user.dateee, user.aaaddress, user.currentrateee, arrearss, user.emailll)
                myDatabase.child(account_number).setValue(user)
                    Toast.makeText(mCtx,"Updated :) ", Toast.LENGTH_LONG).show()
                if (x!=account_number){
                myDatabase.child(x).removeValue()}
        }})

        builder.setNegativeButton("cancel",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }

        })

        val alert = builder.create()
        alert.show()

    }


    private fun deleteInfo(user: users){
        val myDatabase = FirebaseDatabase.getInstance().getReference("users")
        myDatabase.child(user.accountnumberrr).removeValue()
        Toast.makeText(mCtx,"Deleted !",Toast.LENGTH_LONG).show()
    }

}









// val q=account_number.text

/* var loop=1
  ref = FirebaseDatabase.getInstance().getReference("users")
 ref.addValueEventListener(object : ValueEventListener {
     override fun onCancelled(p0: DatabaseError?) {}

     override fun onDataChange(p0: DataSnapshot?) {
         if (p0!!.exists()){

             for (e in p0.children){

                 if (e.child("accountnumberrr").value.toString()==q&&loop==1){


                     var h=(e.child("arreasss"))
                             .value.toString()
                             .toDouble()+(user.arreasss).toDouble()
                     arrears.text=h.toString()

                     loop++
                     return
                      }


             }


             return

         }


     }


 })*/
