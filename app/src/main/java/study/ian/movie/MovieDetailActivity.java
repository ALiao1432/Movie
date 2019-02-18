package study.ian.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import kotlin.Unit;
import study.ian.morphviewlib.MorphView;
import study.ian.movie.adapter.CreditAdapter;
import study.ian.movie.adapter.GenreAdapter;
import study.ian.movie.adapter.KeywordAdapter;
import study.ian.movie.adapter.RecommendAdapter;
import study.ian.movie.model.movie.recommend.Recommend;
import study.ian.movie.model.movie.recommend.RecommendResult;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.util.Config;
import study.ian.movie.util.DetailActivity;
import study.ian.movie.util.ObserverHelper;
import study.ian.movie.view.ExpandableTextView;
import study.ian.movie.view.GradientImageView;

public class MovieDetailActivity extends DetailActivity {

    private final String TAG = "MovieDetailActivity";

    private GradientImageView backdropImage;
    private TextView titleText;
    private TextView releaseDateText;
    private TextView runTimeText;
    private TextView recommendText;
    private ExpandableTextView overviewText;
    private RecyclerView creditRecyclerView;
    private RecyclerView recommendRecyclerView;
    private RecyclerView genreRecyclerView;
    private RecyclerView keywordRecyclerView;
    private MorphView expandHintView;
    private RecommendAdapter recommendAdapter;
    private boolean isRecommendLoading = false;
    private int currentRecommendPage = 0;
    private int totalRecommendPages = 0;
    private int movieId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movieId = getIntent().getIntExtra(MovieService.KEY_ID, 0);
        ServiceBuilder.watchNetworkState(this);

