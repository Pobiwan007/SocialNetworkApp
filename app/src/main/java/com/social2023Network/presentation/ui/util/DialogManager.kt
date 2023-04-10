package com.social2023Network.presentation.ui.util

import android.R
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.net.Uri
import android.provider.Settings
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.storage.UploadTask
import com.social2023Network.presentation.MainActivity

class DialogManager{

    private val context: Context = MainActivity.instance
    fun showPermissionSettingsDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Permission Required")
        builder.setMessage("The app requires permission to read external storage. Please grant the permission in the app settings.")
        builder.setPositiveButton("Go to Settings") { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    fun showProgressDialog(taskSnapshot: UploadTask) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Uploading...")
        builder.setCancelable(false)

        val progressBar = ProgressBar(context, null, R.attr.progressBarStyleHorizontal)
        progressBar.progressTintList = ColorStateList.valueOf(Color.BLUE)
        progressBar.progressBackgroundTintList = ColorStateList.valueOf(Color.GRAY)

        builder.setView(progressBar)

        val dialog = builder.create()
        var bytesUploaded = 0L
        var totalBytes = 0L

        taskSnapshot.addOnProgressListener {
            bytesUploaded = it.bytesTransferred
            totalBytes = it.totalByteCount

            // Calculate the progress percentage
            val progress = (100 * bytesUploaded / totalBytes).toInt()

            // Update the progress dialog with the progress value
            progressBar.progress = progress
        }
        taskSnapshot.addOnCompleteListener{
            if (!it.isSuccessful) {
                dialog.dismiss()
                Toast.makeText(context, "Upload failed!", Toast.LENGTH_SHORT).show()
                return@addOnCompleteListener
            }

            if (bytesUploaded >= totalBytes) {
                dialog.dismiss()
                Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show()
            }
        }
        progressBar.max = 100
        dialog.show()
    }



}
