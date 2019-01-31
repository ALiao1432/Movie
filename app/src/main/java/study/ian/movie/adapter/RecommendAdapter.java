package study.ian.movie.adapter;

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import study.ian.movie.MovieDetailActivity;
import study.ian.movie.R;
import study.ian.movie.TvShowDetailActivity;
import study.ian.movie.model.movie.recommend.RecommendResult;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.service.TvShowService;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendHolder> {

    private final String TAG = "RecommendAdapter";

    private Context context;
    private List<Object> recommendResultList = new ArrayList<>();

    public RecommendAdapter(Context context) {
        this.context = context;
    }

    public <T> void addResults(List<T> rList) {
        recommendResultList.addAll(rList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecommendHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_detail_card, viewGroup, false);
        return new RecommendHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendHolder recommendHolder, int i) {
        if (recommendResultList.get(i) instanceof RecommendResult) {
            RecommendResult result = (RecommendResult) recommendResultList.get(i);

            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.vd_credit_holder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(recommendHolder.recommendImage)
                    .load(ServiceBuilder.POSTER_BASE_URL + result.getPoster_path())
                    .apply(requestOptions)
                    .into(recommendHolder.recommendImage);

            RxView.clicks(recommendHolder.cardView)
                    .throttleFirst(1500, TimeUnit.MILLISECONDS)
                    .doOnNext(unit -> {
                        Intent intent = new Intent();
                        intent.putExtra(MovieService.KEY_ID, result.getId());
                        intent.setClass(context, MovieDetailActivity.class);
                        context.startActivity(intent);
                    })
                    .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: movie recommend error : " + throwable))
                    .subscribe();

            recommendHolder.titleText.setText(result.getTitle());
            recommendHolder.releaseDateText.setText(result.getRelease_date());
        } else if (recommendResultList.get(i) instanceof study.ian.movie.model.tv.recommend.RecommendResult) {
            study.ian.movie.model.tv.recommend.RecommendResult result =
                    (study.ian.movie.model.tv.recommend.RecommendResult) recommendResultList.get(i);

            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.vd_credit_holder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
            Glide.with(recommendHolder.recommendImage)
                    .load(ServiceBuilder.POSTER_BASE_URL + result.getPoster_path())
                    .apply(requestOptions)
                    .into(recommendHolder.recommendImage);

            RxView.clicks(recommendHolder.cardView)
                    .throttleFirst(1500, TimeUnit.MILLISECONDS)
                    .doOnNext(unit -> {
                        Intent intent = new Intent();
                        intent.putExtra(TvShowService.KEY_ID, result.getId());
                        intent.setClass(context, TvShowDetailActivity.class);
                        context.startActivity(intent);
                    })
                    .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: tv recommend error : " + throwable))
                    .subscribe();

            recommendHolder.titleText.setText(result.getName());
            recommendHolder.releaseDateText.setText(result.getFirst_air_date());
        }

    }

    @Override
    public int getItemCount() {
        return recommendResultList.size();
    }

    class RecommendHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView recommendImage;
        private TextView titleText;
        private TextView releaseDateText;

        RecommendHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.holderDetailCard);
            recommendImage = itemView.findViewById(R.id.holderDetailImage);
            titleText = itemView.findViewById(R.id.detailMainText);
            releaseDateText = itemView.findViewById(R.id.detailSubText);
        }
    }
}
