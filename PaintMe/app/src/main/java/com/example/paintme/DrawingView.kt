package com.example.paintme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context : Context, attrs : AttributeSet): View(context,attrs)
{
    //A surface to draw
    private var canvas : Canvas? = null
    //A map to hold pixels together
    private var CanvasBitmap : Bitmap? = null
    //Help to draw various type of paths
    private var DrawingPath : CustomPath? = null
    //Defines brush size
    private var BrushSize : Float = 0.toFloat()
    //Defines the color
    private var color = Color.BLACK
    //Hold the info about style and color info about how to draw geometry, text and bitmap
    private var DrawPaint: Paint? = null
    private var CanvasPaint: Paint? = null

    private var paths = ArrayList<CustomPath>()

    init
    {
        setUpDrawing()
    }
    private fun setUpDrawing()
    {
        DrawPaint = Paint()
        DrawingPath = CustomPath(color,BrushSize)
        DrawPaint!!.color = color
        DrawPaint!!.style = Paint.Style.STROKE
        DrawPaint!!.strokeJoin = Paint.Join.ROUND
        DrawPaint!!.strokeCap = Paint.Cap.ROUND
        CanvasPaint = Paint(Paint.DITHER_FLAG)
        //Already defined in main activity
        //BrushSize = 20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        CanvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        canvas = Canvas(CanvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(CanvasBitmap!!,0f,0f,CanvasPaint)

        for (path in paths)
        {
            DrawPaint!!.strokeWidth = path.brushThickness
            DrawPaint!!.color = path.color
            canvas.drawPath(path,DrawPaint!!)
        }
        if(!DrawingPath!!.isEmpty)
        {
            //Set up stroke size
            DrawPaint!!.strokeWidth = DrawingPath!!.brushThickness
            //Set up the path
            canvas.drawPath(DrawingPath!!,DrawPaint!!)
            //Set up the color
            DrawPaint!!.color = DrawingPath!!.color

        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //Store the x and y coordinates where a touch occured
        val touchX = event?.x;
        val touchY = event?.y

        when(event?.action)
        {
            //When you press on the screen for first time
            MotionEvent.ACTION_DOWN -> {
                DrawingPath!!.color = color
                DrawingPath!!.brushThickness = BrushSize

                DrawingPath!!.reset()
                DrawingPath!!.moveTo(touchX!!,touchY!!)
            }
            //When you drag over the screen
            MotionEvent.ACTION_MOVE -> {
                //Add a line from the last point to the specified point (x,y).
                DrawingPath!!.lineTo(touchX!!,touchY!!)
            }
            //When you release the screen
            MotionEvent.ACTION_UP ->{
                //Save the path in arrayList
                paths.add(DrawingPath!!)
                DrawingPath = CustomPath(color, BrushSize)
            }
            //For any other motion event return false i.e dont do anything
            else -> {
                return false
            }
        }
        //Please remember that drawing on the screen is frequent process,whenever you update a view,
        // that change should be propagated and redrawn to notify such change right.
        // invalidate() is a trigger method,that signals force reDrawing of any view you wish to show changes for.
        invalidate()
        return true
    }
    fun setBrushSize(newSize:Float)
    {
        //applyDimension is used to adjust the brush size according to size we have passed
        //+ keeping in mind the dimensions of screen available to us
        BrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                            newSize,resources.displayMetrics)
        DrawPaint!!.strokeWidth = BrushSize
    }

    internal inner class CustomPath(var color : Int, var brushThickness : Float) : Path()
    {

    }
}