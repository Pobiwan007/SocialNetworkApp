package com.social2023Network.domain.transformations

import androidx.compose.runtime.Stable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

//fun interface VisualTransformation {
//
//    fun filter(text: AnnotatedString): TransformedText
//
//    companion object {
//
//        @Stable
//        val None: VisualTransformation = VisualTransformation { text ->
//            TransformedText(text, OffsetMapping.Identity)
//        }
//    }
//}