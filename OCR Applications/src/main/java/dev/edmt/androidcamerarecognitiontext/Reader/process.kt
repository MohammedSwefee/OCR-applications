package dev.edmt.androidcamerarecognitiontext.Reader

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import dev.edmt.androidcamerarecognitiontext.R
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException


/**
 * Created by Mohammed on 12/12/2017.
 */

class MainActivity : AppCompatActivity() {
    internal lateinit var cameraView: SurfaceView
    internal lateinit var textView: TextView
    internal lateinit var number:EditText
    internal lateinit var last_reading: TextView
    //internal lateinit var zz: TextView
    internal lateinit var cameraSource: CameraSource
    internal val RequestCameraPermissionID: Int = 1001
    lateinit var count : Button
    lateinit var read : Button
 //   lateinit var hh : Button









    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {

                RequestCameraPermissionID -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return

                    }
                    try {
                        cameraSource.start(cameraView.holder)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




//        last_reading = findViewById(R.id.last_reading) as TextView
      //  zz = findViewById(R.id.zz) as TextView
        count = findViewById(R.id.count) as Button
        read= findViewById(R.id.read) as Button
        //hh=findViewById(R.id.hh) as Button
//        hh.setOnClickListener{hh()
//            //savedata()
//            }
         read.setOnClickListener{

           //  readinfo()
            // var i=Integer.parseInt(textView.text.replace("[\\D]".toRegex(), ""))
             ww.text=null
             var length=textView.text.length
             var min=0
            for (i in 0..length){

                var max=min+1
                if (max<length){

                    var dd=textView.text.subSequence(startIndex = min,endIndex =max).toString()
                 try {  var ii=Integer.parseInt(dd.replace("[\\D]".toRegex(), ""))
                       // zz.text= ii.toString().trim()
                    var y=ww.text
                    ww.setText(y.toString()+ii.toString())
                     min= min+1
                 }
                 catch (e:NumberFormatException ){min= min+1}
                }


            }

            // var d=a.subSequence(0,9)
        //     var i=Integer.parseInt(d.replace("[\\D]".toRegex(), ""))
           //  var dd=textView.text.subSequence(startIndex = 0,endIndex =10)
            // var ii=Integer.parseInt(dd.replace("[\\D]".toRegex(), ""))
            // var ddd=textView.text.subSequence(startIndex = 20,endIndex = 29)
          //   var iii=Integer.parseInt(ddd.replace("[\\D]".toRegex(), ""))

             //   var it=Integer.parseInt(d.replaceRange(0,20, "") as String?)


            // qq= qq + qq
         }


            //  readinfo()
        count.setOnClickListener {


            saveinfo()


        }

        cameraView = findViewById(R.id.surface_view) as SurfaceView
        textView = findViewById(R.id.text_view) as TextView





        val textRecognizer = TextRecognizer.Builder(applicationContext).build()
        if (!textRecognizer.isOperational) {
            Log.w("MainActivity", "Detector dependencies are not yet available")
        } else {

            cameraSource = CameraSource.Builder(applicationContext, textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)


                    .build()

            cameraView.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(surfaceHolder: SurfaceHolder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(this@MainActivity,
                                    arrayOf(Manifest.permission.CAMERA),
                                    RequestCameraPermissionID)
                            return
                        }
                        cameraSource.start(cameraView.holder)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

                override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {

                }

                override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
                    cameraSource.stop()
                }
            })

            textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
                override fun release() {

                }

                override fun receiveDetections(detections: Detector.Detections<TextBlock>) {

                    val items = detections.detectedItems
                    if (items.size() != 0) {
                        val post = textView.post {
                            val stringBuilder = StringBuilder()
                            for (i in 0 until items.size()) {
                                val item = items.valueAt(i)
                                stringBuilder.append(item.value)
                                //stringBuilder.append("\n")
                            }
                             textView.text = stringBuilder.toString()


                       //     var i=Integer.parseInt(d.replace("[\\D]".toRegex(), ""))

                         //   textView.text = i.toString().trim()

                          // File("readfile.txt").appendText(data.toString())

                        }

                    }
                }
            })
        }
    }



   // private  fun hh(){hh.text=textView.text}

    private fun saveinfo(){

        val sp=getSharedPreferences("sp", Context.MODE_PRIVATE)
        val editsp=sp.edit()

        if (TextUtils.isEmpty(ww.text.toString())){ww?.error="cannot be empty"
        return
        }
        else if(ww.text.length>9){ww?.error="cannot be more than 9"
            return} else {
            editsp.putString("current", ww.text.toString())
            // editsp.putString("date",textView.text.toString())
            editsp.apply()
            val intent = Intent(this, userinterface::class.java)
            startActivity(intent)
        }


    }


//        private fun readinfo(){
//        val sp=getSharedPreferences("sp", Context.MODE_PRIVATE)
//        val last=sp.getString("last_reading","not found")
//            last_reading.text = last
    //  date.text = sp.getString("date","not found")
   // }









}


