package com.example.albertpractice

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var someData: Uri? = intent.extras?.get("image") as Uri?
        imageView.setImageURI(someData)
        var bitmap = imageView.drawable.toBitmap()
        var palette: Palette = createPaletteSync(bitmap, 11)

        //сохранение изображения в память для истории
        var edit = getSharedPreferences("history", Context.MODE_PRIVATE).edit()
        edit.putString("URI", saveImageToInternalStorage(bitmap).toString())
        edit.apply()
        Log.d("saved", saveImageToInternalStorage(bitmap).toString())

        //установка цветов для view
        color_view.setBackgroundColor(palette.swatches[0].rgb)
        color_view2.setBackgroundColor(palette.swatches[1].rgb)
        color_view3.setBackgroundColor(palette.swatches[2].rgb)
        color_view4.setBackgroundColor(palette.swatches[3].rgb)
        color_view5.setBackgroundColor(palette.swatches[4].rgb)
        color_view6.setBackgroundColor(palette.swatches[5].rgb)
        color_view7.setBackgroundColor(palette.swatches[6].rgb)
        color_view8.setBackgroundColor(palette.swatches[7].rgb)
        color_view9.setBackgroundColor(palette.swatches[8].rgb)
        color_view10.setBackgroundColor(palette.swatches[9].rgb)
        color_view11.setBackgroundColor(palette.swatches[10].rgb)

        imageView4.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, StartActivity::class.java)
        startActivity(intent)
    }

    private fun createPaletteSync(bitmap: Bitmap, numberOfColors: Int): Palette =
        Palette.Builder(bitmap).maximumColorCount(numberOfColors).generate()

    private fun saveImageToInternalStorage(bitmap: Bitmap):Uri{

        // Get the context wrapper instance
        val wrapper = ContextWrapper(applicationContext)

        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir("images", Context.MODE_PRIVATE)


        // Create a file to save the image
        file = File(file, "${"lastImage"}.jpg")

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException){ // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image uri
        return Uri.parse(file.absolutePath)
    }
}
