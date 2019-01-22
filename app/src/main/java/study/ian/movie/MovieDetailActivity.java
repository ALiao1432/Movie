package study.ian.movie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
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
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        int movieId = getIntent().getIntExtra(MovieService.KEY_ID, 0);

        findViews();
        setViews(movieId);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();

        super.onDestroy();
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

        Disposable detailDisposable = ServiceBuilder.getService(MovieService.class)
                .getDetail(movieId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(detail -> {
                    loadBackdropImage(detail.getBackdrop_path());
                    titleText.setText(detail.getTitle());
                    runTimeText.setText(detail.getRuntime() + " mins");
                    releaseDateText.setText(detail.getRelease_date());
                    overviewText.setText(detail.getOverview());
                    genreRecyclerView.setAdapter(new GenreAdapter(this, detail.getGenres()));
                })
                .doOnError(throwable -> Log.d(TAG, "onCreate: error : " + throwable))
                .subscribe();

        Observable<Unit> clickObservable = RxView.clicks(backdropImage)
                .throttleFirst(1500, TimeUnit.MILLISECONDS);

        Disposable videoDisposable = ServiceBuilder.getService(MovieService.class)
                .getVideos(movieId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d(TAG, "setViews: get video error : " + throwable))
                .concatMap(video -> clickObservable.map(unit -> video))
                .subscribe(video -> watchYoutubeVideo(this, video.getResults().get(0).getKey()));

        compositeDisposable.add(detailDisposable);
        compositeDisposable.add(videoDisposable);
    }

    private void watchYoutubeVideo(Context context, String id) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + id)));
    }

    private void loadBackdropImage(String imagePath) {
        RequestOptions requestOptions = new RequestOptions().centerCrop();
        Glide.with(this)
                .load(ServiceBuilder.BACKDROP_BASE_URL + imagePath)
                .apply(requestOptions)
                .into(backdropImage);
    }
}
