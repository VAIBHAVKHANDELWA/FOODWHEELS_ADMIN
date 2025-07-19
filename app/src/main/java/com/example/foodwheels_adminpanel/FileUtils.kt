package com.example.foodwheels_adminpanel

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore

object FileUtils {
    fun getPath(context: Context, uri: Uri): String {
        var path = ""
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                path = it.getString(columnIndex)
            }
            it.close()
        }
        return path
    }
}
