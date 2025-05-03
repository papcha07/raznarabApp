package com.example.myapplication.order.data

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object FileConverter {

    fun String.asPart(): RequestBody =
        this.toRequestBody("text/plain".toMediaType())

    fun Double.asPart(): RequestBody =
        this.toString().toRequestBody("text/plain".toMediaType())

    fun prepareFilePart(context: Context, fieldName: String, uri: Uri): MultipartBody.Part {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri)
            ?: throw IOException("Cannot open input stream from URI")

        val bytes = inputStream.readBytes()
        val fileName = getFileName(context, uri) ?: "image.jpg"
        val mediaType = contentResolver.getType(uri)?.toMediaType() ?: "image/*".toMediaType()

        val requestBody = bytes.toRequestBody(mediaType)
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