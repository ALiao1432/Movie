package study.ian.movie.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import study.ian.movie.R;
import study.ian.movie.util.OnYearSelectedListener;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.YearHolder> {

    private final String TAG = "YearAdapter";

    private Context context;
    private List<String> yearList = new ArrayList<>();
    private List<String> tempList = new ArrayList<>();
    private String currentSelected;
    private OnYearSelectedListener yearSelectedListener;

    public YearAdapter(Context context) {
        this.context = context;

        tempList.add("None");
        currentSelected = tempList.get(0);
        int currentYear = (int) (Calendar.getInstance().getTime().getTime() / 365f / 24f / 60f / 60f / 1000f) + 1970;
        for (int i = currentYear; i >= 1900; i--) {
            tempList.add(String.valueOf(i));
        }
    }

    public void setSearchType(boolean hasYear) {
        if (hasYear) {
            yearList.clear();
            yearList.addAll(tempList);
        } else {
            yearList.clear();
            yearList.add("None");
        }
        notifyDataSetChanged();
    }

    public @Nullable Integer getSelectedYear() {
        if (currentSelected.equals("None")) {
            return null;
        } else {
            return Integer.valueOf(currentSelected);
        }
    }

    @NonNull
    @Override
    public YearHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_option, viewGroup, false);
        return new YearHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YearHolder yearHolder, int i) {
        TextView t = yearHolder.yearText;

        t.setText(yearList.get(i));

        RxView.clicks(t)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)
                .doOnNext(unit -> {
                    currentSelected = String.valueOf(t.getText());
                    yearSelectedListener.onYearSelected(getSelectedYear());
                    notifyDataSetChanged();
                })
                .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: year error : " + throwable))
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
        return yearList.size();
    }

    public void setOnYearSelectedListener(OnYearSelectedListener onYearSelectedListener) {
        yearSelectedListener = onYearSelectedListener;
    }

    class YearHolder extends RecyclerView.ViewHolder {

        private TextView yearText;

        YearHolder(@NonNull View itemView) {
            super(itemView);

            yearText = itemView.findViewById(R.id.optionText);
        }
    }
}
