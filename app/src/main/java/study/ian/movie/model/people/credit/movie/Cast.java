package study.ian.movie.model.people.credit.movie;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cast {

    @SerializedName("character")
    private String character;

    @SerializedName("credit_id")
    private String credit_id;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("video")
    private boolean video;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("vote_average")
    private double vote_average;

    @SerializedName("title")
    private String title;

    @SerializedName("genre_ids")
    private List<Integer> genre_ids;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("original_title")
    private String original_title;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("id")
    private int id;

    @Nullable
    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("overview")
    private String overview;

    @Nullable
    @SerializedName("poster_path")
    private String poster_path;

    public String getCharacter() {
        return character;
    }

    public String getCredit_id() {
        return credit_id;
    }

    public String getRelease_date() {
        return release_date;
    }

    public int getVote_count() {
        return vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public boolean isAdult() {
        return adult;
    }

    public double getVote_average() {
        return vote_average;
    }

    public String getTitle() {
        return title;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getId() {
        return id;
    }

    @Nullable
    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOverview() {
        return overview;
    }

    @Nullable
    public String getPoster_path() {
        return poster_path;
    }

    @Override
    public String toString() {
        return "Cast{" +
                "character='" + character + '\'' +
                ", credit_id='" + credit_id + '\'' +
                ", release_date='" + release_date + '\'' +
                ", vote_count=" + vote_count +
                ", video=" + video +
                ", adult=" + adult +
                ", vote_average=" + vote_average +
                ", title='" + title + '\'' +
                ", genre_ids=" + genre_ids +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", popularity=" + popularity +
                ", id=" + id +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", overview='" + overview + '\'' +
                ", poster_path='" + poster_path + '\'' +
                '}';
    }
}
