package com.example.myapplication.profile.data.network

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException

object FileConverterProfile {
    fun prepareFilePart(context: Context, fieldName: String, uri: Uri?): MultipartBody.Part? {
        if (uri == null) return null

        val contentResolver = context.contentResolver

        val bitmap: Bitmap = try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                @Suppress("DEPRECATION")
                android.provider.MediaStore.Images.Media.getBitmap(contentResolver, uri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null // Не удалось декодировать изображение
        }

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        val jpegBytes = outputStream.toByteArray()

        val originalName = getFileName(context, uri) ?: "image"
        val fileName = if (originalName.endsWith(".jpg", ignoreCase = true)) {
            originalName
        } else {
            originalName.substringBeforeLast('.') + ".jpg"
        }

        val mediaType = "image/jpeg".toMediaType()
        val requestBody = jpegBytes.toRequestBody(mediaType)

        return MultipartBody.Part.createFormData(fieldName, fileName, requestBody)
    }

    fun getFileName(context: Context, uri: Uri): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        returnCursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex != -1) {
                return it.getString(nameIndex)
            }
        }
        return null
    }
}