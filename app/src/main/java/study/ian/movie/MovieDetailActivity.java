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
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.Unit;
import study.ian.movie.adapter.CreditAdapter;
import study.ian.movie.adapter.GenreAdapter;
import study.ian.movie.adapter.KeywordAdapter;
import study.ian.movie.adapter.RecommendAdapter;
import study.ian.movie.model.movie.recommend.Recommend;
import study.ian.movie.model.movie.video.VideoResult;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.view.GradientImageView;

public class MovieDetailActivity extends AppCompatActivity {

    private final String TAG = "MovieDetailActivity";
    private final int VISIBLE_THRESHOLD = 10;

    private GradientImageView backdropImage;
    private TextView titleText;
    private TextView runTimeText;
    private TextView releaseDateText;
    private TextView overviewText;
    private RecyclerView genreRecyclerView;
    private RecyclerView creditRecyclerView;
    private RecyclerView keywordRecyclerView;
    private RecyclerView recommendRecyclerView;
    private RecommendAdapter recommendAdapter;
    private boolean isRecommendLoading = false;
    private int currentRecommendPage = 0;
    private int totalRecommendPages = 0;
    private int movieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        movieId = getIntent().getIntExtra(MovieService.KEY_ID, 0);

        findViews();
        setViews(movieId);
    }

    private void findViews() {
        backdropImage = findViewById(R.id.detailBackdropImage);
        titleText = findViewById(R.id.detailTitleText);
        runTimeText = findViewById(R.id.detailRunTimeText);
        releaseDateText = findViewById(R.id.detailReleaseDateText);
        overviewText = findViewById(R.id.overviewContentText);
        genreRecyclerView = findViewById(R.id.recyclerViewGenres);
        creditRecyclerView = findViewById(R.id.recyclerViewCredits);
        keywordRecyclerView = findViewById(R.id.recyclerViewKeywords);
        recommendRecyclerView = findViewById(R.id.recyclerViewRecommend);
    }

    private void setViews(int movieId) {
        initRecyclerViews();

        // get detail
        ServiceBuilder.getService(MovieService.class)
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
        ServiceBuilder.getService(MovieService.class)
                .getVideo(movieId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(video -> {
                    for (VideoResult result : video.getResults()) {
                        if (result.getSite().equals("YouTube")) {
                            backdropImage.setHasTrailer(true);
                            return result.getKey();
                        }
                    }
                    return "";
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: get video error : " + throwable))
                .concatMap(key -> clickObservable.map(unit -> key))
                .doOnNext(key -> {
                    if (key.equals("")) {
                        backdropImage.setClickable(false);
                    } else {
                        watchYoutubeVideo(this, key);
                    }
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: click backdropImage error : " + throwable))
                .subscribe();

        // get credit
        ServiceBuilder.getService(PeopleService.class)
                .getCredit(movieId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(credit -> creditRecyclerView.setAdapter(new CreditAdapter(this, credit)))
                .doOnError(throwable -> Log.d(TAG, "setViews: get credit error : " + throwable))
                .subscribe();

        // get keyword
        ServiceBuilder.getService(MovieService.class)
                .getKeyword(movieId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(keyword -> keywordRecyclerView.setAdapter(new KeywordAdapter(this, keyword)))
                .doOnError(throwable -> Log.d(TAG, "setViews: get keyword error : " + throwable))
                .subscribe();
    }

    private void initRecyclerViews() {
        new LinearSnapHelper().attachToRecyclerView(creditRecyclerView);
        new LinearSnapHelper().attachToRecyclerView(recommendRecyclerView);

        LinearLayoutManager creditLayoutManager = new LinearLayoutManager(this);
        creditLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager genreLayoutManager = new LinearLayoutManager(this);
        genreLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager keywordLayoutManager = new LinearLayoutManager(this);
        keywordLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager recommendLayoutManager = new LinearLayoutManager(this);
        recommendLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        recommendAdapter = new RecommendAdapter(this);
        recommendRecyclerView.setAdapter(recommendAdapter);

        genreRecyclerView.setLayoutManager(genreLayoutManager);
        creditRecyclerView.setLayoutManager(creditLayoutManager);
        keywordRecyclerView.setLayoutManager(keywordLayoutManager);
        recommendRecyclerView.setLayoutManager(recommendLayoutManager);

        // setup load more listener
        recommendRecyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int lastVisibleItem = recommendLayoutManager.findLastVisibleItemPosition();
            int totalItemCount = recommendLayoutManager.getItemCount();

            if (!isRecommendLoading && (lastVisibleItem + VISIBLE_THRESHOLD) >= totalItemCount && currentRecommendPage < totalRecommendPages) {
                currentRecommendPage++;
                loadMorePage(ServiceBuilder.getService(MovieService.class).getRecommend(movieId, ServiceBuilder.API_KEY, currentRecommendPage));
            }
        });

        if (currentRecommendPage == 0) {
            currentRecommendPage++;
            loadMorePage(ServiceBuilder.getService(MovieService.class).getRecommend(movieId, ServiceBuilder.API_KEY, currentRecommendPage));
        }
    }

    private <T> void loadMorePage(Observable<T> observable) {
        isRecommendLoading = true;

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(o -> {
                    if (o instanceof Recommend) {
                        recommendAdapter.addResults(((Recommend) o).getResults());
                        totalRecommendPages = ((Recommend) o).getTotal_pages();
                        isRecommendLoading = false;
                    }
                })
                .doOnError(throwable -> Log.d(TAG, "loadMorePage: error : " + throwable))
                .subscribe();
    }

    private void watchYoutubeVideo(Context context, String id) {
        context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + id)));
    }

    private void loadBackdropImage(String imagePath) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().error(R.drawable.vd_credit_holder);
        Glide.with(this)
                .load(ServiceBuilder.BACKDROP_BASE_URL + imagePath)
                .apply(requestOptions)
                .into(backdropImage);
    }
}
