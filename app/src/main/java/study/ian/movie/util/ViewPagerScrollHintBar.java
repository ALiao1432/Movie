package study.ian.movie.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import study.ian.movie.PersonDetailActivity;

public class ViewPagerScrollHintBar extends View {

    private final String TAG = "ViewPagerScrollHintView";
    private final String BASELINE_COLOR = "#b4b4b4";

    private Paint pagePaint = new Paint();
    private int totalPage = 0;
    private int currentPage = -1;
    private float scrollShift = 0;
    private float width;
    private float height;
    private float onePageWidth;
    private VanishTime vanishTime = new VanishTime();

    public ViewPagerScrollHintBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        String SELECT_COLOR = "#009688";
        pagePaint.setColor(Color.parseColor(SELECT_COLOR));
        pagePaint.setStrokeCap(Paint.Cap.ROUND);
        pagePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (totalPage != 0 & currentPage != -1) {
            // draw baseline
            canvas.drawColor(Color.parseColor(BASELINE_COLOR));

            // draw current page
            canvas.drawRect(
                    (currentPage + scrollShift) * onePageWidth,
                    0,
                    (currentPage + 1 + scrollShift) * onePageWidth,
                    height,
                    pagePaint
            );
        }
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        onePageWidth = width / totalPage;
        postInvalidate();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        startVanishThread();
        postInvalidate();
    }

    public void setCurrentPage(int currentPage, float shift) {
        this.currentPage = currentPage;
        scrollShift = shift;
        startVanishThread();
        postInvalidate();
    }

    private void startVanishThread() {
        setVisibility(VISIBLE);
        if (!vanishTime.isStarting){
            vanishTime.startTime = System.currentTimeMillis();
            vanishTime.start();
        }
        else if (vanishTime.isRunning)
            vanishTime.startTime = System.currentTimeMillis();
        else {
            vanishTime = new VanishTime();
            vanishTime.startTime = System.currentTimeMillis();
            vanishTime.start();
        }
    }

    private class VanishTime extends Thread {
        long startTime;
        boolean isRunning = true;
        boolean isStarting = false;

        @Override
        public void run() {
            isStarting = true;
            while (true) {
                if (!isRunning)
                    return;
                else if (System.currentTimeMillis() > startTime + 1500) {
                    ((PersonDetailActivity)getContext()).runOnUiThread(() -> setVisibility(INVISIBLE));
                    isRunning = false;
                }
            }
        }
    }
}
