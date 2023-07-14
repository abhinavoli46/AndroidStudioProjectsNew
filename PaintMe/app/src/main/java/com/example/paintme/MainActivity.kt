package com.example.paintme

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class MainActivity : AppCompatActivity()
{

    private var drawingView : DrawingView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawingView = findViewById(R.id.drawingView)
        drawingView?.setBrushSize(20.toFloat())
        val brush : ImageButton = findViewById(R.id.brush_picker_Button)
        brush.setOnClickListener()
        {
            brushSizeChooserDialog()
        }
    }

    private fun brushSizeChooserDialog()
    {
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_button_brush_size)
        brushDialog.setTitle("Size")
        val smallButton : ImageButton = brushDialog.findViewById(R.id.small_brush)
        val mediumButton : ImageButton = brushDialog.findViewById(R.id.medium_brush)
        val largeButton : ImageButton = brushDialog.findViewById(R.id.large_brush)

        smallButton.setOnClickListener()
        {
            drawingView?.setBrushSize(10.toFloat())
            brushDialog.dismiss()
        }
        mediumButton.setOnClickListener()
        {
            drawingView?.setBrushSize(20.toFloat())
            brushDialog.dismiss()
        }
        largeButton.setOnClickListener()
        {
            drawingView?.setBrushSize(30.toFloat())
            brushDialog.dismiss()
        }

        brushDialog.show()
    }
}