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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import study.ian.movie.R;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.SortHolder> {

    private Context context;
    private List<String> sortOptionList = new ArrayList<>();
    private String currentSelected;

    public SortAdapter(Context context) {
        this.context = context;

        sortOptionList.addAll(Arrays.asList(context.getResources().getStringArray(R.array.sort_option_array)));
        currentSelected = sortOptionList.get(0);
    }

    @NonNull
    @Override
    public SortHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_search_option, viewGroup, false);
        return new SortHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SortHolder sortHolder, int i) {
        TextView t = sortHolder.sortText;

        t.setText(sortOptionList.get(i));

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

    @Override
    public int getItemCount() {
        return sortOptionList.size();
    }

    class SortHolder extends RecyclerView.ViewHolder {

        private TextView sortText;

        SortHolder(@NonNull View itemView) {
            super(itemView);

            sortText = itemView.findViewById(R.id.searchOptionText);
        }
    }
}
