package study.ian.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import study.ian.movie.R;
import study.ian.movie.model.people.credit.Cast;
import study.ian.movie.model.people.credit.Credit;
import study.ian.movie.service.ServiceBuilder;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditHolder> {

    private Context context;
    private Credit credit;

    public CreditAdapter(Context context, Credit credit) {
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
        Cast cast = credit.getCast().get(i);

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.vd_credit_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context)
                .load(ServiceBuilder.CREDIT_BASE_URL + cast.getProfile_path())
                .apply(requestOptions)
                .into(creditHolder.creditImage);

        creditHolder.creditText.setText(cast.getName());
        creditHolder.charText.setText(cast.getCharacter());
    }

    @Override
    public int getItemCount() {
        return credit.getCast().size();
    }

    class CreditHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView creditImage;
        private TextView creditText;
        private TextView charText;

        CreditHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.holderDetailCard);
            creditImage = itemView.findViewById(R.id.holderDetailImage);
            creditText = itemView.findViewById(R.id.detailMainText);
            charText = itemView.findViewById(R.id.detailSubText);
        }
    }
}
