package com.example.imagegallerylive

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imagegallerylive.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imgrecycler: RecyclerView
    private lateinit var progbar: ProgressBar
    private lateinit var galleryimages: ArrayList<Image>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        imgrecycler = binding.imageRecycler
        progbar = binding.progressBarrrrrr
        imgrecycler.layoutManager = GridLayoutManager(this , 3)


        if(ContextCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.READ_EXTERNAL_STORAGE
            )!=PackageManager.PERMISSION_GRANTED)
        {
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            101
        )
        }

        // We have  the permission to access the gallery
        galleryimages = ArrayList()

        if(galleryimages!!.isEmpty()){
            progbar.visibility = View.VISIBLE
            // images are loading here
            galleryimages = getImagesFromGallery()

//            println("Image Fetch Start")
//            for (image in galleryimages) {
//                println("Image Name: ${image.imageName}, Image Path: ${image.imagePath}")
//            }
//            println("Image Fetch End")

            imgrecycler.adapter = ImageAdapter(this, galleryimages )

            progbar.visibility = View.GONE
        }

    }

    private fun getImagesFromGallery(): ArrayList<Image> {
val imagesList = ArrayList<Image>()

        val projection = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA
        )

        val queryUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor:Cursor? = contentResolver.query(queryUri, projection, null,null,sortOrder)


        cursor?.use{
            val nameColumn =it.getColumnIndexOrThrow( MediaStore.Images.Media.DISPLAY_NAME)
            val dataColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (it.moveToNext()){
                val name = it.getString(nameColumn)
                val data = it.getString(dataColumn)

                val image = Image(data, name)
//                println("${image.imageName} , ${image.imagePath}")
                imagesList.add(image)
            }
        }

        return imagesList
    }
}