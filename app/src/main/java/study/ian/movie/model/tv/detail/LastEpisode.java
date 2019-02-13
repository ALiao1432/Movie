package study.ian.movie.model.tv.detail;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class LastEpisode {

    @SerializedName("air_date")
    private String air_date;

    @SerializedName("episode_number")
    private int episode_number;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("overview")
    private String overview;

    @SerializedName("production_code")
    private String production_code;

    @SerializedName("season_number")
    private int season_number;

    @SerializedName("show_id")
    private int show_id;

    @SerializedName("still_path")
    private String still_path;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("vote_count")
    private int vote_count;

    public String getAir_date() {
        return air_date;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getProduction_code() {
        return production_code;
    }

    public int getSeason_number() {
        return season_number;
    }

    public int getShow_id() {
        return show_id;
    }

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
        return "LastEpisode{" +
                "air_date='" + air_date + '\'' +
                ", episode_number=" + episode_number +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", overview='" + overview + '\'' +
                ", production_code='" + production_code + '\'' +
                ", season_number=" + season_number +
                ", show_id=" + show_id +
                ", still_path='" + still_path + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                '}';
    }
}
