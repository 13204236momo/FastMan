package com.tianshang.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

public class BigImageView extends View implements GestureDetector.OnGestureListener, View.OnTouchListener, GestureDetector.OnDoubleTapListener {
    private final Rect mRect;
    private final BitmapFactory.Options mOptions;
    private final GestureDetector mGestureDetector;
    private final Scroller mScroller;
    private final ScaleGestureDetector mScaleGestureDetector;
    private int mImageWidth;
    private int mImageHeight;
    private BitmapRegionDecoder mDecoder;
    private int mViewWidth;
    private int mViewHeight;
    private float mScale;
    private Bitmap mBitmap;
    private float originalScale;

    public BigImageView(Context context) {
        this(context, null);
    }

    public BigImageView(Context context, @androidx.annotation.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigImageView(Context context, @androidx.annotation.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //1.第一步：设置BigImageView需要的成员变量
        mRect = new Rect();
        //内存复用
        mOptions = new BitmapFactory.Options();
        //手势识别
        mGestureDetector = new GestureDetector(context, this);
        //滚动类
        mScroller = new Scroller(context);
        //缩放手势识别
        mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGesture());
        setOnTouchListener(this);

    }

    public void setImage(InputStream inputStream) {
        //获取图片的信息，不能将整张图片加载进内存
        mOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, mOptions);
        mImageWidth = mOptions.outWidth;
        mImageHeight = mOptions.outHeight;
        //开启复用
        mOptions.inMutable = true;
        //设置格式
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        mOptions.inJustDecodeBounds = false;
        //创建区域解码器
        try {
            mDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestLayout();
    }

    /**
     * 第三步，测量
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();


//        //确定加载图片的区域
////        mRect.left = 0;
////        mRect.top =0;
////        mRect.right = mImageWidth;
////        //得到图片的宽度，就能根据view的宽度计算缩放因子
////        mScale = mViewWidth/(float)mImageWidth;
////        mRect.bottom = (int)(mViewHeight/mScale);

        //加了缩放手势之后
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = Math.min(mImageWidth, mViewWidth);
        mRect.bottom = Math.min(mImageHeight, mViewHeight);

        originalScale = mViewWidth / (float) mImageWidth;
        mScale = originalScale;

    }

    /**
     * 4.画出具体内容
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDecoder == null) {
            return;
        }
        //内存复用,复用bitmap必须跟即将解码的bitmap尺寸一样
        mOptions.inBitmap = mBitmap;
        mBitmap = mDecoder.decodeRegion(mRect, mOptions);
        Matrix matrix = new Matrix();
        matrix.setScale(mViewHeight / (float) mRect.width(), mViewHeight / (float) mRect.width());
        canvas.drawBitmap(mBitmap, matrix, null);
    }

    /**
     * 6.手按下去事件处理
     *
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        //如果滑动有停止，强行停止
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    /**
     * 滑动处理
     *
     * @param motionEvent  开始事件，手指按下获取坐标
     * @param motionEvent1 当前事件
     * @param x            x轴移动距离
     * @param y            y轴移动距离
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float x, float y) {
        //上下移动时mRect需要改变显示区域
        mRect.offset(0, (int) y);
        //移动时处理到达顶部底部情况
        if (mRect.bottom > mImageHeight) {
            mRect.bottom = mImageHeight;
            mRect.top = mImageHeight - (int) (mViewHeight / mScale);
        }
        if (mRect.top < 0) {
            mRect.bottom = (int) (mViewHeight / mScale);
            mRect.top = 0;
        }

        if (mRect.right > mImageWidth) {
            mRect.right = mImageWidth;
            mRect.left = mImageWidth - (int) (mViewHeight / mScale);
        }

        if (mRect.left < 0) {
            mRect.right = (int) (mViewHeight / mScale);
            mRect.left = 0;
        }


        invalidate();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    /**
     * 8.处理惯性问题
     *
     * @param motionEvent
     * @param motionEvent1
     * @param vx
     * @param vy
     * @return
     */
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float vx, float vy) {
        mScroller.fling(mRect.left, mRect.top, (int) -vx, (int) -vy, 0,
                mImageWidth, 0, mImageHeight - (int) (mViewHeight / mScale));
        return false;
    }

    /**
     * 5.处理点击事件
     *
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //将事件交给手势处理
        mGestureDetector.onTouchEvent(motionEvent);
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;

    }


    /**
     * 处理结果
     */
    @Override
    public void computeScroll() {
        if (mScroller.isFinished()) {
            return;
        }
        if (mScroller.computeScrollOffset()) {
            mRect.top = mScroller.getCurrY();
            mRect.bottom = mRect.top + (int) (mViewHeight / mScale);
            invalidate();
        }
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }


    /**
     * 处理缩放的回调事件
     */
    class ScaleGesture extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scale = mScale;
            //减去自身的1，比如缩放了1.5倍，缩放因子是1.5 - 1 = 0.5
            scale += detector.getScaleFactor() - 1;

            if (scale <= originalScale) {
                scale = originalScale;
            } else if (scale > originalScale * 5) {
                //最大放大5倍
                scale = originalScale * 5;
            }
            mRect.right = mRect.left + (int)(mViewWidth/scale);
            mRect.bottom = mRect.top + (int)(mViewHeight/scale);
            mScale = scale;
            invalidate();
            return true;
        }
    }
}
