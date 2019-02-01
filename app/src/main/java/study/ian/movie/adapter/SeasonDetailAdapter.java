package study.ian.movie.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import study.ian.movie.R;
import study.ian.movie.model.tv.season.Episode;

public class SeasonDetailAdapter extends RecyclerView.Adapter<SeasonDetailAdapter.SeasonDetailHolder> {

    private final String TAG = "SeasonDetailAdapter";

    private Context context;
    private List<Episode> episodeList;

    public SeasonDetailAdapter(Context context, List<Episode> episodeList) {
        this.context = context;
        this.episodeList = episodeList;
    }

    @NonNull
    @Override
    public SeasonDetailHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.holder_season_detail, viewGroup, false);
        return new SeasonDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonDetailHolder holder, int i) {
        Episode episode = episodeList.get(i);

        holder.episodeNumText.setText(String.valueOf(episode.getEpisode_number()));
        holder.episodeNameText.setText(episode.getName());
        holder.episodeAirDateTExt.setText(episode.getAir_date());
        holder.episodeOverviewText.setText(episode.getOvewview());
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    class SeasonDetailHolder extends RecyclerView.ViewHolder {

        private TextView episodeNumText;
        private TextView episodeNameText;
        private TextView episodeAirDateTExt;
        private TextView episodeOverviewText;

        SeasonDetailHolder(@NonNull View itemView) {
            super(itemView);

            episodeNumText = itemView.findViewById(R.id.episodeNumText);
            episodeNameText = itemView.findViewById(R.id.episodeNameText);
            episodeAirDateTExt = itemView.findViewById(R.id.episodeAirDateTExt);
            episodeOverviewText = itemView.findViewById(R.id.episodeOverviewText);
        }
    }
}
