package study.ian.movie.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import study.ian.morphviewlib.MorphView;
import study.ian.movie.R;

public class GradientImageView extends MorphView {

    private final String TAG = "GradientImageView";

    private LinearGradient linearGradient;
    private final Paint paint = new Paint();
    private final Path gradientPath = new Path();
    private final float GRADIENT_HEIGHT_RATIO = .75F;
    private float width;
    private float height;

    public GradientImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setPaintColor(0x9fffffff);
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                width = getWidth();
                height = getHeight();
                linearGradient = new LinearGradient(
                        width * .5f, height * GRADIENT_HEIGHT_RATIO,
                        width * .5f, height,
                        0x0, ContextCompat.getColor(context, R.color.colorPrimaryDark),
                        Shader.TileMode.CLAMP
                );
                paint.setShader(linearGradient);
                gradientPath.addRect(0, height * GRADIENT_HEIGHT_RATIO, width, height, Path.Direction.CW);

                setCurrentId(R.drawable.vd_no_trailer);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.rotate(180, width * .5f, height * .5f);
        canvas.drawPath(gradientPath, paint);
        canvas.rotate(180, width * .5f, height * .5f);
        canvas.drawPath(gradientPath, paint);
    }

    public void setHasTrailer(boolean hasTrailer) {
        setCurrentId(hasTrailer ? R.drawable.vd_play : R.drawable.vd_no_trailer);
    }
}
