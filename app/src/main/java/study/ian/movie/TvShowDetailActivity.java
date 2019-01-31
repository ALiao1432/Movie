package study.ian.movie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import study.ian.movie.adapter.CreditAdapter;
import study.ian.movie.adapter.RecommendAdapter;
import study.ian.movie.model.tv.TvShow;
import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.service.TvShowService;
import study.ian.movie.view.GradientImageView;

public class TvShowDetailActivity extends AppCompatActivity {

    private final String TAG = "TvShowDetailActivity";
    private final int VISIBLE_THRESHOLD = 10;

    private GradientImageView backdropImage;
    private TextView titleText;
    private TextView firstAirDateText;
    private TextView statusText;
    private TextView overviewText;
    private TextView recommendText;
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

        tvShowId = getIntent().getIntExtra(TvShowService.KEY_ID, 0);

        findViews();
        setViews(tvShowId);
    }

    private void findViews() {
        backdropImage = findViewById(R.id.detailBackdropImage);
        titleText = findViewById(R.id.detailTitleText);
        firstAirDateText = findViewById(R.id.detailTitleFirstSubText);
        statusText = findViewById(R.id.detailTitleSecondSubText);
        overviewText = findViewById(R.id.overviewContentText);
        recommendText = findViewById(R.id.recommendTitleText);
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
                .getDetail(tvShowId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(detail -> {
                    loadBackdropImage(detail.getBackdrop_path());
                    titleText.setText(detail.getName());
                    firstAirDateText.setText(detail.getFirst_air_date());
                    statusText.setText(detail.getStatus());
                    overviewText.setText(detail.getOverview());
                })
                .doOnError(throwable -> Log.d(TAG, "setViews: get tv show detail error : " + throwable))
                .subscribe();

        // get credit
        ServiceBuilder.getService(PeopleService.class)
                .getTvCredit(tvShowId, ServiceBuilder.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(credit -> creditRecyclerView.setAdapter(new CreditAdapter(this, credit)))
                .doOnError(throwable -> Log.d(TAG, "setViews: get tv credit error : " + throwable))
                .subscribe();
    }

    private void initRecyclerViews() {
        LinearLayoutManager creditLayoutManager = new LinearLayoutManager(this);
        creditLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        creditRecyclerView.setLayoutManager(creditLayoutManager);

        creditRecyclerView.setNestedScrollingEnabled(false);
    }

    private void loadBackdropImage(String imagePath) {
        RequestOptions requestOptions = new RequestOptions().centerCrop().error(R.drawable.vd_credit_holder);
        Glide.with(this)
                .load(ServiceBuilder.BACKDROP_BASE_URL + imagePath)
                .apply(requestOptions)
                .into(backdropImage);
    }
}
