package com.example.runfit.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {

    @TypeConverter
    fun fromBitmap (bmp : Bitmap) : ByteArray{
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun fromByteArray (bytes: ByteArray) : Bitmap{
        return BitmapFactory.decodeByteArray(bytes,0,bytes.size)
    }
}

/*
* This is a type converter class having function to convert Bitmap
* into ByteArray and vice versa in order to save complex data into the Room Database
*
 */