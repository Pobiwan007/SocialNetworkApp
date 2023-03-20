package com.social2023Network.presentation.ui.util

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.setViewTreeLifecycleOwner

import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

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

    fun uploadFilesWithProgressDialog(
        snapshot: UploadTask.TaskSnapshot,
        lifecycleOwner: LifecycleOwner,
        context: Context
    ) {
        try {
            val dialog = Dialog(context).apply {
                setCancelable(false)
                setContentView()
            }
            snapshot.task.addOnSuccessListener {
                dialog.dismiss()
            }
            dialog.show()
        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }

    }


}


@Composable
fun CustomProgressDialogView(
    snapshot: UploadTask.TaskSnapshot,
) {
//    val progress = remember { mutableStateOf(0f) }
//    DisposableEffect(snapshot) {
//        val task = snapshot.task
//        val listener =
//            OnProgressListener<UploadTask.TaskSnapshot> { snapshot ->
//                progress.value =
//                    (100.0 * snapshot.bytesTransferred / snapshot.totalByteCount).toFloat()
//            }
//        task.addOnProgressListener(listener)
//        onDispose {
//            task.removeOnProgressListener(listener)
//        }
//    }
//    AlertDialog(
//        onDismissRequest = { },
//        title = {
//            Text(text = "Uploading...")
//        },
//        text = {
//            Column {
//                Text(text = "File Name: ${snapshot.metadata?.name}")
//                Text(text = "Progress: ${progress.value.toInt()}%")
//                if (snapshot.task.isInProgress) {
//                    CircularProgressIndicator()
//                }
//            }
//        },
//        buttons = {}
//    )
    Text(text = "gergegregreeregerg")
}
