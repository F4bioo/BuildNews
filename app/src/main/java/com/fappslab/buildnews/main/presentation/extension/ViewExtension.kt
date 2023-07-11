package com.fappslab.buildnews.main.presentation.extension

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.fappslab.buildnews.main.presentation.MainActivity

fun MainActivity.biometricListeners(
    errorAction: (Int) -> Unit, successAction: () -> Unit
): BiometricPrompt {
    val executor = ContextCompat.getMainExecutor(this)
    return BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            errorAction(errorCode)
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            successAction()
        }
    })
}

fun MainActivity.checkDeviceHasBiometric(
    cantAuthenticateAction: () -> Unit,
    canAuthenticateAction: () -> Unit
) {
    val biometricManager = BiometricManager.from(this)
    when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
        BiometricManager.BIOMETRIC_SUCCESS -> canAuthenticateAction()
        else -> cantAuthenticateAction()
    }
}
