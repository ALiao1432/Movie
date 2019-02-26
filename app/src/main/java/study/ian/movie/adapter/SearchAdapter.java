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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import study.ian.movie.MovieDetailActivity;
import study.ian.movie.PersonDetailActivity;
import study.ian.movie.R;
import study.ian.movie.TvShowDetailActivity;
import study.ian.movie.model.movie.MovieResult;
import study.ian.movie.model.people.popular.KnownFor;
import study.ian.movie.model.people.popular.PopularResult;
import study.ian.movie.model.tv.TvShowResult;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.service.TvShowService;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = "SearchAdapter";

    private final Context context;
    private final List<Object> resultList = new ArrayList<>();
    private final RequestOptions requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL);
    private boolean hasResult = false;
    private enum VIEW_TYPE {
        NORMAL,
        NO_RESULT
    }

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public <T> void addResultList(List<T> rList) {
        resultList.addAll(rList);
        hasResult = (resultList.size() != 0);
        if (!hasResult) {
            resultList.add(null);
        }

        notifyDataSetChanged();
    }

    public void clearResultList() {
        resultList.clear();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEW_TYPE.NORMAL.ordinal()) {
            View view = LayoutInflater.from(context).inflate(R.layout.holder_main_display, viewGroup, false);
            return new ResultHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.holder_main_no_result, viewGroup, false);
            return new NoResultHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (resultList.get(i) instanceof MovieResult) {
            setMovieCard((MovieResult) resultList.get(i), (SearchAdapter.ResultHolder) viewHolder);
        } else if (resultList.get(i) instanceof TvShowResult) {
            setTvShowCard((TvShowResult) resultList.get(i), (SearchAdapter.ResultHolder) viewHolder);
        } else if (resultList.get(i) instanceof PopularResult) {
            setPersonCard((PopularResult) resultList.get(i), (SearchAdapter.ResultHolder) viewHolder);
        } else if (viewHolder instanceof NoResultHolder) {
            setNoResult((SearchAdapter.NoResultHolder) viewHolder);
        }
    }

    private void setNoResult(NoResultHolder viewHolder) {

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    private void setMovieCard(@NotNull MovieResult movieResult, @NotNull SearchAdapter.ResultHolder viewHolder) {
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

    private void setTvShowCard(@NotNull TvShowResult tvShowResult, @NotNull SearchAdapter.ResultHolder viewHolder) {
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
                    intent.putExtra(TvShowService.KEY_TV_SHOW_ID, tvShowResult.getId());
                    intent.setClass(context, TvShowDetailActivity.class);
                    context.startActivity(intent);
                })
                .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: click tv show card error : " + throwable))
                .subscribe();
    }

    private void setPersonCard(@NotNull PopularResult popularResult, @NotNull ResultHolder viewHolder) {
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
            viewHolder.subText.setText(context.getString(R.string.no_known_for));
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

    @Override
    public int getItemViewType(int position) {
        return hasResult ? VIEW_TYPE.NORMAL.ordinal() : VIEW_TYPE.NO_RESULT.ordinal();
    }

    class ResultHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final ImageView posterImage;
        private final TextView mainText;
        private final TextView subText;

        ResultHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.holderMainDisplayCard);
            posterImage = itemView.findViewById(R.id.holderMainImage);
            mainText = itemView.findViewById(R.id.mainTitleText);
            subText = itemView.findViewById(R.id.subContentText);
        }
    }

    class NoResultHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        NoResultHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.noResultHintView);
        }
    }
}
