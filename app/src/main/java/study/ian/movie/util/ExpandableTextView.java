package study.ian.movie.util;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class ExpandableTextView extends android.support.v7.widget.AppCompatTextView {

    private final String TAG = "ExpandableTextView";
    public final static long DURATION = 150;

    private boolean isExpand = false;
    private final int minLines = 3;

    public ExpandableTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setMaxLines(minLines);
    }

    public void setExpand() {
        if (!isExpand) {
            ObjectAnimator animator = ObjectAnimator.ofInt(this, "maxLines", this.getLineCount());
            animator.setDuration(DURATION);
            animator.start();
            isExpand = true;
        } else {
            ObjectAnimator animator = ObjectAnimator.ofInt(this, "maxLines", minLines);
            animator.setDuration(DURATION);
            animator.start();
            isExpand = false;
        }
    }

    public boolean isExpand() {
        return isExpand;
    }
}
