package dev.edmt.androidcamerarecognitiontext.car

/**
 * Created by Mohammed on 08/04/2018.
 */

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import dev.edmt.androidcamerarecognitiontext.R


class carAdapter(val mCtxx : Context , val layoutId:Int , val carList:List<cars>)
    :ArrayAdapter<cars>(mCtxx,layoutId,carList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtxx)
        val viewcar:View = layoutInflater.inflate(layoutId,null)

        val carnumber = viewcar.findViewById(R.id.carnumber) as TextView
        val iswanted = viewcar.findViewById(R.id.iswanted)as TextView

        val updateBtn = viewcar.findViewById(R.id.updatecar)
        val deleteBtn = viewcar.findViewById(R.id.deletecar)

        val car = carList[position]

        carnumber.text = car.car_number
        val y=car.car_number
        iswanted.text = car.wanted


        updateBtn.setOnClickListener {
            updateInfo(car,y)
        }

        deleteBtn.setOnClickListener {
            deleteInfo(car)
        }

        return viewcar
    }

    private  fun updateInfo(car:cars ,y:String){

        val builder = AlertDialog.Builder(mCtxx)
        builder.setTitle("Update Info")
        val inflater = LayoutInflater.from(mCtxx)
        val viewcar = inflater.inflate(R.layout.car_update,null)

        val carnumberup = viewcar.findViewById(R.id.carnumberup) as TextView
        val iswantedup = viewcar.findViewById(R.id.iswantedup)as TextView

        carnumberup.text = car.car_number
        iswantedup.text = car.wanted

        builder.setView(viewcar)

        builder.setPositiveButton("Update",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

                val  myDatabasecar = FirebaseDatabase.getInstance().getReference("cars")

                val car_number    = carnumberup.text.toString().trim()
                val wanted     = iswantedup.text.toString().trim()


                if (car_number.isEmpty()){
                    carnumberup?.error = "Please enter  account number"
                    return
                }
                if (wanted.isEmpty()){
                    iswantedup?.error = "Please enter  total rate"
                    return
                }


                val car = cars(car.id,car_number,car.owner_name,car.check_date,car.car_color,car.car_model,wanted,car.car_case,"")
                myDatabasecar.child(car_number).setValue(car)
                Toast.makeText(mCtxx,"Updated :) ", Toast.LENGTH_LONG).show()
                if (y!=car_number){
                myDatabasecar.child(y).removeValue()}
            }})

        builder.setNegativeButton("cancel",object :DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }

        })

        val alert = builder.create()
        alert.show()

    }


    private fun deleteInfo(car:cars){
        val myDatabasecar = FirebaseDatabase.getInstance().getReference("cars")
        myDatabasecar.child(car.car_number).removeValue()
        Toast.makeText(mCtxx,"Deleted !",Toast.LENGTH_LONG).show()
    }

}
