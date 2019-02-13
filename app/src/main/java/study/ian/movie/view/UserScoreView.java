package study.ian.movie.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

public class UserScoreView extends View {

    private final String TAG = "UserScoreView";
    private final int UPPER_START_COLOR = 0xfffafafa;
    private final int UPPER_TARGET_COLOR = 0xff43a047;

    private float width;
    private float height;
    private double score = -1;
    private Path upperPath = new Path();
    private PathMeasure pathMeasure = new PathMeasure();
    private Paint bgPaint = new Paint();
    private Paint lowerPaint = new Paint();
    private Paint upperPaint = new Paint();
    private Paint scoreNumPaint = new Paint();

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float VIEW_SIZE_RATIO = 0.1f; // determine the size of this view

        width = MeasureSpec.getSize(widthMeasureSpec);
        width *= VIEW_SIZE_RATIO;
        height = width;

        setMeasuredDimension(Math.round(width), Math.round(height));
    }

    public UserScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
        initPathConfig();
    }

    private void initPaint() {
        bgPaint.setColor(Color.BLACK);
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);

        lowerPaint.setColor(0xffbdbdbd);
        lowerPaint.setAntiAlias(true);
        lowerPaint.setStyle(Paint.Style.STROKE);
        lowerPaint.setStrokeWidth(14);

        upperPaint.setColor(Color.RED);
        upperPaint.setAntiAlias(true);
        upperPaint.setStyle(Paint.Style.STROKE);
        upperPaint.setStrokeWidth(14);
        upperPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initPathConfig() {
        upperPath.addCircle(width * .5f, height * .5f, width * .42f, Path.Direction.CW);
        pathMeasure.setPath(upperPath, false);
    }

    private void initAnimator() {
        long ANIMATION_DURATION = 1500;

        ValueAnimator colorAnimator = ValueAnimator.ofInt(UPPER_START_COLOR, UPPER_TARGET_COLOR).setDuration(ANIMATION_DURATION);
        colorAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        colorAnimator.addUpdateListener(animation -> {
            Log.d(TAG, "initAnimator: animation.getAnimatedFraction : " + animation.getAnimatedFraction());
            pathMeasure.getSegment(
                    0,
                    pathMeasure.getLength() * animation.getAnimatedFraction(),
                    upperPath,
                    false
            );
            Log.d(TAG, String.format("initAnimator: paint color : 0x%08X", (int) animation.getAnimatedValue()));
            upperPaint.setColor((int) animation.getAnimatedValue());
            postInvalidate();
        });
        colorAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBackgroundCircle(canvas);
        drawLowerCircle(canvas);
        drawUpperCircle(canvas);
        drawScoreNum(canvas);
    }

    private void drawBackgroundCircle(Canvas canvas) {
        canvas.drawCircle(width * .5f, height * .5f, width * .5f, bgPaint);
    }

    private void drawLowerCircle(Canvas canvas) {
        canvas.drawCircle(width * .5f, height * .5f, width * .42f, lowerPaint);
    }

    private void drawUpperCircle(Canvas canvas) {
        if (score != -1) {
            canvas.drawPath(upperPath, upperPaint);
            Log.d(TAG, "drawUpperCircle: draw upperCircle");
        }
    }

    private void drawScoreNum(Canvas canvas) {

    }

    public void setUserScore(double score) {
        this.score = score;
        initAnimator();
    }
}
