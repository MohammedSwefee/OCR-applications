package dev.edmt.androidcamerarecognitiontext.car

/**
 * Created by Mohammed on 08/04/2018.
 */
class cars (val id:String, val car_number:String,val owner_name:String,
            val check_date:String, val car_color:String,val car_model:String,
            val wanted:String, val car_case:String,
            val car_email:String){

    constructor():this("","","",
            "","","",
            "","","")

}