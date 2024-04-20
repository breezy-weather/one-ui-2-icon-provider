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
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import kotlin.math.min


class SunDrawable : Drawable() {
    private val mPaint = Paint().apply {
        isAntiAlias = true
    }

    private val mColor: Int = Color.rgb(255, 198, 42)
    private var mAlpha: Float = 1f
    private var mBounds: Rect
    private var mRadius = 0f
    private var mCX = 0f
    private var mCY = 0f

    private var mHaloHeight = 0f
    private var mHaloMargins = 0f
    private var mHaloRadius = 0f
    private val mHaloRectF = RectF()
    private var mHaloWidth = 0f

    init {
        mBounds = bounds
        ensurePosition(mBounds)
    }

    private fun ensurePosition(bounds: Rect) {
        val boundSize = min(bounds.width(), bounds.height()).toFloat()
        mRadius = 0.2656f * boundSize
        mCX = (1.0 * bounds.width() / 2 + bounds.left).toFloat()
        mCY = (1.0 * bounds.height() / 2 + bounds.top).toFloat()
        mHaloWidth = 0.0468f * boundSize
        mHaloHeight = 0.0898f * boundSize
        mHaloRadius = mHaloWidth / 2.0f
        mHaloMargins = boundSize * 0.0507f
    }

    override fun onBoundsChange(bounds: Rect) {
        mBounds = bounds
        ensurePosition(bounds)
    }

    override fun draw(canvas: Canvas) {
        mPaint.alpha = (mAlpha * 255).toInt()
        mPaint.color = mColor
        for (i in 0..7) {
            val save = canvas.save()
            canvas.rotate(i * 22.5f, mCX, mCY)
            mHaloRectF.set(
                mCX - mHaloWidth / 2.0f,
                mCX - mRadius - mHaloMargins - mHaloHeight,
                mHaloWidth / 2.0f + mCX,
                mCX - mRadius - mHaloMargins
            )
            canvas.drawRoundRect(mHaloRectF, mHaloRadius, mHaloRadius, mPaint)
            mHaloRectF.set(
                mCX - mHaloWidth / 2.0f,
                mCX + mRadius + mHaloMargins,
                mHaloWidth / 2.0f + mCX,
                mCX + mRadius + mHaloMargins + mHaloHeight
            )
            canvas.drawRoundRect(mHaloRectF, mHaloRadius, mHaloRadius, mPaint)
            canvas.restoreToCount(save)
        }
        canvas.drawCircle(mCX, mCY, mRadius, mPaint)
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
