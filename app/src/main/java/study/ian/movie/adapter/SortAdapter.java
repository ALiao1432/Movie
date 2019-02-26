package study.ian.movie.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import study.ian.movie.R;
import study.ian.movie.util.OnOptionSelectedListener;

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.SortHolder> {

    private final String TAG = "SortAdapter";

    private final Context context;
    private List<String> optionList;
    private String currentSelected;
    private OnOptionSelectedListener optionSelectedListener;

    public SortAdapter(Context context) {
        this.context = context;
    }

    public void setOptionList(List<String> optionList) {
        this.optionList = optionList;
        currentSelected = optionList.get(0);
    }

    @NonNull
    @Override
    public SortHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_option, viewGroup,false);
        return new SortHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SortHolder sortHolder, int i) {
        TextView t = sortHolder.sortText;

        t.setText(optionList.get(i));

        RxView.clicks(t)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)
                .doOnNext(unit -> {
                    currentSelected = String.valueOf(t.getText());
                    optionSelectedListener.onOptionSelected(currentSelected);
                    notifyDataSetChanged();
                })
                .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: sort error : " + throwable))
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
        return optionList.size();
    }

    public void setOptionSelectedListener(OnOptionSelectedListener onOptionSelectedListener) {
        optionSelectedListener = onOptionSelectedListener;
    }

    class SortHolder extends RecyclerView.ViewHolder {

        final TextView sortText;

        SortHolder(@NonNull View itemView) {
            super(itemView);

            sortText = itemView.findViewById(R.id.optionText);
        }
    }
}
