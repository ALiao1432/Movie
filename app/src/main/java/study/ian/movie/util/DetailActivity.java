package study.ian.movie.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import study.ian.movie.R;
import study.ian.movie.service.ServiceBuilder;

public class DetailActivity extends AppCompatActivity {

    protected void loadBackdropImage(View targetView, String imagePath) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().error(R.drawable.vd_credit_holder);
        Glide.with(getApplicationContext())
                .load(ServiceBuilder.BACKDROP_BASE_URL + imagePath)
                .apply(requestOptions)
                .into((ImageView) targetView);
    }

    protected void watchYoutubeVideo(Context context, String id) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + id)));
    }
}
