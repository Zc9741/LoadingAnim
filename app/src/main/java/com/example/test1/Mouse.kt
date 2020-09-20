package com.example.test1

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View

import androidx.annotation.RequiresApi
import java.util.jar.Attributes

class Mouse: View {
    //小球的半径
    private var ballRadius = 0f
    //两个球之间的间距
    private var space = 0f
    //嘴巴的半径
    private var mouseRadius = 0f
    //嘴张开的角度
    private var mouseAngle=0f
    //张嘴动画对象
    private var mouseMoveAnim:ValueAnimator?=null
    //小球移动动画对象
    private var ballMoveAnim:ValueAnimator?=null
    //小球移动的位置
    private var ballTranslate:Float=0f

    private val animotors=AnimatorSet()

    //大圆的中心点坐标
    private var cx = 0f
    private var cy = 0f
    //圆弧的画笔
    private val mPaint:Paint by lazy {
        Paint().apply {

            color = Color.MAGENTA
            style = Paint.Style.FILL
        }
    }


    //代码创建
    constructor(context: Context):super(context){}
    //xml创建
    constructor(context: Context,attrs: AttributeSet?):super(context,attrs){}

    //绘制圆形
    override fun onDraw(canvas: Canvas?) {
        //绘制大圆（嘴）
        canvas?.drawArc(
            cx-mouseRadius,cy-mouseRadius,cx+mouseRadius,cy+mouseRadius,
            mouseAngle,360f-mouseAngle*2f,
            true,
            mPaint
        )
        //绘制小圆
        canvas?.drawCircle(cx+4.5f*ballRadius-ballTranslate,cy,ballRadius,mPaint)
    }

    //创建张嘴动画
    fun createMouseAnim(){
        if(mouseMoveAnim==null){
        mouseMoveAnim=ValueAnimator.ofFloat(0f,45f,0f).apply {
            repeatCount=ValueAnimator.INFINITE
            duration=800
                addUpdateListener {
                    mouseAngle=it.animatedValue as Float
                    //重新绘制图像
                    invalidate()

                }
            }
        }
    }




    //创建小圆移动动画
    fun createBallAnim(){
        if (ballMoveAnim==null){
            ballMoveAnim=ValueAnimator.ofFloat(0f,4.5f*ballRadius).apply {
                duration=800
                repeatCount=ValueAnimator.INFINITE
                addUpdateListener {
                    ballTranslate=it.animatedValue as Float
                    //重新绘制图像
                    invalidate()
                }
            }
        }
    }

    //启动动画
    fun animotorStart(){
        createMouseAnim()
        createBallAnim()
        animotors.playTogether(mouseMoveAnim,ballMoveAnim)
        if(animotors.isPaused){
            animotors.resume()
        }else{
            animotors.start()
        }
    }
    //暂停动画
    fun animotorStop(){
        animotors.pause()
    }

    //初始化ballRadius（半径）、mouseRaius（半径）、大圆的中心点
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //确定小球的半径
        if (width >= height){
            //高度顶满
            ballRadius = height / 6f
            //判断宽度能否容纳8.5个R
            if (8.5*ballRadius > width){
                ballRadius = width/8.5f
            }
        }else{
            //宽度顶满
            ballRadius = width/8.5f
            //判断高度是否能够容纳6个R
            if(6*ballRadius > height){
                ballRadius = height/6f
            }
        }

        mouseRadius = 3*ballRadius
        space = 0.5f*ballRadius

        //确定大圆中心点坐标
        cx = ((width - 8.5*ballRadius)/2f + 3*ballRadius).toFloat()
        cy = height/2f
    }



}

/*class Mouse() : View {
    //绘制画笔
    private val mPaint: Paint by lazy {
        Paint().apply {
            color=Color.MAGENTA
            style=Paint.Style.FILL
        }
    }
    constructor(context: Context): super(context){

    }

    //xml创建
    //constructor(context: Context,attrs:AttributeSet?):super(context){}
    constructor(context: Context,attrs: AttributeSet?): super(context,attrs){}

    //@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        //绘制圆弧
        canvas?.drawArc(
            0f,0f,width.toFloat(),height.toFloat(),
            90f,180f,
            true,
            mPaint
        )
    }

}*/

