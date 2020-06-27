package com.an.biometric.sample

//import android.hardware.biometrics.BiometricPrompt
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
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
            var totalCamera = 0
            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                totalCamera = Camera.getNumberOfCameras()
            }

            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels + getNavigationBarHeight()
            val width = displayMetrics.widthPixels

            var tes = ""
            tes = """
                ${tes}BOARD :${Build.BOARD},

                """.trimIndent()
            tes = """
                ${tes}BRAND :${Build.BRAND},

                """.trimIndent()
            tes = """
                ${tes}VERSION CODE :${BuildConfig.VERSION_CODE},

                """.trimIndent()
            tes = """
                ${tes}VERSION NAME :${BuildConfig.VERSION_NAME},

                """.trimIndent()
            tes = """
                ${tes}ANDROID SDK :${Build.VERSION.SDK_INT},

                """.trimIndent()
            tes = """
                ${tes}ANDROID VERSION NAME :${Build.VERSION.RELEASE},

                """.trimIndent()
            tes = """
                ${tes}ANDROID INCREMENTAL :${Build.VERSION.INCREMENTAL},

                """.trimIndent()
            tes = """
                ${tes}ANDROID DEVICE HEIGHT :${height},

                """.trimIndent()
            tes = """
                ${tes}ANDROID DEVICE WIDHT :${width},

                """.trimIndent()
            tes = """
                ${tes}ANDROID TOTAL CAMERA :${totalCamera},

                """.trimIndent()

//            tes = """
//                ${tes}ANDROID CAMERA RESOLUTION HEIGHT:${cam_height},
//
//                """.trimIndent()
//            tes = """
//                ${tes}ANDROID CAMERA RESOLUTION WIDTH:${cam_width},
//
//                """.trimIndent()
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

    private fun getNavigationBarHeight(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            val usableHeight = metrics.heightPixels
            windowManager.defaultDisplay.getRealMetrics(metrics)
            val realHeight = metrics.heightPixels
            return if (realHeight > usableHeight) realHeight - usableHeight else 0
        }
        return 0
    }

    fun getCameraInstance(): Camera? {
        val camNum: Int
        camNum = Camera.getNumberOfCameras() ///////error here///////////
        val myTextView: TextView? = null
        myTextView!!.text = "There are $camNum cameras availabe"
        var c: Camera? = null
        try {
            c = Camera.open() // attempt to get a Camera instance
        } catch (e: java.lang.Exception) {
            // Camera is not available (in use or does not exist)
        }
        return c // returns null if camera is unavailable
    }

    fun getBackCameraResolutionInMp(): Float {
        val noOfCameras = Camera.getNumberOfCameras()
        var maxResolution = -1f
        var pixelCount: Long = -1
        for (i in 0 until noOfCameras) {
            val cameraInfo = CameraInfo()
            Camera.getCameraInfo(i, cameraInfo)
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                val camera = Camera.open(i)
                val cameraParams = camera.parameters
                for (j in cameraParams.supportedPictureSizes.indices) {
                    val pixelCountTemp = cameraParams.supportedPictureSizes[j].width * cameraParams.supportedPictureSizes[j].height.toLong() // Just changed i to j in this loop
                    if (pixelCountTemp > pixelCount) {
                        pixelCount = pixelCountTemp
                        maxResolution = pixelCountTemp.toFloat() / 1024000.0f
                    }
                }
                camera.release()
            }
        }
        return maxResolution
    }
}