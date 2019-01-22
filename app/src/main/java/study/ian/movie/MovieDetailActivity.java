package study.ian.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import study.ian.movie.adapter.GenreAdapter;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.view.GradientImageView;
import study.ian.movie.view.UserScoreView;

public class MovieDetailActivity extends AppCompatActivity {

    private final String TAG = "MovieDetailActivity";

    private GradientImageView backdropImage;
    private TextView titleText;
    private TextView runTimeText;
    private TextView releaseDateText;
    private TextView overviewText;
    private UserScoreView userScoreView;
    private RecyclerView genreRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        int movieId = getIntent().getIntExtra(MovieService.KEY_ID, 0);

        findViews();
        setViews(movieId);
    }

    private void findViews() {
        backdropImage = findViewById(R.id.detailBackdropImage);
        titleText = findViewById(R.id.detailTitleText);
        runTimeText = findViewById(R.id.detailRunTimeText);
        releaseDateText = findViewById(R.id.detailReleaseDateText);
        overviewText = findViewById(R.id.overviewContentText);
        userScoreView = findViewById(R.id.detailScoreView);
        genreRecyclerView = findViewById(R.id.recyclerViewGenres);
    }

    private void setViews(int movieId) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        genreRecyclerView.setLayoutManager(linearLayoutManager);

        ServiceBuilder.getService(MovieService.class)
                .getDetail(movieId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        detail -> {
                            loadBackdropImage(detail.getBackdrop_path());
                            titleText.setText(detail.getTitle());
                            runTimeText.setText(detail.getRuntime() + " mins");
                            releaseDateText.setText(detail.getRelease_date());
                            overviewText.setText(detail.getOverview());
                            genreRecyclerView.setAdapter(new GenreAdapter(this, detail.getGenres()));
                        },
                        throwable -> Log.d(TAG, "onCreate: error : " + throwable)
                );
    }

    private void loadBackdropImage(String imagePath) {
        RequestOptions requestOptions = new RequestOptions().centerCrop();
        Glide.with(this)
                .load(ServiceBuilder.BACKDROP_BASE_URL + imagePath)
                .apply(requestOptions)
                .into(backdropImage);
    }
}
