package study.ian.movie.adapter;

import android.content.Context;
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
import study.ian.movie.model.movie.MovieResult;
import study.ian.movie.service.ServiceBuilder;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ResultHolder> {

    private final String TAG = "SearchAdapter";

    private Context context;
    private List resultList = new ArrayList<>();

    public SearchAdapter(Context context) {
        this.context = context;
    }

    public <T> void setResultList(List<T> rList) {
        Log.d(TAG, "setResultList: ");
        resultList.clear();
        resultList = rList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_main_display, viewGroup, false);
        return new ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ResultHolder viewHolder, int i) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        if (resultList.get(i) instanceof MovieResult) {
            MovieResult movieResult = (MovieResult) resultList.get(i);
            Glide.with(context)
                    .asBitmap()
                    .load(ServiceBuilder.POSTER_BASE_URL + movieResult.getPoster_path())
                    .apply(requestOptions)
                    .transition(new BitmapTransitionOptions().crossFade(250))
                    .into(viewHolder.posterImage);
            viewHolder.mainText.setText(movieResult.getTitle());
            viewHolder.subText.setText(movieResult.getRelease_date());

            RxView.clicks(viewHolder.cardView)
                    .throttleFirst(1500, TimeUnit.MILLISECONDS)
                    .doOnNext(unit -> Log.d(TAG, "onBindViewHolder: click"))
                    .subscribe();
        }
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
