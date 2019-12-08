package com.example.smartmarksinput.activities

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Spinner
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.app.Activity
import android.widget.Toast
import com.example.smartmarksinput.R
import com.example.smartmarksinput.dropDownInit
import com.example.smartmarksinput.services.ImageProcessingService


class MainActivity : AppCompatActivity() {

    lateinit var semester: Spinner
    lateinit var exam: Spinner
    lateinit var section: Spinner
    private val CAMERA_REQUEST = 1888
    private var imageView: ImageView? = null
    private val MY_CAMERA_PERMISSION_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        semester = findViewById(R.id.semester) as Spinner
        exam = findViewById(R.id.exam) as Spinner
        section = findViewById(R.id.section) as Spinner
        val semesterArray = arrayOf("Select a semester", "Spring-2019", "Summer-2019", "Fall-2019")
        semester.adapter = dropDownInit(semesterArray)

        semester.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@MainActivity,
                    semesterArray.get(position).toString(),
                    Toast.LENGTH_LONG
                ).show()
            }

        }
        val examArray = arrayOf("Select an exam", "Midterm", "Final")
        exam.adapter = dropDownInit(examArray)

        exam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@MainActivity,
                    examArray.get(position).toString(),
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        val sectionArray = arrayOf("Select a section", "PC-A", "PC-B", "PC-C", "PC-D")
        section.adapter = dropDownInit(sectionArray)

        section.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(
                    this@MainActivity,
                    sectionArray.get(position).toString(),
                    Toast.LENGTH_LONG
                ).show()

            }

        }




        btnscan.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.image_source_bottom_sheet, null)
            dialog.setContentView(view)
            val close = view.findViewById<Button>(R.id.btnCancel)
            val takePhoto = view.findViewById<Button>(R.id.btnTakphoto)
            close.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()

            takePhoto.setOnClickListener {
                openCammera()
            }


        }


    }

    public fun openCammera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
        } else {
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Toast.makeText(this@MainActivity, Activity.RESULT_OK.toString(), Toast.LENGTH_LONG).show()
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap

            var img = findViewById<ImageView>(R.id.imageView)
            img.setImageBitmap(photo)

            val imageProcessingService: ImageProcessingService = ImageProcessingService()
            imageProcessingService.sendImage(photo)



//
//            MultipartBody.Part body = MultipartBody . Part . createFormData ("upload", f.getName(), reqFile);
//
//            photo.compress(Bitmap.CompressFormat.JPEG, 50, baos)
//            val imageBytes = baos.toByteArray()
//            val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//
//
//            Log.d("imageString", imageBytes.toString())
//
//            var f = File()
//            Log.("TAG", photo)
//            val f = File(Context.getCacheDir(), "test")
//            f.createNewFile
//            val sizeBytes = getSizeFromBitmap(photo)
//            Toast.makeText(this@MainActivity, sizeBytes.toString(), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@MainActivity, "in else", Toast.LENGTH_LONG).show()
        }
    }


}
