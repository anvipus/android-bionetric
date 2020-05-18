package com.an.biometric

import android.annotation.TargetApi
import android.content.Context
import android.content.DialogInterface
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.CancellationSignal

class BiometricManager protected constructor(biometricBuilder: BiometricBuilder) : BiometricManagerV23() {
    protected var mCancellationSignal = CancellationSignal()
    fun authenticate(biometricCallback: BiometricCallback) {
        if (title == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog title cannot be null")
            return
        }
        if (subtitle == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog subtitle cannot be null")
            return
        }
        if (description == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog description cannot be null")
            return
        }
        if (negativeButtonText == null) {
            biometricCallback.onBiometricAuthenticationInternalError("Biometric Dialog negative button text cannot be null")
            return
        }
        if (!BiometricUtils.isSdkVersionSupported) {
            biometricCallback.onSdkVersionNotSupported()
            return
        }
        if (!BiometricUtils.isPermissionGranted(mContext)) {
            biometricCallback.onBiometricAuthenticationPermissionNotGranted()
            return
        }
        if (!BiometricUtils.isHardwareSupported(mContext)) {
            biometricCallback.onBiometricAuthenticationNotSupported()
            return
        }
        if (!BiometricUtils.isFingerprintAvailable(mContext)) {
            biometricCallback.onBiometricAuthenticationNotAvailable()
            return
        }
        displayBiometricDialog(biometricCallback)
    }

    fun cancelAuthentication() {
        if (BiometricUtils.isBiometricPromptEnabled) {
            if (!mCancellationSignal.isCanceled) mCancellationSignal.cancel()
        } else {
            if (!mCancellationSignalV23.isCanceled) mCancellationSignalV23.cancel()
        }
    }

    private fun displayBiometricDialog(biometricCallback: BiometricCallback) {
        if (BiometricUtils.isBiometricPromptEnabled) {
            displayBiometricPrompt(biometricCallback)
        } else {
            displayBiometricPromptV23(biometricCallback)
        }
    }

    @TargetApi(Build.VERSION_CODES.P)
    private fun displayBiometricPrompt(biometricCallback: BiometricCallback) {
        BiometricPrompt.Builder(mContext)
                .setTitle(title)
                .setSubtitle(subtitle)
                .setDescription(description)
                .setNegativeButton(negativeButtonText, mContext!!.mainExecutor, DialogInterface.OnClickListener { dialogInterface, i -> biometricCallback.onAuthenticationCancelled() })
                .build()
                .authenticate(mCancellationSignal, mContext!!.mainExecutor,
                        BiometricCallbackV28(biometricCallback))
    }

    class BiometricBuilder(val context: Context) {
        var mTitle: String? = null
        var mSubtitle: String? = null
        var mDescription: String? = null
        var mNegativeButtonText: String? = null
        fun setTitle(title: String): BiometricBuilder {
            this.mTitle = title
            return this
        }

        fun setSubtitle(subtitle: String): BiometricBuilder {
            this.mSubtitle = subtitle
            return this
        }

        fun setDescription(description: String): BiometricBuilder {
            this.mDescription = description
            return this
        }

        fun setNegativeButtonText(negativeButtonText: String): BiometricBuilder {
            this.mNegativeButtonText = negativeButtonText
            return this
        }

        fun build(): BiometricManager {
            return BiometricManager(this)
        }

    }

    init {
        mContext = biometricBuilder.context
        title = biometricBuilder.mTitle
        subtitle = biometricBuilder.mSubtitle
        description = biometricBuilder.mDescription
        negativeButtonText = biometricBuilder.mNegativeButtonText
    }
}