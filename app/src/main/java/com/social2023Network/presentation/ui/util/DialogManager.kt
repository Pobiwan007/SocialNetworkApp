package com.social2023Network.presentation.ui.util

import android.R
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.storage.UploadTask

class DialogManager{

    fun showPermissionSettingsDialog(context: Context) {
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

    fun showProgressDialog(uploadTasks: List<UploadTask>, context: Context): AlertDialog {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Uploading...")
        builder.setCancelable(false)

        val progressBar = ProgressBar(context, null, R.attr.progressBarStyleHorizontal)
        progressBar.max = 100

        builder.setView(progressBar)

        val dialog = builder.create()
        dialog.show()

        var totalBytesUploaded = 0L
        var totalBytesToUpload = 0L

        uploadTasks.forEach { task ->
            totalBytesToUpload += task.snapshot.totalByteCount
            task.addOnProgressListener { taskSnapshot ->
                totalBytesUploaded += (taskSnapshot.bytesTransferred - taskSnapshot.bytesTransferred)
                val progress = (100.0 * totalBytesUploaded / totalBytesToUpload).toInt()
                progressBar.progress = progress
            }

            task.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    dialog.dismiss()
                    Toast.makeText(context, "Upload failed!", Toast.LENGTH_SHORT).show()
                    return@addOnCompleteListener
                }

                if (totalBytesUploaded >= totalBytesToUpload) {
                    dialog.dismiss()
                    Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return dialog
    }



}
