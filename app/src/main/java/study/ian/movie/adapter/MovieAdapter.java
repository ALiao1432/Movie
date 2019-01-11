package study.ian.movie.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import study.ian.movie.R;
import study.ian.movie.model.movie.Movie;
import study.ian.movie.model.movie.Result;
import study.ian.movie.service.ServiceBuilder;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private final String TAG = "MovieAdapter";
    private final int COLOR_INDEX = 1;

    private Context context;
    private Movie movie;
    private Palette palette;

    public MovieAdapter(Context context, Movie movie) {
        this.context = context;
        this.movie = movie;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_movie, viewGroup, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        Result movieResult = movie.getResults().get(i);

        Glide.with(context)
                .asBitmap()
                .load(ServiceBuilder.POSTER_BASE_URL + movieResult.getPoster_path())
                .apply(new RequestOptions().centerCrop())
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        // generate palette asynchronously
                        Palette.from(resource).generate(p -> {
                            palette = p;
                            final Palette.Swatch swatch = palette.getDarkVibrantSwatch();

                            if (swatch != null) {
                                movieHolder.layout.setBackgroundColor(swatch.getRgb());
                                movieHolder.titleText.setTextColor(swatch.getTitleTextColor());
                                movieHolder.releaseDateText.setTextColor(swatch.getBodyTextColor());
                            }
                        });
                        return false;
                    }
                })
                .into(movieHolder.posterImage);

        movieHolder.titleText.setText(movieResult.getTitle());
        movieHolder.releaseDateText.setText(movieResult.getRelease_date());
    }

    @Override
    public int getItemCount() {
        return movie.getResults().size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout layout;
        private ImageView posterImage;
        private TextView titleText;
        private TextView releaseDateText;

        MovieHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout_movie_holder);
            posterImage = itemView.findViewById(R.id.holder_movie_image);
            titleText = itemView.findViewById(R.id.text_movie_title);
            releaseDateText = itemView.findViewById(R.id.text_release_date);
        }
    }
}
