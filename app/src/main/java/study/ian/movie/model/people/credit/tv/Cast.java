package study.ian.movie.model.people.credit.tv;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cast {

    @SerializedName("credit_id")
    private String credit_id;

    @SerializedName("original_name")
    private String original_name;

    @SerializedName("id")
    private int id;

    @SerializedName("genre_ids")
    private List<Integer> genre_ids;

    @SerializedName("character")
    private String character;

    @SerializedName("name")
    private String name;

    @Nullable
    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("episode_count")
    private int episode_count;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("first_air_date")
    private String first_air_date;

    @Nullable
    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("overview")
    private String overview;

    @SerializedName("origin_country")
    private List<String> origin_country;

    public String getCredit_id() {
        return credit_id;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public String getPoster_path() {
        return poster_path;
    }

    public int getVote_count() {
        return vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getEpisode_count() {
        return episode_count;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    @Nullable
    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    public List<String> getOrigin_country() {
        return origin_country;
    }

    @Override
    public String toString() {
        return "Cast{" +
                "credit_id='" + credit_id + '\'' +
                ", original_name='" + original_name + '\'' +
                ", id=" + id +
                ", genre_ids=" + genre_ids +
                ", character='" + character + '\'' +
                ", name='" + name + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", vote_count=" + vote_count +
                ", vote_average=" + vote_average +
                ", popularity=" + popularity +
                ", episode_count=" + episode_count +
                ", original_language='" + original_language + '\'' +
                ", first_air_date='" + first_air_date + '\'' +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", overview='" + overview + '\'' +
                ", origin_country=" + origin_country +
                '}';
    }
}
