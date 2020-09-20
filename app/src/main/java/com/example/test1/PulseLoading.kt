package com.example.test1

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


@SuppressLint("ResourceAsColor")
class PulseLoading : View {
    //代码创建
    constructor(context: Context):super(context){}
    //xml创建
    constructor(context: Context,attrs: AttributeSet?):super(context,attrs){}

    //记录第一个点的中心坐标
    private var cx=0f
    private var cy=0f
    //记录小圆的半径
    private var ballRadius=0f
    //三个小球的变化范围
    private var scales=arrayOf(1f,1f,1f)

    //延迟时间
    private var animDelay=arrayOf(120L,240L,360L)

    private var animators=AnimatorSet()

    //确定圆的半径值  第一个小圆的中心坐标
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if(width>=height){
            ballRadius=height/2f
            if(7f*ballRadius>width){
                ballRadius=width/7f
            }
        }else{
            ballRadius=width/7f
        }

        cx=(width-7f*ballRadius)/2f+ballRadius
        cy=(height-2f*ballRadius)/2f+ballRadius
    }

    //创建画笔
    private val mPaint:Paint by lazy {
        Paint().apply {
            color=R.color.colorGrey
            style=Paint.Style.FILL
        }
    }

    //绘制图像
    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(cx,cy,ballRadius*scales[0],mPaint)
        canvas?.drawCircle(cx+2.5f*ballRadius,cy,ballRadius*scales[1],mPaint)
        canvas?.drawCircle(cx+5f*ballRadius,cy,ballRadius*scales[2],mPaint)
    }

    //用于存储动画的集合
    private var list= mutableListOf<ValueAnimator>()
    fun createBallAnim(){
            for(index in 0..2){
                var anim= ValueAnimator.ofFloat(1f,0.4f,1f).apply {
                    duration=800
                    repeatCount=ValueAnimator.INFINITE
                    startDelay=animDelay[index]
                    addUpdateListener {
                        scales[index]=it.animatedValue as Float
                        //重新绘制图像
                        invalidate()
                    }
                }
                list.add(index,anim)
        }
        for (item in list){
            animators.play(item)
        }
    }



    //开始动画
    fun animStart(){
        createBallAnim()
        if(animators.isPaused){
                animators.resume()
        }else{
            animators.start()
        }
    }

    //暂停动画
    fun animPause(){
        animators.pause()
    }
}