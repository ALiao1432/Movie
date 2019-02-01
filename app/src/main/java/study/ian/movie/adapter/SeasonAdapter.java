package study.ian.movie.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import study.ian.movie.R;
import study.ian.movie.SeasonDetailActivity;
import study.ian.movie.model.tv.detail.Season;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.service.TvShowService;

public class SeasonAdapter extends RecyclerView.Adapter<SeasonAdapter.SeasonHolder> {

    private final String TAG = "SeasonAdapter";

    private Context context;
    private List<Season> seasonList;
    private int tvShowId;

    public SeasonAdapter(Context context, List<Season> seasonList, int tvShowId) {
        this.context = context;
        this.seasonList = seasonList;
        this.tvShowId = tvShowId;
    }

    @NonNull
    @Override
    public SeasonHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_detail_card, viewGroup, false);
        return new SeasonHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SeasonHolder seasonHolder, int i) {
        Season season = seasonList.get(i);

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.vd_credit_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(seasonHolder.seasonImage)
                .load(ServiceBuilder.POSTER_BASE_URL + season.getPoster_path())
                .apply(requestOptions)
                .into(seasonHolder.seasonImage);

        seasonHolder.titleText.setText(season.getName() + "．" + season.getEpisode_count() + "EP");
        seasonHolder.airDateText.setText(season.getAir_date());

        RxView.clicks(seasonHolder.cardView)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)
                .doOnNext(unit -> {
                    Intent intent = new Intent();
                    intent.putExtra(TvShowService.TV_SHOW_KEY_ID, tvShowId);
                    intent.putExtra(TvShowService.SEASON_NUM_KEY_ID, season.getSeason_number());
                    intent.setClass(context, SeasonDetailActivity.class);
                    context.startActivity(intent);
                })
                .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: click season image error : " + throwable))
                .subscribe();
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    class SeasonHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView seasonImage;
        private TextView titleText;
        private TextView airDateText;

        SeasonHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.holderDetailCard);
            seasonImage = itemView.findViewById(R.id.holderDetailImage);
            titleText = itemView.findViewById(R.id.detailMainText);
            airDateText = itemView.findViewById(R.id.detailSubText);
        }
    }
}
