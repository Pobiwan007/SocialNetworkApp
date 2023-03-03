package com.social2023Network.domain.usecase

import android.os.Build
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ConverterDataUseCase {


    suspend fun execute(dateStr: String): String  = withContext(Dispatchers.IO){
        // create a DateTimeFormatter object to parse the input date string
        //2013-02-20T16:00:23.893Z
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        // parse the input date string into a LocalDateTime object
        val dateTime = LocalDateTime.parse(dateStr, formatter)

        // extract the day, month, and year from the LocalDateTime object
        val day = dateTime.dayOfMonth
        val month = dateTime.monthValue
        val year = dateTime.year

        // format the date as "dd-mm-yyyy"
        val formattedDate = "%02d-%02d-%04d".format(day, month, year)

        formattedDate

    }
}