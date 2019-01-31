package study.ian.movie.model.tv.recommend;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecommendResult {

    @Nullable
    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("id")
    private int id;

    @Nullable
    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("overview")
    private String overview;

    @SerializedName("first_air_date")
    private String first_air_date;

    @SerializedName("origin_country")
    private List<String> origin_country;

    @SerializedName("genre_ids")
    private List<Integer> genre_ids;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("name")
    private String name;

    @SerializedName("original_name")
    private String original_name;

    public List<String> getOrigin_country() {
        return origin_country;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    @Nullable
    public String getPoster_path() {
        return poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    @Nullable
    public String getBackdrop_path() {
        return backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    @Override
    public String toString() {
        return "RecommendResult{" +
                "poster_path='" + poster_path + '\'' +
                ", popularity=" + popularity +
                ", id=" + id +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", vote_average=" + vote_average +
                ", overview='" + overview + '\'' +
                ", first_air_date='" + first_air_date + '\'' +
                ", origin_country=" + origin_country +
                ", genre_ids=" + genre_ids +
                ", original_language='" + original_language + '\'' +
                ", vote_count=" + vote_count +
                ", name='" + name + '\'' +
                ", original_name='" + original_name + '\'' +
                '}';
    }
}
