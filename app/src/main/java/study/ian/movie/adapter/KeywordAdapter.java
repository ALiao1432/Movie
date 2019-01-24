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

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import study.ian.movie.R;
import study.ian.movie.model.movie.keyword.Keyword;
import study.ian.movie.model.movie.keyword.KeywordResult;

public class KeywordAdapter extends RecyclerView.Adapter<KeywordAdapter.KeywordHolder> {

    private final String TAG = "KeywordAdapter";

    private Context context;
    private Keyword keyword;

    public KeywordAdapter(Context context, Keyword keyword) {
        this.context = context;
        this.keyword = keyword;
    }

    @NonNull
    @Override
    public KeywordHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_keyword, viewGroup, false);
        return new KeywordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeywordHolder keywordHolder, int i) {
        KeywordResult keywordResult = keyword.getKeywords().get(i);
        keywordHolder.keywordBtn.setText(keywordResult.getName());

        keywordHolder.clickDisposable = RxView.clicks(keywordHolder.keywordBtn)
                .throttleFirst(1500, TimeUnit.MILLISECONDS)
                .subscribe(unit -> Log.d(TAG, "onBindViewHolder: keyword : " + keywordResult.getName()));
    }

    @Override
    public int getItemCount() {
        return keyword.getKeywords().size();
    }

    @Override
    public void onViewRecycled(@NonNull KeywordHolder holder) {
        holder.clickDisposable.dispose();

        super.onViewRecycled(holder);
    }

    class KeywordHolder extends RecyclerView.ViewHolder {

        private Disposable clickDisposable;
        private Button keywordBtn;

        KeywordHolder(@NonNull View itemView) {
            super(itemView);

            keywordBtn = itemView.findViewById(R.id.keywordBtn);
        }
    }
}
