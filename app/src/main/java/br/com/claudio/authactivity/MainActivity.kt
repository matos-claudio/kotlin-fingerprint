package br.com.claudio.authactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import java.util.concurrent.Executor

class MainActivity : BaseActivity() {

    private val executor = Executor { }
    lateinit var btLoginFingerPrint: Button
    lateinit var btLoginUserAndPass: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen()
        btLoginUserAndPass = findViewById(R.id.btLoginUserAndPass)
        btLoginFingerPrint = findViewById(R.id.btLoginFingerPrint)

        btLoginFingerPrint.setOnClickListener { checkBiometricReader() }
    }

    fun checkBiometricReader() {
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                showBiometricPrompt()
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Toast.makeText(applicationContext,
                    "Não há recursos biométricos disponíveis neste dispositivo.", Toast.LENGTH_SHORT).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Toast.makeText(applicationContext,
                    "Os recursos biométricos não estão disponíveis no momento.", Toast.LENGTH_SHORT).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
            Toast.makeText(applicationContext,
                "O usuário não associou credenciais biométricas à sua conta.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showBiometricPrompt() {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login com biometria")
                .setNegativeButtonText("Cancelar")
                .build()

        val biometricPrompt = BiometricPrompt(this, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int,
                                                       errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(baseContext,
                                "Authentication error: $errString", Toast.LENGTH_SHORT)
                                .show()
                    }

                    override fun onAuthenticationSucceeded(
                            result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
//                        val authenticatedCryptoObject: BiometricPrompt.CryptoObject =
//                                result.getCryptoObject()!!

                        //Autenticou a biometria
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(applicationContext, "Erro na autenticação",
                                Toast.LENGTH_SHORT)
                                .show()
                    }
                })
        biometricPrompt.authenticate(promptInfo)
    }
}
