package study.ian.movie.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import study.ian.movie.R;
import study.ian.movie.model.discover.GenreResult;

public class GenreOptionAdapter extends RecyclerView.Adapter<GenreOptionAdapter.GenreOptionHolder> {

    private Context context;
    private String currentSelected;
    private List<GenreResult> genreResultList;

    public GenreOptionAdapter(Context context) {
        this.context = context;
    }

    public void setGenreResultList(List<GenreResult> resultList) {
        genreResultList = resultList;
        currentSelected = resultList.get(0).getName();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GenreOptionAdapter.GenreOptionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_search_option, viewGroup, false);
        return new GenreOptionHolder(view);
    }

    @Override
    public int getItemCount() {
        if (genreResultList != null) {
            return genreResultList.size();
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull GenreOptionAdapter.GenreOptionHolder genreOptionHolder, int i) {
        TextView t = genreOptionHolder.genreText;

        t.setText(genreResultList.get(i).getName());

        RxView.clicks(t)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)
                .doOnNext(unit -> {
                    currentSelected = String.valueOf(t.getText());
                    notifyDataSetChanged();
                })
                .subscribe();

        if (currentSelected.equals(String.valueOf(t.getText()))) {
            t.setSelected(true);
            t.setTypeface(Typeface.DEFAULT_BOLD);
        } else {
            t.setSelected(false);
            t.setTypeface(Typeface.DEFAULT);
        }
    }

    class GenreOptionHolder extends RecyclerView.ViewHolder {

        private TextView genreText;

        GenreOptionHolder(@NonNull View itemView) {
            super(itemView);

            genreText = itemView.findViewById(R.id.searchOptionText);
        }
    }
}
