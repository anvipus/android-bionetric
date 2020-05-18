package com.an.biometric

import android.hardware.biometrics.BiometricPrompt
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

interface BiometricCallback {
    fun onSdkVersionNotSupported()
    fun onBiometricAuthenticationNotSupported()
    fun onBiometricAuthenticationNotAvailable()
    fun onBiometricAuthenticationPermissionNotGranted()
    fun onBiometricAuthenticationInternalError(error: String?)
    fun onAuthenticationFailed()
    fun onAuthenticationCancelled()
    fun onAuthenticationSuccessfulV28(result: BiometricPrompt.AuthenticationResult?)
    fun onAuthenticationSuccessfulV23(result: FingerprintManagerCompat.AuthenticationResult?)
    fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?)
    fun onAuthenticationError(errorCode: Int, errString: CharSequence?)
}