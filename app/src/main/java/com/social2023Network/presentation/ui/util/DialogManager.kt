package com.social2023Network.presentation.ui.util

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.google.firebase.storage.UploadTask

class DialogManager {

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

    fun uploadFilesWithProgressDialog(context: Context, snapshot: UploadTask.TaskSnapshot) {
        val dialog = AlertDialog.Builder(context)
            .setView(ComposeView(context).apply {
                setContent {
                    CustomProgressDialogView(snapshot)
                }
            })
            .setCancelable(false)
            .create()
        snapshot.task.addOnSuccessListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}


@Composable
fun CustomProgressDialogView(snapshot: UploadTask.TaskSnapshot) {
    AlertDialog(
        onDismissRequest = { /* do nothing */ },
        title = {
            Text(
                text = "Uploading...",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        },
        text = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                CircularProgressIndicator(progress = (100 * snapshot.bytesTransferred / snapshot.totalByteCount).toFloat())
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Uploading...")
            }
        },
        backgroundColor = Color.White,
        contentColor = Color.Black,
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(horizontal = 32.dp),
        buttons = {}
    )
}