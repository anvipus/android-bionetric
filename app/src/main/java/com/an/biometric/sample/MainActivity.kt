package com.an.biometric.sample

import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import com.an.biometric.BiometricCallback
import com.an.biometric.BiometricManager
import com.an.biometric.BiometricManager.BiometricBuilder

//import androidx.biometric.BiometricPrompt;
class MainActivity : AppCompatActivity(), BiometricCallback {
    private var button: Button? = null
    private var textView: TextView? = null
    var mBiometricManager: BiometricManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.btn_authenticate)
        textView = findViewById(R.id.textView)
        button!!.setOnClickListener(View.OnClickListener { /*
                 *
                 * */
            mBiometricManager = BiometricBuilder(this@MainActivity)
                    .setTitle(getString(R.string.biometric_title))
                    .setSubtitle(getString(R.string.biometric_subtitle))
                    .setDescription(getString(R.string.biometric_description))
                    .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                    .build()

            //start authentication
            mBiometricManager!!.authenticate(this@MainActivity)
        })
        try {
            var tes = ""
            tes = """
                ${tes}BOARD :${Build.BOARD},

                """.trimIndent()
            tes = """
                ${tes}BRAND :${Build.BRAND},

                """.trimIndent()
            tes = """
                ${tes}DEVICE :${Build.DEVICE},

                """.trimIndent()
            tes = """
                ${tes}DISPLAY :${Build.DISPLAY},

                """.trimIndent()
            tes = """
                ${tes}HOST :${Build.HOST},

                """.trimIndent()
            tes = """
                ${tes}ID :${Build.ID},

                """.trimIndent()
            tes = """
                ${tes}MANUFAKTUR :${Build.MANUFACTURER},

                """.trimIndent()
            tes = """
                ${tes}MODEL :${Build.MODEL},

                """.trimIndent()
            tes = """
                ${tes}PRODUCT :${Build.PRODUCT},

                """.trimIndent()
            tes = """
                ${tes}TAG :${Build.TAGS},

                """.trimIndent()
            tes = """
                ${tes}TYPE :${Build.TYPE},

                """.trimIndent()
            tes = """
                ${tes}USER :${Build.USER},

                """.trimIndent()
            tes = """
                ${tes}RADIO :${Build.getRadioVersion()},

                """.trimIndent()
            tes = """
                ${tes}FINGERPRINT :${Build.FINGERPRINT},

                """.trimIndent()
            val androidId = Settings.Secure.getString(contentResolver,
                    Settings.Secure.ANDROID_ID)
            tes = """
                ${tes}ANDROID_ID :$androidId,

                """.trimIndent()
            textView!!.setText(tes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSdkVersionNotSupported() {
        Toast.makeText(applicationContext, getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationNotSupported() {
        Toast.makeText(applicationContext, getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationNotAvailable() {
        Toast.makeText(applicationContext, getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(applicationContext, getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationInternalError(error: String?) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationFailed() {
        Toast.makeText(applicationContext, getString(R.string.biometric_failure), Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationCancelled() {
        Toast.makeText(applicationContext, getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show()
        mBiometricManager!!.cancelAuthentication()
    }

    override fun onAuthenticationSuccessfulV28(result: BiometricPrompt.AuthenticationResult?) {
        Toast.makeText(applicationContext, getString(R.string.biometric_success), Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationSuccessfulV23(result: FingerprintManagerCompat.AuthenticationResult?) {
        Toast.makeText(applicationContext, getString(R.string.biometric_success), Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        Toast.makeText(applicationContext, helpString, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        Toast.makeText(applicationContext, errString, Toast.LENGTH_LONG).show()
    }
}