package study.ian.movie.model.tv.season;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Episode {

    @SerializedName("air_date")
    private String air_date;

    @SerializedName("crew")
    private List<Crew> crew;

    @SerializedName("episode_number")
    private int episode_number;

    @SerializedName("guest_stars")
    private List<Object> guest_stars;

    @SerializedName("name")
    private String name;

    @SerializedName("overview")
    private String overview;

    @SerializedName("id")
    private int id;

    @Nullable
    @SerializedName("production_code")
    private String production_code;

    @SerializedName("season_number")
    private int season_number;

    @Nullable
    @SerializedName("still_path")
    private String still_path;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("vote_count")
    private int vote_count;

    public String getAir_date() {
        return air_date;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public List<Object> getGuest_stars() {
        return guest_stars;
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
    public String getProduction_code() {
        return production_code;
    }

    public int getSeason_number() {
        return season_number;
    }

    @Nullable
    public String getStill_path() {
        return still_path;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    @NotNull
    @Override
    public String toString() {
        return "Episode{" +
                "air_date='" + air_date + '\'' +
                ", crew=" + crew +
                ", episode_number=" + episode_number +
                ", guest_stars=" + guest_stars +
                ", name='" + name + '\'' +
                ", overview='" + overview + '\'' +
                ", id=" + id +
                ", production_code='" + production_code + '\'' +
                ", season_number=" + season_number +
                ", still_path='" + still_path + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                '}';
    }
}
