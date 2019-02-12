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

import study.ian.movie.MovieDetailActivity;
import study.ian.movie.PersonDetailActivity;
import study.ian.movie.R;
import study.ian.movie.TvShowDetailActivity;
import study.ian.movie.model.movie.MovieResult;
import study.ian.movie.model.people.popular.KnownFor;
import study.ian.movie.model.people.popular.Popular;
import study.ian.movie.model.people.popular.PopularResult;
import study.ian.movie.model.tv.TvShowResult;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.service.TvShowService;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ResultHolder> {

    private final String TAG = "SearchAdapter";

    private Context context;
    private List<Object> resultList = new ArrayList<>();
    private RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public <T> void addResultList(List<T> rList) {
        resultList.addAll(rList);
        notifyDataSetChanged();
    }

    public void clearResultList() {
        resultList.clear();
    }

    @NonNull
    @Override
    public SearchAdapter.ResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_main_display, viewGroup, false);
        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ResultHolder viewHolder, int i) {
        if (resultList.get(i) instanceof MovieResult) {
            setMovieCard((MovieResult) resultList.get(i), viewHolder);
        } else if (resultList.get(i) instanceof TvShowResult) {
            setTvShowCard((TvShowResult) resultList.get(i), viewHolder);
        } else if (resultList.get(i) instanceof PopularResult) {
            setPersonCard((PopularResult) resultList.get(i), viewHolder);
        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    private void setMovieCard(MovieResult movieResult, SearchAdapter.ResultHolder viewHolder) {
        Glide.with(viewHolder.posterImage)
                .asBitmap()
                .load(ServiceBuilder.POSTER_BASE_URL + movieResult.getPoster_path())
                .apply(requestOptions)
                .transition(new BitmapTransitionOptions().crossFade(250))
                .into(viewHolder.posterImage);
        viewHolder.mainText.setText(movieResult.getTitle());
        viewHolder.subText.setText(movieResult.getRelease_date());

        RxView.clicks(viewHolder.cardView)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)
                .doOnNext(unit -> {
                    Intent intent = new Intent();
                    intent.putExtra(MovieService.KEY_ID, movieResult.getId());
                    intent.setClass(context, MovieDetailActivity.class);
                    context.startActivity(intent);
                })
                .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: click movie card error : " + throwable))
                .subscribe();
    }

    private void setTvShowCard(TvShowResult tvShowResult, SearchAdapter.ResultHolder viewHolder) {
        Glide.with(viewHolder.posterImage)
                .asBitmap()
                .load(ServiceBuilder.POSTER_BASE_URL + tvShowResult.getPoster_path())
                .apply(requestOptions)
                .transition(new BitmapTransitionOptions().crossFade(250))
                .into(viewHolder.posterImage);
        viewHolder.mainText.setText(tvShowResult.getName());
        viewHolder.subText.setText(tvShowResult.getFirst_air_date());

        RxView.clicks(viewHolder.cardView)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)
                .doOnNext(unit -> {
                    Intent intent = new Intent();
                    intent.putExtra(TvShowService.TV_SHOW_KEY_ID, tvShowResult.getId());
                    intent.setClass(context, TvShowDetailActivity.class);
                    context.startActivity(intent);
                })
                .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: click tv show card error : " + throwable))
                .subscribe();
    }

    private void setPersonCard(PopularResult popularResult, ResultHolder viewHolder) {
        Glide.with(viewHolder.posterImage)
                .asBitmap()
                .load(ServiceBuilder.POSTER_BASE_URL + popularResult.getProfile_path())
                .apply(requestOptions)
                .transition(new BitmapTransitionOptions().crossFade(250))
                .into(viewHolder.posterImage);
        viewHolder.mainText.setText(popularResult.getName());

        // set subText
        StringBuilder sb = new StringBuilder();
        for (KnownFor knownFor : popularResult.getKnown_for()) {
            sb.append(knownFor.getTitle()).append(", ");
        }
        if (sb.toString().equals("")) {
            viewHolder.subText.setText(context.getResources().getString(R.string.no_known_for));
        } else {
            viewHolder.subText.setText(sb.toString());
        }

        RxView.clicks(viewHolder.cardView)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)
                .doOnNext(unit -> {
                    Intent intent = new Intent();
                    intent.putExtra(PeopleService.KEY_ID, popularResult.getId());
                    intent.setClass(context, PersonDetailActivity.class);
                    context.startActivity(intent);
                })
                .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: click person card error : " + throwable))
                .subscribe();
    }

    class ResultHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView posterImage;
        private TextView mainText;
        private TextView subText;

        ResultHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.holderMainDisplayCard);
            posterImage = itemView.findViewById(R.id.holderMainImage);
            mainText = itemView.findViewById(R.id.mainTitleText);
            subText = itemView.findViewById(R.id.subContentText);
        }
    }
}
