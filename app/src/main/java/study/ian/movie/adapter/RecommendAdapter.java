package study.ian.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import study.ian.movie.model.movie.recommend.RecommendResult;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.ServiceBuilder;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendHolder> {

    private Context context;
    private List<RecommendResult> recommendResultList = new ArrayList<>();

    public RecommendAdapter(Context context) {
        this.context = context;
    }

    public void addResults(List<RecommendResult> rList) {
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
        RecommendResult result = recommendResultList.get(i);

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.vd_credit_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
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
                .subscribe();

        recommendHolder.titleText.setText(result.getTitle());
        recommendHolder.releaseDateText.setText(result.getRelease_date());
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
