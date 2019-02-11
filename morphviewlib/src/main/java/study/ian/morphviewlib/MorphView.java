package study.ian.morphviewlib;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

public class MorphView extends AppCompatImageView {

    private static final String TAG = "MorphView";

    private int W_SIZE = 150;
    private int H_SIZE = 150;
    private final SvgData svgData;
    private final Paint paint = new Paint();
    private DataPath path;
    private ValueAnimator pointAnimator;
    private int currentId;

    @SuppressWarnings("ClickableViewAccessibility")
    public MorphView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);

        svgData = new SvgData(context);

        initPaint();
        initAnimator();
    }

    private void initPaint() {
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(W_SIZE / 10);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initAnimator() {
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        final long animationDuration = 1600;

        pointAnimator = ValueAnimator.ofFloat(0, 1, 1.025f, 1.0125f, 1);
        pointAnimator.setDuration(animationDuration);
        pointAnimator.setInterpolator(linearInterpolator);
        pointAnimator.addUpdateListener(valueAnimator -> {
            path.reset();
            path = svgData.getMorphPath((float) valueAnimator.getAnimatedValue());
            postInvalidate();
        });
    }

    private void initPath(int id) {
        path = svgData.getPath(id, this);
        currentId = id;
    }

    public void addPath(int id) {
        path.addPath(svgData.getPath(id, this));
        postInvalidate();
    }

    public void performAnimation(int toId) {
        svgData.setMorphRes(currentId, toId, this);
        currentId = toId;
        pointAnimator.start();
    }

    public void setSize(int w, int h) {
        this.W_SIZE = w;
        this.H_SIZE = h;
        initPath(currentId);
    }

    public int getW_SIZE() {
        return W_SIZE;
    }

    public int getH_SIZE() {
        return H_SIZE;
    }

    public void setCurrentId(int id) {
        initPath(id);
        postInvalidate();
    }

    public void setPaintColor(String color) {
        paint.setColor(Color.parseColor(color));
    }

    public void setPaintColor(int color) {
        paint.setColor(color);
    }

    public void setPaintWidth(int w) {
        paint.setStrokeWidth(w);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate((getWidth() - W_SIZE) * .5f, (getHeight() - H_SIZE) * .5f);
        canvas.drawPath(path, paint);
        canvas.restore();
    }
}
