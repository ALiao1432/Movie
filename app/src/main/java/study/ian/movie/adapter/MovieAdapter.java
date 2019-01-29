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
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import study.ian.movie.MovieDetailActivity;
import study.ian.movie.R;
import study.ian.movie.model.movie.MovieResult;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.ServiceBuilder;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private final String TAG = "MovieAdapter";

    private Context context;
    private List<MovieResult> movieResultList = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void addResults(List<MovieResult> rList) {
        movieResultList.addAll(rList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_main_display, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int i) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .asBitmap()
                .load(ServiceBuilder.POSTER_BASE_URL + movieResultList.get(i).getPoster_path())
                .apply(requestOptions)
                .transition(new BitmapTransitionOptions().crossFade(250))
                .into(holder.posterImage);

        holder.titleText.setText(movieResultList.get(i).getTitle());
        holder.releaseDateText.setText(movieResultList.get(i).getRelease_date());

        RxView.clicks(holder.cardView)
                .throttleFirst(1500, TimeUnit.MILLISECONDS) // only react to first click and skip the clicks within 1500ms
                .doOnNext(unit -> {
                    Intent intent = new Intent();
                    intent.putExtra(MovieService.KEY_ID, movieResultList.get(i).getId());
                    intent.setClass(context, MovieDetailActivity.class);
                    context.startActivity(intent);
                })
                .subscribe();
    }

    @Override
    public int getItemCount() {
        return movieResultList.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView posterImage;
        private TextView titleText;
        private TextView releaseDateText;

        MovieHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.holderMainDisplayCard);
            posterImage = itemView.findViewById(R.id.holderMainImage);
            titleText = itemView.findViewById(R.id.mainTitleText);
            releaseDateText = itemView.findViewById(R.id.subContentText);
        }
    }
}
