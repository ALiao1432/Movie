package study.ian.movie.model.tv.detail;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Detail {

    @Nullable
    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("created_by")
    private List<CreatedBy> created_by;

    @SerializedName("episode_run_time")
    private List<Integer> episode_run_time;

    @SerializedName("first_air_date")
    private String first_air_date;

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("id")
    private int id;

    @SerializedName("in_production")
    private boolean in_production;

    @SerializedName("languages")
    private List<String> languages;

    @SerializedName("last_air_date")
    private String last_air_date;

    @SerializedName("last_episode_to_air")
    private LastEpisode last_episode_to_air;

    @SerializedName("name")
    private String name;

    @Nullable
    @SerializedName("next_episode_to_air")
    private Object next_episode_to_air;

    @SerializedName("networks")
    private List<Network> networks;

    @SerializedName("number_of_episode")
    private int number_of_episode;

    @SerializedName("number_of_seasons")
    private int number_of_seasons;

    @SerializedName("origin_country")
    private List<String> origin_country;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("original_name")
    private String original_name;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("poster_path")
    private String poster_paht;

    @SerializedName("production_companies")
    private List<ProductionCompany> production_companies;

    @SerializedName("seasons")
    private List<Season> seasons;

    @SerializedName("status")
    private String status;

    @SerializedName("type")
    private String type;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("vote_count")
    private int vote_count;

    @Nullable
    public String getBackdrop_path() {
        return backdrop_path;
    }

    public List<CreatedBy> getCreated_by() {
        return created_by;
    }

    public List<Integer> getEpisode_run_time() {
        return episode_run_time;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
        return id;
    }

    public boolean isIn_production() {
        return in_production;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public String getLast_air_date() {
        return last_air_date;
    }

    public LastEpisode getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public Object getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public int getNumber_of_episode() {
        return number_of_episode;
    }

    public int getNumber_of_seasons() {
        return number_of_seasons;
    }

    public List<String> getOrigin_country() {
        return origin_country;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPoster_paht() {
        return poster_paht;
    }

    public List<ProductionCompany> getProduction_companies() {
        return production_companies;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public double getVote_average() {
        return vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "backdrop_path='" + backdrop_path + '\'' +
                ", created_by=" + created_by +
                ", episode_run_time=" + episode_run_time +
                ", first_air_date='" + first_air_date + '\'' +
                ", genres=" + genres +
                ", homepage='" + homepage + '\'' +
                ", id=" + id +
                ", in_production=" + in_production +
                ", languages=" + languages +
                ", last_air_date='" + last_air_date + '\'' +
                ", last_episode_to_air=" + last_episode_to_air +
                ", name='" + name + '\'' +
                ", next_episode_to_air=" + next_episode_to_air +
                ", networks=" + networks +
                ", number_of_episode=" + number_of_episode +
                ", number_of_seasons=" + number_of_seasons +
                ", origin_country=" + origin_country +
                ", original_language='" + original_language + '\'' +
                ", original_name='" + original_name + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", poster_paht='" + poster_paht + '\'' +
                ", production_companies=" + production_companies +
                ", seasons=" + seasons +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                '}';
    }
}
