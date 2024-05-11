package com.cliff.conch.tools

import android.graphics.Bitmap
import android.graphics.Canvas
import android.webkit.WebView

object ScreenShooter {
    private fun takeScreenshotOfWebView(webView: WebView): Bitmap {
        val picture = webView.capturePicture()
        val bitmap = Bitmap.createBitmap(picture.width, picture.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        picture.draw(canvas)
        return bitmap
    }

    fun takeFullPageScreenshotOfWebView(webView: WebView): Bitmap {
        val totalHeight = webView.contentHeight
        val bitmap = Bitmap.createBitmap(webView.width, totalHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        var y = 0
        while (y < totalHeight) {
            webView.scrollTo(0, y)
            val screenshot = takeScreenshotOfWebView(webView)
            canvas.drawBitmap(screenshot, 0f, y.toFloat(), null)
            y += screenshot.height
        }
        return bitmap
    }
}