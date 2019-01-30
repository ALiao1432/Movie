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
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import study.ian.movie.R;
import study.ian.movie.TvShowDetailActivity;
import study.ian.movie.model.tv.TvShowResult;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.service.TvShowService;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.TvShowHolder> {

    private final String TAG = "TvShowAdapter";

    private Context context;
    private List<TvShowResult> tvShowResultList = new ArrayList<>();

    public TvShowAdapter(Context context) {
        this.context = context;
    }

    public void addResults(List<TvShowResult> rList) {
        tvShowResultList.addAll(rList);
        notifyDataSetChanged();
    }

    public void clearResults() {
        tvShowResultList.clear();
    }

    @NonNull
    @Override
    public TvShowAdapter.TvShowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_main_display, viewGroup, false);
        return new TvShowAdapter.TvShowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowAdapter.TvShowHolder holder, int i) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .asBitmap()
                .load(ServiceBuilder.POSTER_BASE_URL + tvShowResultList.get(i).getPoster_path())
                .apply(requestOptions)
                .transition(new BitmapTransitionOptions().crossFade(250))
                .into(holder.posterImage);

        holder.titleText.setText(tvShowResultList.get(i).getName());
        holder.releaseDateText.setText(tvShowResultList.get(i).getFirst_air_date());

        RxView.clicks(holder.cardView)
                .throttleFirst(1500, TimeUnit.MILLISECONDS) // only react to first click and skip the clicks within 1500ms
                .doOnNext(unit -> {
                    Intent intent = new Intent();
                    intent.putExtra(TvShowService.KEY_ID, tvShowResultList.get(i).getId());
                    intent.setClass(context, TvShowDetailActivity.class);
                    context.startActivity(intent);
                })
                .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: click tv show card error : " + throwable))
                .subscribe();
    }

    @Override
    public int getItemCount() {
        return tvShowResultList.size();
    }

    class TvShowHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView posterImage;
        private TextView titleText;
        private TextView releaseDateText;

        TvShowHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.holderMainDisplayCard);
            posterImage = itemView.findViewById(R.id.holderMainImage);
            titleText = itemView.findViewById(R.id.mainTitleText);
            releaseDateText = itemView.findViewById(R.id.subContentText);
        }
    }
}
