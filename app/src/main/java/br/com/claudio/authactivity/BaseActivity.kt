package br.com.claudio.authactivity

import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    fun setFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
    }
}