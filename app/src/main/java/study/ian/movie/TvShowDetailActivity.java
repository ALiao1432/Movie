package study.ian.movie;

import android.os.Bundle;
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
import study.ian.movie.adapter.SeasonAdapter;
import study.ian.movie.model.genral.video.VideoResult;
import study.ian.movie.model.tv.recommend.Recommend;
import study.ian.movie.model.tv.recommend.RecommendResult;
import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.service.TvShowService;
import study.ian.movie.util.Config;
import study.ian.movie.util.DetailActivity;
import study.ian.movie.util.ObserverHelper;
import study.ian.movie.view.ExpandableTextView;
import study.ian.movie.view.GradientImageView;

public class TvShowDetailActivity extends DetailActivity {

    private final String TAG = "TvShowDetailActivity";

    private GradientImageView backdropImage;
    private TextView titleText;
    private TextView firstAirDateText;
    private TextView statusText;
    private TextView recommendText;
    private MorphView expandHintView;
    private ExpandableTextView overviewText;
    private RecyclerView creditRecyclerView;
    private RecyclerView seasonRecyclerView;
    private RecyclerView recommendRecyclerView;
    private RecyclerView genreRecyclerView;
    private RecyclerView keywordRecyclerView;
    private RecommendAdapter recommendAdapter;
    private boolean isRecommendLoading = false;
    private int currentRecommendPage = 0;
    private int totalRecommendPages = 0;
    private int tvShowId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_show_detail);

        tvShowId = getIntent().getIntExtra(TvShowService.KEY_TV_SHOW_ID, 0);
        ServiceBuilder.watchNetworkState(this);

        findViews();
        setViews(tvShowId);
    }

    private void findViews() {
        backdropImage = findViewById(R.id.detailBackdropImage);
        titleText = findViewById(R.id.detailTitleText);
        firstAirDateText = findViewById(R.id.detailTitleFirstSubText);
        statusText = findViewById(R.id.detailTitleSecondSubText);
        recommendText = findViewById(R.id.recommendTitleText);
        overviewText = findViewById(R.id.overviewContentText);
        creditRecyclerView = findViewById(R.id.recyclerViewCredits);
        seasonRecyclerView = findViewById(R.id.recyclerViewSeasons);
        recommendRecyclerView = findViewById(R.id.recyclerViewRecommend);
        genreRecyclerView = findViewById(R.id.recyclerViewGenres);
        keywordRecyclerView = findViewById(R.id.recyclerViewKeywords);
    }

    private void setViews(int tvShowId) {
        initRecyclerViews();

        // get detail
        ServiceBuilder.getService(TvShowService.class)
                .getDetail(tvShowId, ServiceBuilder.API_KEY, Config.REQUEST_LANGUAGE)
                .compose(ObserverHelper.applyHelper())
                .doOnNext(detail -> {
                    loadBackdropImage(backdropImage, detail.getBackdrop_path());
                    titleText.setText(detail.getName());
                    firstAirDateText.setText(detail.getFirst_air_date());
                    statusText.setText(detail.getStatus());
                    overviewText.setText(detail.getOverview());

                    if (overviewText.isExpandable()) {
                        View v = ((ViewStub) findViewById(R.id.expandHintViewStub)).inflate();
                        expandHintView = v.findViewById(R.id.expandHintView);
                        expandHintView.setCurrentId(R.drawable.vd_expand_arrow_down);
                        expandHintView.setPaintColor("#E2E2E2");
                        expandHintView.setSize(75, 75);

                        RxView.clicks(overviewText)
                                .throttleFirst(ExpandableTextView.DURATION, TimeUnit.MILLISECONDS)
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
                    }

                    seasonRecyclerView.setAdapter(new SeasonAdapter(this, detail.getSeasons(), tvShowId));
                    genreRecyclerView.setAdapter(new GenreAdapter(this, detail.getGenres()));
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: get tv show detail error : " + throwable))
                .subscribe();

        // get video
        Observable<Unit> clickObservable = RxView.clicks(backdropImage)
                .throttleFirst(1500, TimeUnit.MILLISECONDS);
        ServiceBuilder.getService(TvShowService.class)
                .getVideo(tvShowId, ServiceBuilder.API_KEY, Config.REQUEST_LANGUAGE)
                .compose(ObserverHelper.applyHelper())
                .map(video -> {
                    for (VideoResult result : video.getResults()) {
                        if (result.getSite().equals("YouTube")) {
                            backdropImage.setHasTrailer(true);
                            return result.getKey();
                        }
                    }
                    return "";
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: get tv video error : " + throwable))
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
                .getTvCredit(tvShowId, ServiceBuilder.API_KEY, Config.REQUEST_LANGUAGE)
                .compose(ObserverHelper.applyHelper())
                .doOnNext(credit -> creditRecyclerView.setAdapter(new CreditAdapter(this, credit)))
                .doOnError(throwable -> Log.d(TAG, "setViews: get tv credit error : " + throwable))
                .subscribe();

        // get keyword
        ServiceBuilder.getService(TvShowService.class)
                .getKeyword(tvShowId, ServiceBuilder.API_KEY)
                .compose(ObserverHelper.applyHelper())
                .doOnNext(keyword -> keywordRecyclerView.setAdapter(new KeywordAdapter(this, keyword)))
                .doOnError(throwable -> Log.d(TAG, "setViews: get keyword error : " + throwable))
                .subscribe();
    }

    private void initRecyclerViews() {
        LinearLayoutManager creditLayoutManager = new LinearLayoutManager(this);
        creditLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager seasonLayoutManager = new LinearLayoutManager(this);
        seasonLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager recommendLayoutManager = new LinearLayoutManager(this);
        recommendLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager genreLayoutManager = new LinearLayoutManager(this);
        genreLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        LinearLayoutManager keywordLayoutManager = new LinearLayoutManager(this);
        keywordLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        creditRecyclerView.setLayoutManager(creditLayoutManager);
        seasonRecyclerView.setLayoutManager(seasonLayoutManager);
        recommendRecyclerView.setLayoutManager(recommendLayoutManager);
        genreRecyclerView.setLayoutManager(genreLayoutManager);
        keywordRecyclerView.setLayoutManager(keywordLayoutManager);

        recommendAdapter = new RecommendAdapter(this);
        recommendRecyclerView.setAdapter(recommendAdapter);

        creditRecyclerView.setNestedScrollingEnabled(false);
        seasonRecyclerView.setNestedScrollingEnabled(false);
        recommendRecyclerView.setNestedScrollingEnabled(false);

        // setup load more listener
        recommendRecyclerView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int lastVisibleItem = recommendLayoutManager.findLastVisibleItemPosition();
            int totalItemCount = recommendLayoutManager.getItemCount();

            if (!isRecommendLoading && (lastVisibleItem + Config.VISIBLE_THRESHOLD) >= totalItemCount && currentRecommendPage < totalRecommendPages) {
                currentRecommendPage++;
                loadMorePage(ServiceBuilder.getService(TvShowService.class).getRecommend(tvShowId, ServiceBuilder.API_KEY, currentRecommendPage, Config.REQUEST_LANGUAGE));
            }
        });

        if (currentRecommendPage == 0) {
            currentRecommendPage++;
            loadMorePage(ServiceBuilder.getService(TvShowService.class).getRecommend(tvShowId, ServiceBuilder.API_KEY, currentRecommendPage, Config.REQUEST_LANGUAGE));
        }
    }

    private <T> void loadMorePage(Observable<T> observable) {
        isRecommendLoading = true;

        observable.compose(ObserverHelper.applyHelper())
                .doOnNext(o -> {
                    if (o instanceof Recommend) {
                        List<RecommendResult> resultList = ((Recommend) o).getResults();
                        if (resultList.size() == 0) {
                            recommendText.setText(getResources().getString(R.string.no_recommend_tv));
                        } else {
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
