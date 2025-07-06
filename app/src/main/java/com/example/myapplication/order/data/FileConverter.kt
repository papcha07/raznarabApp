package com.example.myapplication.order.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.IOException

object FileConverter {

    // Функция для преобразования строки в RequestBody
    fun String.asPart(): RequestBody =
        this.toRequestBody("text/plain".toMediaType())

    // Функция для преобразования числа в RequestBody
    fun Double.asPart(): RequestBody =
        this.toString().toRequestBody("text/plain".toMediaType())

    // Функция для подготовки MultipartBody.Part с изображением в формате JPEG
    fun prepareFilePart(context: Context, fieldName: String, uri: Uri): MultipartBody.Part {
        val contentResolver = context.contentResolver

        // Получаем Bitmap из URI
        val bitmap: Bitmap = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            android.provider.MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }

        // Конвертируем Bitmap в JPEG-байты
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
        val jpegBytes = outputStream.toByteArray()

        // Получаем имя файла, принудительно присваиваем расширение .jpg
        val originalName = getFileName(context, uri) ?: "image"
        val fileName = if (originalName.endsWith(".jpg", ignoreCase = true)) {
            originalName
        } else {
            originalName.substringBeforeLast('.') + ".jpg"
        }

        // Создаём RequestBody для JPEG-байтов
        val mediaType = "image/jpeg".toMediaType()
        val requestBody = jpegBytes.toRequestBody(mediaType)

        // Возвращаем MultipartBody.Part
        return MultipartBody.Part.createFormData(fieldName, fileName, requestBody)
    }

    // Функция для получения имени файла из URI
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
