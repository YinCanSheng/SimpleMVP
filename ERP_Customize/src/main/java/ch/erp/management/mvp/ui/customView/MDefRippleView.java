package ch.erp.management.mvp.ui.customView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import ch.erp.management.R;


/**
 * 波纹特效
 *
 * @author ChenHong
 */
public class MDefRippleView extends RelativeLayout {
    private int WIDTH;
    private int HEIGHT;
    private int frameRate = 10;
    private int rippleDuration = 300;
    private int rippleAlpha = 90;
    private Handler canvasHandler;
    private float radiusMax = 0;
    private boolean animationRunning = false;
    private int timer = 0;
    private int timerEmpty = 0;
    private int durationEmpty = -1;
    private float x = -1;
    private float y = -1;
    private int zoomDuration;
    private float zoomScale;
    private ScaleAnimation scaleAnimation;
    private Boolean hasToZoom;
    private Boolean isCentered;
    private Integer rippleType;
    private Paint paint;
    private Bitmap originBitmap;
    private int rippleColor;
    private int ripplePadding;
    private boolean mIsStartRipple = true;
    private GestureDetector gestureDetector;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            /* 当View内容改变通知系统重新绘制 */
            invalidate();
        }
    };

    private OnRippleCompleteListener onCompletionListener;

    public MDefRippleView(Context context) {
        super(context);
    }

    public MDefRippleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MDefRippleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);

    }

    /**
     * Method that initializes all fields and sets listeners
     *
     * @param context Context used to create this view
     * @param attrs   Attribute used to initialize fields
     */
    private void init(final Context context, final AttributeSet attrs) {
        /* 如果在自定义控件的构造函数或者其他绘制相关地方使用系统依赖的代码，会导致可视化编辑器无法报错并提示 */
        if (isInEditMode())
            return;
        /**
         * 获取自定义属性
         */
        final TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.MDefRippleView);
		/* 波纹颜色 */
        rippleColor = typedArray.getColor(R.styleable.MDefRippleView_rv_color,
                getResources().getColor(R.color.darkgrey));
		/**/
        rippleType = typedArray.getInt(R.styleable.MDefRippleView_rv_type, 0);
		/* 是否放大 */
        hasToZoom = typedArray
                .getBoolean(R.styleable.MDefRippleView_rv_zoom, false);
		/* 是否位于中部 */
        isCentered = typedArray.getBoolean(R.styleable.MDefRippleView_rv_centered,
                true);
		/* 时间 */
        rippleDuration = typedArray.getInteger(
                R.styleable.MDefRippleView_rv_rippleDuration, rippleDuration);
		/**/
        frameRate = typedArray.getInteger(R.styleable.MDefRippleView_rv_framerate,
                frameRate);
		/* 透明度 */
        rippleAlpha = typedArray.getInteger(R.styleable.MDefRippleView_rv_alpha,
                rippleAlpha);
		/* 内部边界 */
        ripplePadding = typedArray.getDimensionPixelSize(
                R.styleable.MDefRippleView_rv_ripplePadding, 0);
        canvasHandler = new Handler();
		/* 放大尺寸 */
        zoomScale = typedArray.getFloat(R.styleable.MDefRippleView_rv_zoomScale,
                1.03f);
		/* 放大动画持续时间 */
        zoomDuration = typedArray.getInt(
                R.styleable.MDefRippleView_rv_zoomDuration, 200);
		/* 在调用这个函数后，你就不能再使用这个TypedArray/当recycle被调用后，这就说明这个对象从现在可以被重用了 */
        typedArray.recycle();
		/* 初始化画笔 */
        paint = new Paint();
		/* 抗锯齿 */
        paint.setAntiAlias(true);
		/* 实心 */
        paint.setStyle(Paint.Style.FILL);
		/* 设置画笔颜色 */
        paint.setColor(rippleColor);
		/* 设置透明度 */
        paint.setAlpha(rippleAlpha);
		/* 允许重新绘制 */
        this.setWillNotDraw(false);
		/* 手势检测 */
        gestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {

                    /* 长按手势 */
                    @Override
                    public void onLongPress(MotionEvent event) {
                        super.onLongPress(event);
                        // if (getRippleIsStart()) {
                        setRippleDuration(600);
                        setZooming(false);
                        animateRipple(event);
                        sendClickEvent(true);
                        // }
                    }

                    /* 按下第一次一定时间内没有第二次按下调用 */
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        return true;
                    }

                    /* 单击一下松开 */
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        if (getRippleIsStart()) {
                            setRippleDuration(300);
                            setZooming(true);
                            return true;
                        }
                        return false;
                    }
                });
		/* 开启View缓存 */
        this.setDrawingCacheEnabled(true);
		/* 允许单击 */
        this.setClickable(true);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (animationRunning) {
            if (rippleDuration <= timer * frameRate) {
                animationRunning = false;
                timer = 0;
                durationEmpty = -1;
                timerEmpty = 0;
                //canvas.restore();
                invalidate();
                if (onCompletionListener != null)
                    onCompletionListener.onComplete(this);
                return;
            } else
                canvasHandler.postDelayed(runnable, frameRate);

            if (timer == 0)
                canvas.save();

            canvas.drawCircle(
                    x,
                    y,
                    (radiusMax * (((float) timer * frameRate) / rippleDuration)),
                    paint);

            paint.setColor(Color.parseColor("#ffff4444"));

            if (rippleType == 1 && originBitmap != null
                    && (((float) timer * frameRate) / rippleDuration) > 0.4f) {
                if (durationEmpty == -1)
                    durationEmpty = rippleDuration - timer * frameRate;

                timerEmpty++;
                final Bitmap tmpBitmap = getCircleBitmap((int) ((radiusMax) * (((float) timerEmpty * frameRate) / (durationEmpty))));
                canvas.drawBitmap(tmpBitmap, 0, 0, paint);
                tmpBitmap.recycle();
            }

            paint.setColor(rippleColor);

            if (rippleType == 1) {
                if ((((float) timer * frameRate) / rippleDuration) > 0.6f)
                    paint.setAlpha((int) (rippleAlpha - ((rippleAlpha) * (((float) timerEmpty * frameRate) / (durationEmpty)))));
                else
                    paint.setAlpha(rippleAlpha);
            } else
                paint.setAlpha((int) (rippleAlpha - ((rippleAlpha) * (((float) timer * frameRate) / rippleDuration))));

            timer++;
        }
    }

	/* View第一次被赋予大小或者大小改变调用 */

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WIDTH = w;
        HEIGHT = h;
		/* 初始化缩放动画 */
        scaleAnimation = new ScaleAnimation(1.0f, zoomScale, 1.0f, zoomScale,
                w / 2, h / 2);
		/* 动画持续时间 */
        scaleAnimation.setDuration(zoomDuration);
		/* 反方向执行 */
        scaleAnimation.setRepeatMode(Animation.REVERSE);
		/* 循环一次 */
        scaleAnimation.setRepeatCount(1);
    }

    /**
     * Launch Ripple animation for the current view with a MotionEvent
     * <p/>
     * 启动纹波动画与MotionEvent当前视图
     *
     * @param event MotionEvent registered by the Ripple gesture listener
     */
    public void animateRipple(MotionEvent event) {
        createAnimation(event.getX(), event.getY());
    }

    /**
     * Launch Ripple animation for the current view centered at x and y position
     *
     * @param x Horizontal position of the ripple center
     * @param y Vertical position of the ripple center
     */
    public void animateRipple(final float x, final float y) {
        createAnimation(x, y);
    }

    /**
     * Create Ripple animation centered at x, y
     * <p/>
     * 在指定位置创建波纹动画
     *
     * @param x Horizontal position of the ripple center
     * @param y Vertical position of the ripple center
     */
    private void createAnimation(final float x, final float y) {

        if (this.isEnabled() && !animationRunning) {
            if (hasToZoom)
				/* 开启放大动画 */
                this.startAnimation(scaleAnimation);

            radiusMax = Math.max(WIDTH, HEIGHT);

            if (rippleType != 2)
                radiusMax /= 2;

            radiusMax -= ripplePadding;

            if (isCentered || rippleType == 1) {
                this.x = getMeasuredWidth() / 2;
                this.y = getMeasuredHeight() / 2;
            } else {
                this.x = x;
                this.y = y;
            }

            animationRunning = true;

            if (rippleType == 1 && originBitmap == null)
                originBitmap = getDrawingCache(true);

            invalidate();
        }
    }

    /**
     * 单击松开
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            animateRipple(event);
            sendClickEvent(false);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        this.onTouchEvent(event);
        return super.onInterceptTouchEvent(event);
    }

    /**
     * Send a click event if parent view is a Listview instance
     *
     * @param isLongClick Is the event a long click ?
     */
    private void sendClickEvent(final Boolean isLongClick) {
        if (getParent() instanceof AdapterView) {
            final AdapterView adapterView = (AdapterView) getParent();
            final int position = adapterView.getPositionForView(this);
            final long id = adapterView.getItemIdAtPosition(position);
            if (isLongClick) {
                if (adapterView.getOnItemLongClickListener() != null)
                    adapterView.getOnItemLongClickListener().onItemLongClick(
                            adapterView, this, position, id);
            } else {
                if (adapterView.getOnItemClickListener() != null)
                    adapterView.getOnItemClickListener().onItemClick(
                            adapterView, this, position, id);
            }
        }
    }

    private Bitmap getCircleBitmap(final int radius) {
        final Bitmap output = Bitmap.createBitmap(originBitmap.getWidth(),
                originBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        final Rect rect = new Rect((int) (x - radius), (int) (y - radius),
                (int) (x + radius), (int) (y + radius));

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(x, y, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(originBitmap, rect, rect, paint);

        return output;
    }

    /**
     * Set Ripple color, default is #FFFFFF
     *
     * @param rippleColor New color resource
     */
    public void setRippleColor(int rippleColor) {
        this.rippleColor = getResources().getColor(rippleColor);
    }

    public int getRippleColor() {
        return rippleColor;
    }

    public RippleType getRippleType() {
        return RippleType.values()[rippleType];
    }

    /**
     * Set Ripple type, default is RippleType.SIMPLE
     *
     * @param rippleType New Ripple type for next animation
     */
    public void setRippleType(final RippleType rippleType) {
        this.rippleType = rippleType.ordinal();
    }

    public Boolean isCentered() {
        return isCentered;
    }

    /**
     * Set if ripple animation has to be centered in its parent view or not,
     * default is False
     *
     * @param isCentered
     */
    public void setCentered(final Boolean isCentered) {
        this.isCentered = isCentered;
    }

    public int getRipplePadding() {
        return ripplePadding;
    }

    /**
     * Set Ripple padding if you want to avoid some graphic glitch
     *
     * @param ripplePadding New Ripple padding in pixel, default is 0px
     */
    public void setRipplePadding(int ripplePadding) {
        this.ripplePadding = ripplePadding;
    }

    public Boolean isZooming() {
        return hasToZoom;
    }

    /**
     * At the end of Ripple effect, the child views has to zoom
     *
     * @param hasToZoom Do the child views have to zoom ? default is False
     */
    public void setZooming(Boolean hasToZoom) {
        this.hasToZoom = hasToZoom;
    }

    public float getZoomScale() {
        return zoomScale;
    }

    /**
     * Scale of the end animation
     *
     * @param zoomScale Value of scale animation, default is 1.03f
     */
    public void setZoomScale(float zoomScale) {
        this.zoomScale = zoomScale;
    }

    public int getZoomDuration() {
        return zoomDuration;
    }

    /**
     * Duration of the ending animation in ms
     *
     * @param zoomDuration Duration, default is 200ms
     */
    public void setZoomDuration(int zoomDuration) {
        this.zoomDuration = zoomDuration;
    }

    public int getRippleDuration() {
        return rippleDuration;
    }

    /**
     * Duration of the Ripple animation in ms
     *
     * @param rippleDuration Duration, default is 400ms
     */
    public void setRippleDuration(int rippleDuration) {
        this.rippleDuration = rippleDuration;
    }

    public int getFrameRate() {
        return frameRate;
    }

    /**
     * Set framerate for Ripple animation
     *
     * @param frameRate New framerate value, default is 10
     */
    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }

    public int getRippleAlpha() {
        return rippleAlpha;
    }

    /**
     * Set alpha for ripple effect color
     *
     * @param rippleAlpha Alpha value between 0 and 255, default is 90
     */
    public void setRippleAlpha(int rippleAlpha) {
        this.rippleAlpha = rippleAlpha;
    }

    public void setOnRippleCompleteListener(OnRippleCompleteListener listener) {
        this.onCompletionListener = listener;
    }

    /**
     * Defines a callback called at the end of the Ripple effect
     */
    public interface OnRippleCompleteListener {
        void onComplete(MDefRippleView rippleView);
    }

    /**
     * 是否开启放大动画
     */
    public boolean getRippleIsStart() {
        return mIsStartRipple;
    }

    public void setRippleIsStart(boolean mIsStart) {
        this.mIsStartRipple = mIsStart;
    }

    public enum RippleType {
        SIMPLE(0), DOUBLE(1), RECTANGLE(2);

        int type;

        RippleType(int type) {
            this.type = type;
        }
    }

}
