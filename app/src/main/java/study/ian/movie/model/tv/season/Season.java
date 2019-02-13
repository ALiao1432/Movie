package study.ian.movie.model.tv.season;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Season {

    @SerializedName("_id")
    private String _id;

    @SerializedName("air_date")
    private String air_date;

    @SerializedName("episodes")
    private List<Episode> episodes;

    @SerializedName("name")
    private String name;

    @SerializedName("overview")
    private String overview;

    @SerializedName("id")
    private int id;

    @Nullable
    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("season_number")
    private int season_number;

    public String get_id() {
        return _id;
    }

    public String getAir_date() {
        return air_date;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public String getPoster_path() {
        return poster_path;
    }

    public int getSeason_number() {
        return season_number;
    }

    @NotNull
    @Override
    public String toString() {
        return "Season{" +
                "_id='" + _id + '\'' +
                ", air_date='" + air_date + '\'' +
                ", episodes=" + episodes +
                ", name='" + name + '\'' +
                ", overview='" + overview + '\'' +
                ", id=" + id +
                ", poster_path='" + poster_path + '\'' +
                ", season_number=" + season_number +
                '}';
    }
}
