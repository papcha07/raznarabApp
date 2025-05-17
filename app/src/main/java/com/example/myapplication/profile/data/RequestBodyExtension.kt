package com.example.myapplication.profile.data

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun String.toRequestBodyOrNull(): RequestBody? =
    if (this.isBlank()) null else this.toRequestBody("text/plain".toMediaTypeOrNull())

fun Boolean?.toRequestBodyOrNull(): RequestBody? =
    this?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())

fun File?.toMultipartBodyPart(name: String): MultipartBody.Part? {
    return this?.let {
        val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
        MultipartBody.Part.createFormData(name, it.name, requestFile)
    }
}

fun uriToFile(context: Context, uri: Uri?, fileName: String = "temp_image"): File? {
    val file = File(context.cacheDir, fileName)
    if(uri != null){
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                copyStream(inputStream, outputStream)
            }
        }
        return file
    }
    else{
        return null
    }
}

private fun copyStream(input: InputStream, output: FileOutputStream) {
    val buffer = ByteArray(4 * 1024) // 4KB buffer
    var read: Int
    while (input.read(buffer).also { read = it } != -1) {
        output.write(buffer, 0, read)
    }
    output.flush()
}