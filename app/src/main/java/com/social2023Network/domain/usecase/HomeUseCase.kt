package com.social2023Network.domain.usecase

import android.content.Context
import android.net.Uri
import android.os.Build
import android.webkit.MimeTypeMap
import androidx.compose.ui.graphics.Color
import com.social2023Network.presentation.ui.theme.pink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeUseCase {

    suspend fun convertDate(dateStr: String): String  = withContext(Dispatchers.IO){
        // create a DateTimeFormatter object to parse the input date string
        //2013-02-20T16:00:23.893Z
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS") //2023-03-22T21:02:18.041
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        // parse the input date string into a LocalDateTime object
        val dateTime = LocalDateTime.parse(dateStr, formatter)

        // extract the day, month, and year from the LocalDateTime object
        val day = dateTime.dayOfMonth
        val month = dateTime.monthValue
        val year = dateTime.year
        val hour = dateTime.hour
        val minutes = dateTime.minute

        // format the date as "dd-mm-yyyy"
        val formattedDate = "%02d-%02d-%04d %02d:%02d".format(day, month, year, hour, minutes)

        formattedDate

    }

    suspend fun getColorByRating(rating: String) : Color = withContext(Dispatchers.Default){
        when (rating) {
            "G" -> Color.Green
            "PG" -> Color.Yellow
            "PG-13" -> pink
            "R" -> Color.Red
            else -> Color.Gray
        }
    }

    suspend fun convertUriToFileExtension(uri: Uri, context: Context) : String? = withContext(Dispatchers.Default){
        MimeTypeMap.getSingleton().getExtensionFromMimeType(context.contentResolver.getType(uri))
    }
}