        findViews();
        setViews(movieId);
    }

    private void findViews() {
        backdropImage = findViewById(R.id.detailBackdropImage);
        titleText = findViewById(R.id.detailTitleText);
        releaseDateText = findViewById(R.id.detailTitleFirstSubText);
        runTimeText = findViewById(R.id.detailTitleSecondSubText);
        recommendText = findViewById(R.id.recommendTitleText);
        overviewText = findViewById(R.id.overviewContentText);
        creditRecyclerView = findViewById(R.id.recyclerViewCredits);
        recommendRecyclerView = findViewById(R.id.recyclerViewRecommend);
        genreRecyclerView = findViewById(R.id.recyclerViewGenres);
        keywordRecyclerView = findViewById(R.id.recyclerViewKeywords);
    }

    private void setViews(int movieId) {
        initRecyclerViews();

        // get detail
        ServiceBuilder.getService(MovieService.class)
                .getDetail(movieId, ServiceBuilder.API_KEY, Config.REQUEST_LANGUAGE)
                .compose(ObserverHelper.applyHelper())
                .doOnNext(detail -> {
                    ((TextView) findViewById(R.id.overviewTitleText)).setText(getString(R.string.overview));
                    ((TextView) findViewById(R.id.genreTitleText)).setText(getString(R.string.genre));

                    loadBackdropImage(backdropImage, detail.getBackdrop_path());
                    titleText.setText(detail.getTitle());
                    releaseDateText.setText(detail.getRelease_date());
                    runTimeText.setText(detail.getRuntime() + " mins");
                    overviewText.setText(detail.getOverview());

                    if (overviewText.isExpandable()) {
                        View v = ((ViewStub) findViewById(R.id.expandHintViewStub)).inflate();
                        expandHintView = v.findViewById(R.id.expandHintView);
                        expandHintView.setCurrentId(R.drawable.vd_expand_arrow_down);
                        expandHintView.setPaintColor("#E2E2E2");
                        expandHintView.setSize(75, 75);
                    }
                    
                    genreRecyclerView.setAdapter(new GenreAdapter(this, detail.getGenres()));
                })
                .doOnError(throwable -> Log.d(TAG, "onCreate: get movie detail error : " + throwable))
                .filter(detail -> overviewText.isExpandable())
                .flatMap(detail -> RxView.clicks(overviewText).throttleFirst(ExpandableTextView.DURATION, TimeUnit.MILLISECONDS))
                .doOnNext(unit -> {
                    overviewText.setExpand();
                    if (overviewText.isExpand()) {
                        expandHintView.performAnimation(R.drawable.vd_expand_arrow_up);
                    } else {
                        expandHintView.performAnimation(R.drawable.vd_expand_arrow_down);
                    }
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: click bioText error : " + throwable))
                .subscribe();

        // get video
        Observable<Unit> clickObservable = RxView.clicks(backdropImage)
                .throttleFirst(1500, TimeUnit.MILLISECONDS);
        ServiceBuilder.getService(MovieService.class)
                .getVideo(movieId, ServiceBuilder.API_KEY, Config.REQUEST_LANGUAGE)
                .compose(ObserverHelper.applyHelper())
                .flatMap(video -> Observable.fromIterable(video.getResults()))
                .map(videoResult -> {
                    if (videoResult.getSite().equals("YouTube")) {
                        backdropImage.setHasTrailer(true);
                        return videoResult.getKey();
                    }
                    return "";
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: get movie video error : " + throwable))
                .concatMap(key -> clickObservable.map(unit -> key))
                .filter(key -> !key.equals(""))
                .doOnNext(key -> watchYoutubeVideo(this, key))
                .doOnError(throwable -> Log.d(TAG, "setViews: click backdropImage error : " + throwable))
                .subscribe();

        // get credit
        ServiceBuilder.getService(PeopleService.class)
                .getMovieCredit(movieId, ServiceBuilder.API_KEY)
                .compose(ObserverHelper.applyHelper())
                .doOnNext(credit -> {
                    ((TextView) findViewById(R.id.creditTitleText)).setText(getString(R.string.credit));
                    creditRecyclerView.setAdapter(new CreditAdapter(this, credit));
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: get movie credit error : " + throwable))
                .subscribe();

        // get keyword
        ServiceBuilder.getService(MovieService.class)
                .getKeyword(movieId, ServiceBuilder.API_KEY)
                .compose(ObserverHelper.applyHelper())
                .doOnNext(keyword -> {
                    if (keyword.getKeywords().size() != 0) {
                        ((TextView) findViewById(R.id.keywordTitleText)).setText(getString(R.string.keyword));
                        keywordRecyclerView.setAdapter(new KeywordAdapter(this, keyword));
                    } else {
                        ((TextView) findViewById(R.id.keywordTitleText)).setText(getString(R.string.no_keyword));
                    }
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: get keyword error : " + throwable))
                .subscribe();
    }

    private void initRecyclerViews() {
        LinearLayoutManager creditLayoutManager = new LinearLayoutManager(this);
        creditLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager genreLayoutManager = new LinearLayoutManager(this);
        genreLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager keywordLayoutManager = new LinearLayoutManager(this);
        keywordLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager recommendLayoutManager = new LinearLayoutManager(this);
        recommendLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        creditRecyclerView.setLayoutManager(creditLayoutManager);
        genreRecyclerView.setLayoutManager(genreLayoutManager);
        keywordRecyclerView.setLayoutManager(keywordLayoutManager);
        recommendRecyclerView.setLayoutManager(recommendLayoutManager);

        recommendAdapter = new RecommendAdapter(this);
        recommendRecyclerView.setAdapter(recommendAdapter);

        creditRecyclerView.setNestedScrollingEnabled(false);
        recommendRecyclerView.setNestedScrollingEnabled(false);

        // setup load more listener
        recommendRecyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int lastVisibleItem = recommendLayoutManager.findLastVisibleItemPosition();
            int totalItemCount = recommendLayoutManager.getItemCount();

            if (!isRecommendLoading && (lastVisibleItem + Config.VISIBLE_THRESHOLD) >= totalItemCount && currentRecommendPage < totalRecommendPages) {
                currentRecommendPage++;
                loadMorePage(ServiceBuilder.getService(MovieService.class).getRecommend(movieId, ServiceBuilder.API_KEY, currentRecommendPage, Config.REQUEST_LANGUAGE));
            }
        });

        if (currentRecommendPage == 0) {
            currentRecommendPage++;
            loadMorePage(ServiceBuilder.getService(MovieService.class).getRecommend(movieId, ServiceBuilder.API_KEY, currentRecommendPage, Config.REQUEST_LANGUAGE));
        }
    }

    private <T> void loadMorePage(Observable<T> observable) {
        isRecommendLoading = true;

        observable.compose(ObserverHelper.applyHelper())
                .doOnNext(o -> {
                    if (o instanceof Recommend) {
                        List<RecommendResult> resultList = ((Recommend) o).getResults();
                        if (resultList.size() == 0) {
                            recommendText.setText(getString(R.string.no_recommend_movie));
                        } else {
                            recommendText.setText(getString(R.string.recommend));
                            recommendAdapter.addResults(resultList);
                            totalRecommendPages = ((Recommend) o).getTotal_pages();
                            isRecommendLoading = false;
                        }
                    }
                })
                .doOnError(throwable -> Log.d(TAG, "loadMorePage: error : " + throwable))
                .subscribe();
    }
}
