package study.ian.movie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
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
import study.ian.movie.adapter.CreditAdapter;
import study.ian.movie.adapter.GenreAdapter;
import study.ian.movie.model.movie.video.VideoResult;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.view.GradientImageView;

public class MovieDetailActivity extends AppCompatActivity {

    private final String TAG = "MovieDetailActivity";

    private GradientImageView backdropImage;
    private TextView titleText;
    private TextView runTimeText;
    private TextView releaseDateText;
    private TextView overviewText;
    private RecyclerView genreRecyclerView;
    private RecyclerView creditRecyclerView;
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
        genreRecyclerView = findViewById(R.id.recyclerViewGenres);
        creditRecyclerView = findViewById(R.id.recyclerViewCredits);
    }

    private void setViews(int movieId) {
        initRecyclerViews();

        // get detail
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
                .doOnError(throwable -> Log.d(TAG, "onCreate: get detail error : " + throwable))
                .subscribe();

        // get video
        Observable<Unit> clickObservable = RxView.clicks(backdropImage)
                .throttleFirst(1500, TimeUnit.MILLISECONDS);
        Disposable videoDisposable = ServiceBuilder.getService(MovieService.class)
                .getVideo(movieId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> Log.d(TAG, "setViews: get video error : " + throwable))
                .concatMap(video -> clickObservable.map(unit -> video))
                .doOnNext(video -> {
                    for (VideoResult result : video.getResults()) {
                        if (result.getSite().equals("YouTube")) {
                            watchYoutubeVideo(this, result.getKey());
                            break;
                        }
                    }
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: click backdropImage error : " + throwable))
                .subscribe();

        // get credit
        Disposable creditDisposable = ServiceBuilder.getService(PeopleService.class)
                .getCredit(movieId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        credit -> creditRecyclerView.setAdapter(new CreditAdapter(this, credit)),
                        throwable -> Log.d(TAG, "setViews: get credit error : " + throwable)
                );

        // get keyword
        Disposable keywordDisposable = ServiceBuilder.getService(MovieService.class)
                .getKeyword(movieId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(keyword -> Log.d(TAG, "setViews: "))
                .doOnError(throwable -> Log.d(TAG, "setViews: get keyword error : " + throwable))
                .subscribe();

        compositeDisposable.add(detailDisposable);
        compositeDisposable.add(videoDisposable);
        compositeDisposable.add(creditDisposable);
    }

    private void initRecyclerViews() {
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(genreRecyclerView);
        helper.attachToRecyclerView(creditRecyclerView);

        LinearLayoutManager genreLayoutManager = new LinearLayoutManager(this);
        genreLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager creditLayoutManager = new LinearLayoutManager(this);
        creditLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        genreRecyclerView.setLayoutManager(genreLayoutManager);
        creditRecyclerView.setLayoutManager(creditLayoutManager);
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
