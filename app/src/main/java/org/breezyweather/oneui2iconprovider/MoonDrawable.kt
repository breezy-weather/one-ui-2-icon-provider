/**
 * This file is part of Breezy Weather One UI 2 Icon Provider.
 *
 * Breezy Weather One UI 2 Icon Provider is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, version 3 of the License.
 *
 * Breezy Weather One UI 2 Icon Provider is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Breezy Weather One UI 2 Icon Provider. If not, see <https://www.gnu.org/licenses/>.
 */

package org.breezyweather.oneui2iconprovider

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.Xfermode
import android.graphics.drawable.Drawable
import kotlin.math.min
import kotlin.math.sin

class MoonDrawable : Drawable() {
    private val mPaint = Paint().apply {
        isAntiAlias = true
    }
    private val mClearXfermode: Xfermode

    private val mCoreColor: Int = Color.rgb(255, 199, 42)
    private var mAlpha: Float = 1f
    private var mBounds: Rect
    private var mCoreRadius = 0f
    private var mCoreCenterX = 0f
    private var mCoreCenterY = 0f
    private var mShaderRadius = 0f
    private var mShaderCenterX = 0f
    private var mShaderCenterY = 0f

    init {
        mClearXfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        mBounds = bounds
        ensurePosition(mBounds)
    }

    private fun ensurePosition(bounds: Rect) {
        val boundSize = min(bounds.width(), bounds.height())
        val rect2 = Rect(
            bounds.left + (bounds.width() - boundSize) / 2,
            bounds.top + (bounds.height() - boundSize) / 2,
            bounds.right - (bounds.width() - boundSize) / 2,
            bounds.bottom - (bounds.height() - boundSize) / 2
        )
        mCoreRadius = 0.3164f * boundSize
        mCoreCenterX = (rect2.width() / 2.0f) + rect2.left
        mCoreCenterY = (rect2.height() / 2.0f) + rect2.top
        mShaderRadius = mCoreRadius
        mShaderCenterX = (0.6796f * boundSize) + rect2.left
        mShaderCenterY = (boundSize * 0.3203f) + rect2.top
    }

    override fun onBoundsChange(bounds: Rect) {
        mBounds = bounds
        ensurePosition(bounds)
    }

    override fun draw(canvas: Canvas) {
        mPaint.alpha = (mAlpha * 255).toInt()
        val layerId = canvas.saveLayer(
            mBounds.left.toFloat(),
            mBounds.top.toFloat(),
            mBounds.right.toFloat(),
            mBounds.bottom.toFloat(),
            null,
            Canvas.ALL_SAVE_FLAG
        )
        mPaint.color = mCoreColor
        canvas.drawCircle(mCoreCenterX, mCoreCenterY, mCoreRadius, mPaint)
        mPaint.setXfermode(mClearXfermode)
        canvas.drawCircle(mShaderCenterX, mShaderCenterY, mShaderRadius, mPaint)
        mPaint.setXfermode(null)
        canvas.restoreToCount(layerId)
    }

    override fun setAlpha(alpha: Int) {
        mAlpha = alpha.toFloat()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.setColorFilter(colorFilter)
    }

    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun getIntrinsicWidth(): Int {
        return mBounds.width()
    }

    override fun getIntrinsicHeight(): Int {
        return mBounds.height()
    }
}
