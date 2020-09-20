package com.example.test1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //添加动画启动点击事件
        mStart.setOnClickListener {
            mouse.animotorStart()
            mPulse.animStart()
        }
        //添加动画暂停点击事件
        mStop.setOnClickListener {
            mouse.animotorStop()
            mPulse.animPause()
        }
    }

}