package com.example.etymo.ui.components

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.*

/**
 * Manages sound effects and haptic feedback for the quiz flow.
 *
 * Uses ToneGenerator for sounds (no external audio files needed)
 * and the system Vibrator for haptic feedback.
 */
class FeedbackManager(private val context: Context) {

    private var toneGenerator: ToneGenerator? = null
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    init {
        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 60)
        } catch (_: Exception) {
            // Some devices may fail to create ToneGenerator
        }
    }

    /** Correct answer — cheerful ascending double-beep + short haptic */
    fun playCorrect() {
        scope.launch {
            try {
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 120)
            } catch (_: Exception) {}
        }
        vibrateCorrect()
    }

    /** Wrong answer — descending buzz tone + double-buzz haptic */
    fun playWrong() {
        scope.launch {
            try {
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_NACK, 200)
            } catch (_: Exception) {}
        }
        vibrateWrong()
    }

    /** Light tap — for option selection */
    fun playTap() {
        scope.launch {
            try {
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 40)
            } catch (_: Exception) {}
        }
        vibrateTap()
    }

    /** Quiz complete — celebratory tone + success haptic */
    fun playComplete() {
        scope.launch {
            try {
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 100)
                delay(120)
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 100)
                delay(120)
                toneGenerator?.startTone(ToneGenerator.TONE_PROP_ACK, 200)
            } catch (_: Exception) {}
        }
        vibrateSuccess()
    }

    private fun vibrateCorrect() {
        val vibrator = getVibrator() ?: return
        vibrator.vibrate(
            VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE)
        )
    }

    private fun vibrateWrong() {
        val vibrator = getVibrator() ?: return
        // Double buzz pattern for wrong answer
        vibrator.vibrate(
            VibrationEffect.createWaveform(
                longArrayOf(0, 80, 60, 80),
                intArrayOf(0, 200, 0, 200),
                -1
            )
        )
    }

    private fun vibrateTap() {
        val vibrator = getVibrator() ?: return
        vibrator.vibrate(
            VibrationEffect.createOneShot(15, 80)
        )
    }

    private fun vibrateSuccess() {
        val vibrator = getVibrator() ?: return
        // Celebratory triple-pulse
        vibrator.vibrate(
            VibrationEffect.createWaveform(
                longArrayOf(0, 40, 30, 40, 30, 60),
                intArrayOf(0, 150, 0, 180, 0, 220),
                -1
            )
        )
    }

    private fun getVibrator(): Vibrator? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val mgr = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
            mgr?.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        }
    }

    fun release() {
        scope.cancel()
        toneGenerator?.release()
        toneGenerator = null
    }
}

/**
 * Remember a FeedbackManager that is properly released when leaving composition.
 */
@Composable
fun rememberFeedbackManager(): FeedbackManager {
    val context = LocalContext.current
    val manager = remember { FeedbackManager(context) }
    DisposableEffect(Unit) {
        onDispose { manager.release() }
    }
    return manager
}
