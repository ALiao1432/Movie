package study.ian.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import study.ian.movie.R;
import study.ian.movie.model.movie.detail.Genre;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreHolder> {

    private final String TAG = "GenreAdapter";

    private Context context;
    private List<Object> genreList = new ArrayList<>();

    public <T> GenreAdapter(Context context, List<T> genreList) {
        this.context = context;
        this.genreList.addAll(genreList);
    }

    @NonNull
    @Override
    public GenreHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_genre, viewGroup, false);
        return new GenreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreHolder genreHolder, int i) {
        if (genreList.get(i) instanceof Genre) {
            Genre genre = (Genre) genreList.get(i);

            genreHolder.genreBtn.setText(genre.getName());
            RxView.clicks(genreHolder.genreBtn)
                    .throttleFirst(1500, TimeUnit.MILLISECONDS)
                    .doOnNext(unit -> Log.d(TAG, "onBindViewHolder: genre : " + genre.getName() + ", id : " + genre.getId()))
                    .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: genre error : " + throwable))
                    .subscribe();
        } else if (genreList.get(i) instanceof study.ian.movie.model.tv.detail.Genre) {
            study.ian.movie.model.tv.detail.Genre genre =
                    (study.ian.movie.model.tv.detail.Genre) genreList.get(i);

            genreHolder.genreBtn.setText(genre.getName());
            RxView.clicks(genreHolder.genreBtn)
                    .throttleFirst(1500, TimeUnit.MILLISECONDS)
                    .doOnNext(unit -> Log.d(TAG, "onBindViewHolder: genre : " + genre.getName() + ", id : " + genre.getId()))
                    .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: genre error : " + throwable))
                    .subscribe();
        }
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }

    class GenreHolder extends RecyclerView.ViewHolder {

        private Button genreBtn;

        GenreHolder(@NonNull View itemView) {
            super(itemView);

            genreBtn = itemView.findViewById(R.id.genreBtn);
        }
    }
}
