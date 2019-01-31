package nl.entreco.giddyapp.libcropper.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import nl.entreco.giddyapp.libcropper.R

internal object PaintUtil {

    fun newBorderPaint(context: Context, resources: Resources) : Paint {
        return Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = resources.getDimension(R.dimen.border_thickness)
            color = resources.getColor(R.color.guideline, context.theme)
        }
    }

    fun newGuidelinePaint(context: Context, resources: Resources) : Paint {
        return Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = resources.getDimension(R.dimen.guideline_thickness)
            color = resources.getColor(R.color.guideline, context.theme)
        }
    }

    fun newSurroundingAreaOverlayPaint(context: Context, resources: Resources) : Paint {
        return Paint().apply {
            style = Paint.Style.FILL
            color = resources.getColor(R.color.surrounding_area, context.theme)
        }
    }

    fun newCornerPaint(context: Context, resources: Resources) : Paint {
        return Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = resources.getDimension(R.dimen.corner_thickness)
            color = resources.getColor(R.color.corner, context.theme)
        }
    }
}