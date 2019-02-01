package study.ian.movie.model.people.popular;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KnownFor {

    @Nullable
    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("original_title")
    private String original_title;

    @SerializedName("genre_ids")
    private List<Integer> genre_ids = null;

    @SerializedName("id")
    private int id;

    @SerializedName("media_type")
    private String media_type;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("title")
    private String title;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("video")
    private boolean video;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("first_air_date")
    private String first_air_date;

    @SerializedName("original_country")
    private List<String> original_country = null;

    @SerializedName("name")
    private String name;

    @SerializedName("original_name")
    private String original_name;

    @Nullable
    public String getPoster_path() {
        return poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public int getId() {
        return id;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public List<String> getOriginal_country() {
        return original_country;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_name() {
        return original_name;
    }

    @Override
    public String toString() {
        return "KnownFor{" +
                "poster_path='" + poster_path + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", original_title='" + original_title + '\'' +
                ", genre_ids=" + genre_ids +
                ", id=" + id +
                ", media_type='" + media_type + '\'' +
                ", original_language='" + original_language + '\'' +
                ", title='" + title + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", popularity=" + popularity +
                ", vote_count=" + vote_count +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", first_air_date='" + first_air_date + '\'' +
                ", original_country=" + original_country +
                ", name='" + name + '\'' +
                ", original_name='" + original_name + '\'' +
                '}';
    }
}
