package study.ian.movie;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import study.ian.movie.adapter.SeasonDetailAdapter;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.service.TvShowService;
import study.ian.movie.util.DetailActivity;
import study.ian.movie.util.LanguageConfig;
import study.ian.movie.view.GradientImageView;

public class SeasonDetailActivity extends DetailActivity {

    private final String TAG = "SeasonDetailActivity";

    private RecyclerView seasonDetailRecyclerView;
    private int seasonId;
    private int seasonNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_detail);

        seasonId = getIntent().getIntExtra(TvShowService.TV_SHOW_KEY_ID, 0);
        seasonNum = getIntent().getIntExtra(TvShowService.SEASON_NUM_KEY_ID, 0);

        findViews();
        setViews();
    }

    private void findViews() {
        seasonDetailRecyclerView = findViewById(R.id.recyclerViewSeasonDetail);
    }

    private void setViews() {
        GridLayoutManager seasonDetailLayoutManager = new GridLayoutManager(this, 1);
        seasonDetailRecyclerView.setLayoutManager(seasonDetailLayoutManager);

        ServiceBuilder.getService(TvShowService.class)
                .getSeasonDetails(seasonId, seasonNum, ServiceBuilder.API_KEY, LanguageConfig.REQUEST_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(season ->
                        seasonDetailRecyclerView.setAdapter(new SeasonDetailAdapter(this, season.getEpisodes())))
                .doOnError(throwable -> Log.d(TAG, "setViews: get season detail error : " + throwable))
                .subscribe();
    }
}
