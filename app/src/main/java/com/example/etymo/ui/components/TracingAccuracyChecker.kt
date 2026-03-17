package com.example.etymo.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.ui.geometry.Offset

/**
 * Compares user-drawn strokes against a reference character rendered to a bitmap.
 * Returns a score from 0f to 1f indicating tracing accuracy.
 *
 * Strategy:
 * 1. Render the reference character into a bitmap at the given canvas size.
 * 2. Render the user strokes into another bitmap of the same size.
 * 3. Count how many "ink" pixels in the reference bitmap are covered by user strokes
 *    (overlap), and how many user pixels fall outside the reference area (overshoot).
 * 4. Compute a combined score from coverage and precision.
 */
object TracingAccuracyChecker {

    private const val BITMAP_SIZE = 200 // px – small for performance
    private const val INK_THRESHOLD = 50 // alpha threshold for "ink" pixel

    /**
     * @param referenceChar  The character to trace
     * @param strokes        The user's drawn strokes (in canvas pixel coordinates)
     * @param canvasWidth    The actual canvas width in pixels
     * @param canvasHeight   The actual canvas height in pixels
     * @return a score between 0f and 1f, where 1f is perfect
     */
    fun evaluate(
        referenceChar: String,
        strokes: List<List<Offset>>,
        canvasWidth: Float,
        canvasHeight: Float
    ): Float {
        if (strokes.isEmpty() || referenceChar.isEmpty()) return 0f

        val w = BITMAP_SIZE
        val h = BITMAP_SIZE

        // Scale factors from canvas coords to bitmap coords
        val sx = w.toFloat() / canvasWidth
        val sy = h.toFloat() / canvasHeight

        // --- Render reference character ---
        val refBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val refCanvas = Canvas(refBitmap)
        val textPaint = Paint().apply {
            color = android.graphics.Color.BLACK
            textSize = w * 0.75f
            isAntiAlias = true
            typeface = Typeface.DEFAULT
            textAlign = Paint.Align.CENTER
            style = Paint.Style.FILL_AND_STROKE
            strokeWidth = 30f // Thick enough to be forgiving, thin enough to require intent
            strokeJoin = Paint.Join.ROUND
        }
        val textBounds = android.graphics.Rect()
        textPaint.getTextBounds(referenceChar, 0, referenceChar.length, textBounds)
        val textX = w / 2f
        val textY = h / 2f - (textBounds.top + textBounds.bottom) / 2f
        refCanvas.drawText(referenceChar, textX, textY, textPaint)

        // --- Render user strokes ---
        val userBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val userCanvas = Canvas(userBitmap)
        val strokePaint = Paint().apply {
            color = android.graphics.Color.BLACK
            strokeWidth = 6f * sx  // scale stroke width
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }

        for (points in strokes) {
            if (points.size >= 2) {
                val path = android.graphics.Path()
                path.moveTo(points[0].x * sx, points[0].y * sy)
                for (i in 1 until points.size) {
                    path.lineTo(points[i].x * sx, points[i].y * sy)
                }
                userCanvas.drawPath(path, strokePaint)
            } else if (points.size == 1) {
                userCanvas.drawCircle(
                    points[0].x * sx,
                    points[0].y * sy,
                    3f * sx,
                    strokePaint.apply { style = Paint.Style.FILL }
                )
                strokePaint.style = Paint.Style.STROKE
            }
        }

        // --- Compare pixels ---
        var refPixelCount = 0
        var overlapCount = 0
        var userPixelCount = 0
        var overshootCount = 0

        for (py in 0 until h) {
            for (px in 0 until w) {
                val refAlpha = android.graphics.Color.alpha(refBitmap.getPixel(px, py))
                val userAlpha = android.graphics.Color.alpha(userBitmap.getPixel(px, py))
                val isRefInk = refAlpha > INK_THRESHOLD
                val isUserInk = userAlpha > INK_THRESHOLD

                if (isRefInk) refPixelCount++
                if (isUserInk) userPixelCount++
                if (isRefInk && isUserInk) overlapCount++
                if (isUserInk && !isRefInk) overshootCount++
            }
        }

        refBitmap.recycle()
        userBitmap.recycle()

        if (refPixelCount == 0) return 0f
        if (userPixelCount == 0) return 0f

        // Coverage = how much of the reference was traced
        val coverage = overlapCount.toFloat() / refPixelCount.toFloat()
        // Precision = how much of the user strokes fall on the reference
        val precision = if (userPixelCount > 0)
            overlapCount.toFloat() / userPixelCount.toFloat()
        else 0f

        // Combined F1-like score
        return if (coverage + precision > 0f)
            2f * coverage * precision / (coverage + precision)
        else 0f
    }

    /**
     * @return true if the tracing accuracy is above the threshold (default 25%)
     */
    fun isAccurate(
        referenceChar: String,
        strokes: List<List<Offset>>,
        canvasWidth: Float,
        canvasHeight: Float,
        threshold: Float = 0.25f
    ): Boolean {
        return evaluate(referenceChar, strokes, canvasWidth, canvasHeight) >= threshold
    }
}
