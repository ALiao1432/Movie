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

import study.ian.movie.PersonDetailActivity;
import study.ian.movie.R;
import study.ian.movie.model.people.popular.KnownFor;
import study.ian.movie.model.people.popular.PopularResult;
import study.ian.movie.service.PeopleService;
import study.ian.movie.service.ServiceBuilder;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleHolder> {

    private final String TAG = "PeopleAdapter";

    private Context context;
    private List<PopularResult> popularResultList = new ArrayList<>();

    public PeopleAdapter(Context context) {
        this.context = context;
    }

    public void addResults(List<PopularResult> rList) {
        popularResultList.addAll(rList);
        notifyDataSetChanged();
    }

    public void clearResults() {
        popularResultList.clear();
    }

    @NonNull
    @Override
    public PeopleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_main_display, viewGroup, false);
        return new PeopleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleHolder peopleHolder, int i) {
        PopularResult result = popularResultList.get(i);

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(peopleHolder.profileImage)
                .asBitmap()
                .load(ServiceBuilder.POSTER_BASE_URL + result.getProfile_path())
                .apply(requestOptions)
                .transition(new BitmapTransitionOptions().crossFade(250))
                .into(peopleHolder.profileImage);

        peopleHolder.nameText.setText(result.getName());

        StringBuilder sb = new StringBuilder();
        for (KnownFor knownFor : result.getKnown_for()) {
            sb.append(knownFor.getTitle()).append(", ");
        }
        if (sb.toString().equals("")) {
            peopleHolder.knownForText.setText(context.getResources().getString(R.string.no_known_for));
        } else {
            peopleHolder.knownForText.setText(sb.toString());
        }

        RxView.clicks(peopleHolder.cardView)
                .throttleFirst(1500, TimeUnit.MILLISECONDS) // only react to first click and skip the clicks within 1500ms
                .doOnNext(unit -> {
                    Intent intent = new Intent();
                    intent.putExtra(PeopleService.KEY_ID, popularResultList.get(i).getId());
                    intent.setClass(context, PersonDetailActivity.class);
                    context.startActivity(intent);
                })
                .doOnError(throwable -> Log.d(TAG, "onBindViewHolder: click people card error : " + throwable))
                .subscribe();
    }

    @Override
    public int getItemCount() {
        return popularResultList.size();
    }

    class PeopleHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView profileImage;
        private TextView nameText;
        private TextView knownForText;

        PeopleHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.holderMainDisplayCard);
            profileImage = itemView.findViewById(R.id.holderMainImage);
            nameText = itemView.findViewById(R.id.mainTitleText);
            knownForText = itemView.findViewById(R.id.subContentText);
        }
    }
}
