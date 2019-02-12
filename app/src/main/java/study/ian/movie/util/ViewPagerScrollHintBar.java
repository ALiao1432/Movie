package study.ian.movie.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import study.ian.movie.PersonDetailActivity;

public class ViewPagerScrollHintBar extends View {

    private final String TAG = "ViewPagerScrollHintView";
    private final int[] PAGE_COLOR = {0xff, 0x00, 0x96, 0x88};
    private final int[] BASE_COLOR = {0xff, 0xb4, 0xb4, 0xb4};
    private final long HINT_DURATION = 1000;

    private Paint pagePaint = new Paint();
    private Paint basePaint = new Paint();
    private int totalPage = 0;
    private int currentPage = -1;
    private float scrollShift = 0;
    private float width;
    private float height;
    private float onePageWidth;
    private Subject<Long> scrollSubject = PublishSubject.create();

    public ViewPagerScrollHintBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initPaint();
    }

    private void initPaint() {
        pagePaint.setColor(Color.argb(PAGE_COLOR[0], PAGE_COLOR[1], PAGE_COLOR[2], PAGE_COLOR[3]));
        pagePaint.setStrokeCap(Paint.Cap.ROUND);
        pagePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        basePaint.setColor(Color.argb(BASE_COLOR[0], BASE_COLOR[1], BASE_COLOR[2], BASE_COLOR[3]));
    }

    public void connectViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                setCurrentPage(i, v);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
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
            canvas.drawRect(0, 0, width, height, basePaint);

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

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        onePageWidth = width / totalPage;
        postInvalidate();
    }

    public void setCurrentPage(int page) {
        currentPage = page;
        basePaint.setColor(Color.argb(BASE_COLOR[0], BASE_COLOR[1], BASE_COLOR[2], BASE_COLOR[3]));
        postInvalidate();
        scrollSubject.onNext(System.currentTimeMillis());
    }

    public void setCurrentPage(int page, float shift) {
        currentPage = page;
        scrollShift = shift;
        basePaint.setColor(Color.argb(BASE_COLOR[0], BASE_COLOR[1], BASE_COLOR[2], BASE_COLOR[3]));
        postInvalidate();
        scrollSubject.onNext(System.currentTimeMillis());
    }
}
