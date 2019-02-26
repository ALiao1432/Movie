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
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding3.view.RxView;

import java.util.concurrent.TimeUnit;

import study.ian.movie.MovieDetailActivity;
import study.ian.movie.PersonDetailActivity;
import study.ian.movie.R;
import study.ian.movie.TvShowDetailActivity;
import study.ian.movie.model.movie.credit.Cast;
import study.ian.movie.model.movie.credit.Credit;
import study.ian.movie.service.MovieService;
import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;
import study.ian.movie.service.TvShowService;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditHolder> {

    private final String TAG = "CreditAdapter";

    private final Context context;
    private final Object credit;

    public CreditAdapter(Context context, Object credit) {
        this.context = context;
        this.credit = credit;
    }

    @NonNull
    @Override
    public CreditHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_detail_card, viewGroup, false);
        return new CreditHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditHolder creditHolder, int i) {
        if (credit instanceof Credit) {
            Cast cast = ((Credit) credit).getCast().get(i);

            loadImage(creditHolder.creditImage, ServiceBuilder.CREDIT_BASE_URL + cast.getProfile_path());

            RxView.clicks(creditHolder.cardView)
                    .throttleFirst(1500, TimeUnit.MILLISECONDS)
                    .doOnNext(unit -> {
                        Intent intent = new Intent();
                        intent.putExtra(PeopleService.KEY_ID, cast.getId());
                        intent.setClass(context, PersonDetailActivity.class);
                        context.startActivity(intent);
                    })
                    .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: click movie cast error : " + throwable))
                    .subscribe();

            creditHolder.creditText.setText(cast.getName());
            creditHolder.charText.setText(cast.getCharacter());
        } else if (credit instanceof study.ian.movie.model.tv.credit.Credit) {
            study.ian.movie.model.tv.credit.Cast cast =
                    ((study.ian.movie.model.tv.credit.Credit) credit).getCast().get(i);

            loadImage(creditHolder.creditImage, ServiceBuilder.CREDIT_BASE_URL + cast.getProfile_path());

            RxView.clicks(creditHolder.cardView)
                    .throttleFirst(1500, TimeUnit.MILLISECONDS)
                    .doOnNext(unit -> {
                        Intent intent = new Intent();
                        intent.putExtra(PeopleService.KEY_ID, cast.getId());
                        intent.setClass(context, PersonDetailActivity.class);
                        context.startActivity(intent);
                    })
                    .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: click tv cast error : " + throwable))
                    .subscribe();

            creditHolder.creditText.setText(cast.getName());
            creditHolder.charText.setText(cast.getCharacter());
        } else if (credit instanceof study.ian.movie.model.people.credit.movie.Credit) {
            study.ian.movie.model.people.credit.movie.Cast cast =
                    ((study.ian.movie.model.people.credit.movie.Credit) credit).getCast().get(i);

            loadImage(creditHolder.creditImage, ServiceBuilder.POSTER_BASE_URL + cast.getPoster_path());

            RxView.clicks(creditHolder.cardView)
                    .throttleFirst(1500, TimeUnit.MILLISECONDS)
                    .doOnNext(unit -> {
                        Intent intent = new Intent();
                        intent.putExtra(MovieService.KEY_ID, cast.getId());
                        intent.setClass(context, MovieDetailActivity.class);
                        context.startActivity(intent);
                    })
                    .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: click person movie credit error : " + throwable))
                    .subscribe();

            creditHolder.creditText.setText(cast.getTitle());
            creditHolder.charText.setText(cast.getRelease_date());
        } else if (credit instanceof study.ian.movie.model.people.credit.tv.Credit) {
            study.ian.movie.model.people.credit.tv.Cast cast =
                    ((study.ian.movie.model.people.credit.tv.Credit) credit).getCast().get(i);

            loadImage(creditHolder.creditImage, ServiceBuilder.POSTER_BASE_URL + cast.getPoster_path());

            RxView.clicks(creditHolder.cardView)
                    .throttleFirst(1500, TimeUnit.MILLISECONDS)
                    .doOnNext(unit -> {
                        Intent intent = new Intent();
                        intent.putExtra(TvShowService.KEY_TV_SHOW_ID, cast.getId());
                        intent.setClass(context, TvShowDetailActivity.class);
                        context.startActivity(intent);
                    })
                    .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: click person tv credit error : " + throwable))
                    .subscribe();

            creditHolder.creditText.setText(cast.getName());
            creditHolder.charText.setText(cast.getFirst_air_date());
        }
    }

    @Override
    public int getItemCount() {
        if (credit instanceof Credit) {
            return ((Credit) credit).getCast().size();
        } else if (credit instanceof study.ian.movie.model.tv.credit.Credit) {
            return ((study.ian.movie.model.tv.credit.Credit) credit).getCast().size();
        } else if (credit instanceof study.ian.movie.model.people.credit.movie.Credit) {
            return ((study.ian.movie.model.people.credit.movie.Credit) credit).getCast().size();
        } else if (credit instanceof study.ian.movie.model.people.credit.tv.Credit) {
            return ((study.ian.movie.model.people.credit.tv.Credit) credit).getCast().size();
        } else {
            return 0;
        }
    }

    private void loadImage(View targetView, String path) {
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.vd_credit_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(targetView)
                .load(path)
                .apply(requestOptions)
                .into((ImageView) targetView);
    }

    class CreditHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final ImageView creditImage;
        private final TextView creditText;
        private final TextView charText;

        CreditHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.holderDetailCard);
            creditImage = itemView.findViewById(R.id.holderDetailImage);
            creditText = itemView.findViewById(R.id.detailMainText);
            charText = itemView.findViewById(R.id.detailSubText);
        }
    }
}
