package dev.edmt.androidcamerarecognitiontext.car

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
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
import java.io.IOException

class carreader : AppCompatActivity() {


    internal lateinit var cameraViewcar: SurfaceView
    internal lateinit var textViewcar: TextView
    internal lateinit var wwcar: EditText
    internal lateinit var last_reading: TextView
    //internal lateinit var zz: TextView
    internal lateinit var cameraSource: CameraSource
    internal val RequestCameraPermissionID: Int = 1001
    lateinit var checkcar : Button
    lateinit var readcar : Button



    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RequestCameraPermissionID -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return

                    }
                    cameraViewcar = findViewById(R.id.surface_viewcar) as SurfaceView
                    try {
                        cameraSource.start(cameraViewcar.holder)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carreader)

        cameraViewcar = findViewById(R.id.surface_viewcar) as SurfaceView
        checkcar = findViewById(R.id.checkcar) as Button
        readcar= findViewById(R.id.readcar) as Button
        textViewcar= findViewById(R.id.text_viewcar) as TextView
        wwcar= findViewById(R.id.wwcar) as EditText


        readcar.setOnClickListener{
            wwcar.text=null
            var length=textViewcar.text.length
            var min=0
            for (i in 0..length){
                var max=min+1
                if (max<length){
                    var dd=textViewcar.text.subSequence(startIndex = min,endIndex =max).toString()
                    try {  var ii=Integer.parseInt(dd.replace("[\\D]".toRegex(), ""))
                        var y=wwcar.text
                        wwcar.setText(y.toString()+ii.toString())
                        min= min+1
                    }
                    catch (e:NumberFormatException ){min= min+1}
                }
            }
        }


        checkcar.setOnClickListener {


            startcheck()


        }

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

            cameraViewcar.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(surfaceHolder: SurfaceHolder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(this@carreader,
                                    arrayOf(Manifest.permission.CAMERA),
                                    RequestCameraPermissionID)
                            return
                        }
                        cameraSource.start(cameraViewcar.holder)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

                override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {}

                override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) { cameraSource.stop() }
            })

            textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
                override fun release() {}

                override fun receiveDetections(detections: Detector.Detections<TextBlock>) {

                    val items = detections.detectedItems
                    if (items.size() != 0) {
                        val post = textViewcar.post {
                            val stringBuilder = StringBuilder()
                            for (i in 0 until items.size()) {
                                val item = items.valueAt(i)
                                stringBuilder.append(item.value)
                            }
                            textViewcar.text = stringBuilder.toString()

                        }

                    }
                }
            })
        }



    }

    private fun startcheck(){

        val spcar=getSharedPreferences("sp", Context.MODE_PRIVATE)
        val editsp=spcar.edit()

        if (TextUtils.isEmpty(wwcar.text.toString())){wwcar?.error="cannot be empty"
            return
        }
        else if(wwcar.text.length>9){wwcar?.error="cannot be more than 9"
            return} else {
            editsp.putString("tt", wwcar.text.toString())
            // editsp.putString("date",textView.text.toString())
            editsp.apply()
//            intent.putExtra("aaa",wwcar.text.toString())

           val intent = Intent(this, check::class.java)

            startActivity(intent)
        }


    }
}
